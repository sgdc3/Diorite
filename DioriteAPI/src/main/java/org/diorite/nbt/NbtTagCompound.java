package org.diorite.nbt;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class NbtTagCompound extends NbtAbstractTag implements NbtNamedTagContainer
{
    protected Map<String, NbtTag> tags;

    public NbtTagCompound()
    {
        super("");
        this.tags = new HashMap<>(1);
    }

    public NbtTagCompound(final String name)
    {
        super(name);
        this.tags = new HashMap<>(8);
    }

    public NbtTagCompound(final String name, final Map<String, NbtTag> tags)
    {
        super(name);
        this.tags = new HashMap<>(tags);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends NbtTag> T getTag(final String name)
    {
        final int index = name.indexOf('.');
        if (index == - 1)
        {
            return (T) this.tags.get(name);
        }
        return Optional.ofNullable(this.getCompound(name.substring(0, index))).map(tag -> tag.<T>getTag(name.substring(index + 1))).orElse(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends NbtTag> T getTag(final String name, final Class<T> tagClass)
    {
        return (T) this.getTag(name);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends NbtTag> T getTag(final String name, final T def)
    {
        final int index = name.indexOf('.');
        if (index == - 1)
        {
            return (T) this.tags.get(name);
        }
        return Optional.ofNullable(this.getCompound(name.substring(0, index))).map(tag -> tag.<T>getTag(name.substring(index + 1))).orElse(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends NbtTag> T getTag(final String name, final Class<T> tagClass, final T def)
    {
        return (T) Optional.ofNullable(this.getTag(name)).orElse(def);
    }

    @Override
    public Map<String, NbtTag> getTags()
    {
        return new HashMap<>(this.tags);
    }

    @Override
    public void removeTag(final String name)
    {
        final int index = name.indexOf('.');
        if (index == - 1)
        {
            this.tags.remove(name);
            return;
        }
        this.getCompound(name.substring(0, index)).removeTag(name.substring(index + 1));
    }

    @Override
    public void addTag(final NbtTag tag)
    {
        if (this.tags.containsKey(tag.getName()))
        {
            this.tags.get(tag.getName()).setParent(null);
        }
        this.tags.put(tag.getName(), tag);
        tag.setParent(this);
    }

    @Override
    public void setTag(final String key, final NbtTag tag)
    {
        if (this.tags.containsKey(key))
        {
            this.tags.get(key).setParent(null);
        }
        this.tags.put(key, tag);
        tag.setParent(this);
    }

    @Override
    public void addTag(final String path, final NbtTag tag)
    {
        final NbtTagCompound compound = this.getCompound(path);
        compound.addTag(tag);
    }

    @Override
    public boolean containsTag(final String name)
    {
        final int index = name.indexOf('.');
        if (index == - 1)
        {
            return this.tags.containsKey(name);
        }
        return this.getCompound(name.substring(0, index)).containsTag(name.substring(index + 1));
    }

    @Override
    public void removeTag(final NbtTag tag)
    {
        this.tags.remove(tag.getName());
    }

    /*
     * Simple get methods.
     */

    public NbtTagCompound getCompound(final String name)
    {
        return this.getTag(name, NbtTagCompound.class);
    }

    public int getInt(final String name)
    {
        return this.getTag(name, NbtTagInt.class, new NbtTagInt(name, 0)).getValue();
    }

    public Integer getInteger(final String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagInt.class)).map(NbtTagInt::getValue).orElse(null);
    }

    public short getShort(final String name)
    {

        return this.getTag(name, NbtTagShort.class, new NbtTagShort(name, (short) 0)).getValue();
    }

    public Short getShortObj(final String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagShort.class)).map(NbtTagShort::getValue).orElse(null);
    }

    public byte getByte(final String name)
    {

        return this.getTag(name, NbtTagByte.class, new NbtTagByte(name, (byte) 0)).getValue();
    }

    public Byte getByteObj(final String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagByte.class)).map(NbtTagByte::getValue).orElse(null);
    }

    public long getLong(final String name)
    {
        return this.getTag(name, NbtTagLong.class, new NbtTagLong(name, 0)).getValue();
    }

    public Long getLongObj(final String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagLong.class)).map(NbtTagLong::getValue).orElse(null);
    }

    public double getDouble(final String name)
    {
        return this.getTag(name, NbtTagDouble.class, new NbtTagDouble(name, 0)).getValue();
    }

    public Double getDoubleObj(final String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagDouble.class)).map(NbtTagDouble::getValue).orElse(null);
    }

    public float getFloat(final String name)
    {
        return this.getTag(name, NbtTagFloat.class, new NbtTagFloat(name, 0)).getValue();
    }

    public Float getFloatObj(final String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagFloat.class)).map(NbtTagFloat::getValue).orElse(null);
    }

    public String getString(final String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagString.class)).map(NbtTagString::getValue).orElse(null);
    }

    public <T extends NbtTag> List<T> getList(final String name, final Class<T> itemClass)
    {
        return this.getTag(name, NbtTagList.class).getTags(itemClass);
    }

    public int[] getIntArray(final String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagIntArray.class)).map(NbtTagIntArray::getValue).orElse(null);
    }

    public byte[] getByteArray(final String name)
    {
        return Optional.ofNullable(this.getTag(name, NbtTagByteArray.class)).map(NbtTagByteArray::getValue).orElse(null);
    }

    /*
     * Get list helper methods
     */

    public String[] getStringArray(final String name)
    {
        final List<NbtTagString> tags = this.getList(name, NbtTagString.class);
        final String[] array = new String[tags.size()];
        for (int i = 0; i < tags.size(); i++)
        {
            array[i] = tags.get(i).getValue();
        }
        return array;
    }

    public double[] getDoubleArray(final String name)
    {
        final List<NbtTagDouble> tags = this.getList(name, NbtTagDouble.class);
        final double[] array = new double[tags.size()];
        for (int i = 0; i < tags.size(); i++)
        {
            array[i] = tags.get(i).getValue();
        }
        return array;
    }

    public float[] getFloatArray(final String name)
    {
        final List<NbtTagFloat> tags = this.getList(name, NbtTagFloat.class);
        final float[] array = new float[tags.size()];
        for (int i = 0; i < tags.size(); i++)
        {
            array[i] = tags.get(i).getValue();
        }
        return array;
    }

    public long[] getLongArray(final String name)
    {
        final List<NbtTagLong> tags = this.getList(name, NbtTagLong.class);
        final long[] array = new long[tags.size()];
        for (int i = 0; i < tags.size(); i++)
        {
            array[i] = tags.get(i).getValue();
        }
        return array;
    }

    public short[] getShortArray(final String name)
    {
        final List<NbtTagShort> tags = this.getList(name, NbtTagShort.class);
        final short[] array = new short[tags.size()];
        for (int i = 0; i < tags.size(); i++)
        {
            array[i] = tags.get(i).getValue();
        }
        return array;
    }

    /*
     * Get methods with default
     */

    public NbtTagCompound getCompound(final String name, final NbtTagCompound def)
    {
        return this.getTag(name, NbtTagCompound.class, def);
    }

    public int getInt(final String name, final NbtTagInt def)
    {
        return this.getTag(name, NbtTagInt.class, def).getValue();
    }

    public short getShort(final String name, final NbtTagShort def)
    {
        return this.getTag(name, NbtTagShort.class, def).getValue();
    }

    public byte getByte(final String name, final NbtTagByte def)
    {
        return this.getTag(name, NbtTagByte.class, def).getValue();
    }

    public long getLong(final String name, final NbtTagLong def)
    {
        return this.getTag(name, NbtTagLong.class, def).getValue();
    }

    public double getDouble(final String name, final NbtTagDouble def)
    {
        return this.getTag(name, NbtTagDouble.class, def).getValue();
    }

    public float getFloat(final String name, final NbtTagFloat def)
    {
        return this.getTag(name, NbtTagFloat.class, def).getValue();
    }

    public String getString(final String name, final NbtTagString def)
    {
        return this.getTag(name, NbtTagString.class, def).getValue();
    }

    public <T extends NbtTag> List<T> getList(final String name, final Class<T> itemClass, final NbtTagList def)
    {
        return this.getTag(name, NbtTagList.class, def).getTags(itemClass);
    }

    public int[] getIntArray(final String name, final NbtTagIntArray def)
    {
        return this.getTag(name, NbtTagIntArray.class, def).getValue();
    }

    public byte[] getByteArray(final String name, final NbtTagByteArray def)
    {
        return this.getTag(name, NbtTagByteArray.class, def).getValue();
    }

    /*
     * Get methods with simple default
     */

    public int getInt(final String name, final int def)
    {
        return this.getTag(name, NbtTagInt.class, new NbtTagInt(name, def)).getValue();
    }

    public short getShort(final String name, final int def)
    {
        return this.getTag(name, NbtTagShort.class, new NbtTagShort(name, (short) def)).getValue();
    }

    public byte getByte(final String name, final int def)
    {
        return this.getTag(name, NbtTagByte.class, new NbtTagByte(name, (byte) def)).getValue();
    }

    public long getLong(final String name, final long def)
    {
        return this.getTag(name, NbtTagLong.class, new NbtTagLong(name, def)).getValue();
    }

    public double getDouble(final String name, final double def)
    {
        return this.getTag(name, NbtTagDouble.class, new NbtTagDouble(name, def)).getValue();
    }

    public float getFloat(final String name, final double def)
    {
        return this.getTag(name, NbtTagFloat.class, new NbtTagFloat(name, (float) def)).getValue();
    }

    public String getString(final String name, final String def)
    {
        return this.getTag(name, NbtTagString.class, new NbtTagString(name, def)).getValue();
    }

    public <T extends NbtTag> List<T> getList(final String name, final Class<T> itemClass, final List<T> def)
    {
        return this.getTag(name, NbtTagList.class, new NbtTagList(name, def)).getTags(itemClass);
    }

    public int[] getIntArray(final String name, final int[] def)
    {
        return this.getTag(name, NbtTagIntArray.class, new NbtTagIntArray(name, def)).getValue();
    }

    public byte[] getByteArray(final String name, final byte[] def)
    {
        return this.getTag(name, NbtTagByteArray.class, new NbtTagByteArray(name, def)).getValue();
    }

    /*
     * Get list helper methods with default
     */

    public String[] getStringArray(final String name, final String[] def)
    {
        final NbtTagList tagsList = this.getTag(name, NbtTagList.class);
        if (tagsList == null)
        {
            return def;
        }
        final List<NbtTagString> tags = tagsList.getTags(NbtTagString.class);
        final String[] array = new String[tags.size()];
        for (int i = 0; i < tags.size(); i++)
        {
            array[i] = tags.get(i).getValue();
        }
        return array;
    }

    public double[] getDoubleArray(final String name, final double[] def)
    {
        final NbtTagList tagsList = this.getTag(name, NbtTagList.class);
        if (tagsList == null)
        {
            return def;
        }
        final List<NbtTagDouble> tags = tagsList.getTags(NbtTagDouble.class);
        final double[] array = new double[tags.size()];
        for (int i = 0; i < tags.size(); i++)
        {
            array[i] = tags.get(i).getValue();
        }
        return array;
    }

    public float[] getFloatArray(final String name, final float[] def)
    {
        final NbtTagList tagsList = this.getTag(name, NbtTagList.class);
        if (tagsList == null)
        {
            return def;
        }
        final List<NbtTagFloat> tags = tagsList.getTags(NbtTagFloat.class);
        final float[] array = new float[tags.size()];
        for (int i = 0; i < tags.size(); i++)
        {
            array[i] = tags.get(i).getValue();
        }
        return array;
    }

    public long[] getLongArray(final String name, final long[] def)
    {
        final NbtTagList tagsList = this.getTag(name, NbtTagList.class);
        if (tagsList == null)
        {
            return def;
        }
        final List<NbtTagLong> tags = tagsList.getTags(NbtTagLong.class);
        final long[] array = new long[tags.size()];
        for (int i = 0; i < tags.size(); i++)
        {
            array[i] = tags.get(i).getValue();
        }
        return array;
    }

    public short[] getShortArray(final String name, final short[] def)
    {
        final NbtTagList tagsList = this.getTag(name, NbtTagList.class);
        if (tagsList == null)
        {
            return def;
        }
        final List<NbtTagShort> tags = tagsList.getTags(NbtTagShort.class);
        final short[] array = new short[tags.size()];
        for (int i = 0; i < tags.size(); i++)
        {
            array[i] = tags.get(i).getValue();
        }
        return array;
    }

    /*
     * Boolean methods.
     */

    public void setBoolean(final String path, final boolean value)
    {
        this.setByte(path, value ? 1 : 0);
    }

    public boolean getBoolean(final String path)
    {
        return this.getByte(path) == 1;
    }

    public boolean getBoolean(final String path, final boolean def)
    {
        return this.getByte(path, def ? 1 : 0) == 1;
    }

    /*
     * Set methods.
     */

    public void setList(final String path, final List<NbtTag> value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagList(path, value));
            return;
        }
        this.getCompound(path.substring(0, index)).setList(path.substring(index + 1), value);
    }

    public void setByteArray(final String path, final byte[] value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagByteArray(path, value));
            return;
        }
        this.getCompound(path.substring(0, index)).setByteArray(path.substring(index + 1), value);
    }

    public void setIntArray(final String path, final int[] value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagIntArray(path, value));
            return;
        }
        this.getCompound(path.substring(0, index)).setIntArray(path.substring(index + 1), value);
    }

    public void setString(final String path, final String value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagString(path, value));
            return;
        }
        this.getCompound(path.substring(0, index)).setString(path.substring(index + 1), value);
    }

    public void setShort(final String path, final int value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagShort(path, (short) value));
            return;
        }
        this.getCompound(path.substring(0, index)).setShort(path.substring(index + 1), value);
    }

    public void setDouble(final String path, final double value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagDouble(path, value));
            return;
        }
        this.getCompound(path.substring(0, index)).setDouble(path.substring(index + 1), value);
    }

    public void setFloat(final String path, final double value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagFloat(path, (float) value));
            return;
        }
        this.getCompound(path.substring(0, index)).setFloat(path.substring(index + 1), value);
    }

    public void setLong(final String path, final long value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagLong(path, value));
            return;
        }
        this.getCompound(path.substring(0, index)).setLong(path.substring(index + 1), value);
    }

    public void setByte(final String path, final int value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagByte(path, (byte) value));
            return;
        }
        this.getCompound(path.substring(0, index)).setByte(path.substring(index + 1), value);
    }

    public void setInt(final String path, final int value)
    {
        final int index = path.indexOf('.');
        if (index == - 1)
        {
            this.addTag(new NbtTagInt(path, value));
            return;
        }
        this.getCompound(path.substring(0, index)).setInt(path.substring(index + 1), value);
    }


    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.COMPOUND;
    }

    @Override
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        for (final Map.Entry<String, NbtTag> tagEntry : this.tags.entrySet())
        {
            outputStream.writeByte(tagEntry.getValue().getTagID());
            tagEntry.getValue().write(outputStream, false);
        }
        outputStream.writeByte(NbtTagType.END.getTypeID());
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);
        limiter.incrementComplexity();

        this.tags = new HashMap<>(8);
        do
        {
            final byte type = inputStream.readByte();
            final NbtTagType tagType = NbtTagType.valueOf(type);
            if (tagType == null)
            {
                throw new IOException("Could not find a tag for type ID " + type + ".");
            }
            if (tagType == NbtTagType.END)
            {
                break;
            }
            this.addTag(inputStream.readTag(tagType, false, limiter));
        } while (true);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("tags", this.tags).toString();
    }
}