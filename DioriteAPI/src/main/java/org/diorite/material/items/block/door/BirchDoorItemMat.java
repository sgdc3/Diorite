package org.diorite.material.items.block.door;

import java.util.Map;

import org.diorite.material.WoodTypeMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class BirchDoorItemMat extends WoodenDoorItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final BirchDoorItemMat BIRCH_DOOR_ITEM = new BirchDoorItemMat();

    private static final Map<String, BirchDoorItemMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<BirchDoorItemMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected BirchDoorItemMat()
    {
        super("BIRCH_DOOR_ITEM", 428, "minecraft:birch_door", "BIRCH_DOOR_ITEM", (short) 0x00, WoodTypeMat.BIRCH);
    }

    protected BirchDoorItemMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final WoodTypeMat woodType)
    {
        super(enumName, id, minecraftId, typeName, type, woodType);
    }

    protected BirchDoorItemMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final WoodTypeMat woodType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType);
    }

    @Override
    public BirchDoorItemMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public BirchDoorItemMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of BirchDoorItem sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of BirchDoorItem or null
     */
    public static BirchDoorItemMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of BirchDoorItem sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of BirchDoorItem or null
     */
    public static BirchDoorItemMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BirchDoorItemMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public BirchDoorItemMat[] types()
    {
        return BirchDoorItemMat.birchDoorItemTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static BirchDoorItemMat[] birchDoorItemTypes()
    {
        return byID.values(new BirchDoorItemMat[byID.size()]);
    }

    static
    {
        BirchDoorItemMat.register(BIRCH_DOOR_ITEM);
    }
}

