package org.diorite.impl.connection.packets.play.out;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayOutListener;
import org.diorite.inventory.item.ItemStack;

@PacketClass(id = 0x30, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutWindowItems implements PacketPlayOut
{
    private int             windowId;
    private List<ItemStack> items;

    public PacketPlayOutWindowItems()
    {
    }

    public PacketPlayOutWindowItems(final int windowId, final List<ItemStack> items)
    {
        this.windowId = windowId;
        this.items = items;
    }

    public PacketPlayOutWindowItems(final int windowId, final ItemStack... items)
    {
        this.items = Arrays.asList(items);
        this.windowId = windowId;
    }

    public int getWindowId()
    {
        return this.windowId;
    }

    public void setWindowId(final int windowId)
    {
        this.windowId = windowId;
    }

    public List<ItemStack> getItems()
    {
        return this.items;
    }

    public void addItem(final ItemStack item)
    {
        if (this.items == null)
        {
            this.items = new ArrayList<>(10);
        }
        this.items.add(item);
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.windowId = data.readUnsignedByte();
        final short count = data.readShort();
        for (int i = 0; count < i; i++)
        {
            this.items.add(data.readItemStack());
        }
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeByte(this.windowId);
        data.writeShort(this.items.size());
        this.items.forEach(data::writeItemStack);
    }

    @Override
    public void handle(final PacketPlayOutListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("windowId", this.windowId).append("items", this.items).toString();
    }
}
