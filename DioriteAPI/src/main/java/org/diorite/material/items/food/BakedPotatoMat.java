package org.diorite.material.items.food;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

@SuppressWarnings("MagicNumber")
public class BakedPotatoMat extends EdibleItemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final BakedPotatoMat BAKED_POTATO = new BakedPotatoMat();

    private static final Map<String, BakedPotatoMat>     byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TShortObjectMap<BakedPotatoMat> byID   = new TShortObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Short.MIN_VALUE);

    protected BakedPotatoMat()
    {
        super("BAKED_POTATO", 393, "minecraft:baked_potato", "BAKED_POTATO", (short) 0x00, 5, 6);
    }

    protected BakedPotatoMat(final String enumName, final int id, final String minecraftId, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    protected BakedPotatoMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type, final int foodLevelIncrease, final float saturationIncrease)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, foodLevelIncrease, saturationIncrease);
    }

    @Override
    public BakedPotatoMat getType(final int type)
    {
        return getByID(type);
    }

    @Override
    public BakedPotatoMat getType(final String type)
    {
        return getByEnumName(type);
    }

    /**
     * Returns one of BakedPotato sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of BakedPotato or null
     */
    public static BakedPotatoMat getByID(final int id)
    {
        return byID.get((short) id);
    }

    /**
     * Returns one of BakedPotato sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of BakedPotato or null
     */
    public static BakedPotatoMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BakedPotatoMat element)
    {
        byID.put(element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public BakedPotatoMat[] types()
    {
        return BakedPotatoMat.bakedPotatoTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static BakedPotatoMat[] bakedPotatoTypes()
    {
        return byID.values(new BakedPotatoMat[byID.size()]);
    }

    static
    {
        BakedPotatoMat.register(BAKED_POTATO);
    }
}

