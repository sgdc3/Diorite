package org.diorite.material.blocks.wooden.wood;

import java.util.Map;

import org.diorite.material.Material;
import org.diorite.material.WoodTypeMat;
import org.diorite.material.blocks.SlabTypeMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "DoubleWoodenSlab" and all its subtypes.
 * <p>
 * NOTE: Will crash game when in inventory.
 */
public class DoubleWoodenSlabMat extends WoodSlabMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 6;

    public static final DoubleWoodenSlabMat DOUBLE_WOODEN_SLAB_OAK      = new DoubleWoodenSlabMat();
    public static final DoubleWoodenSlabMat DOUBLE_WOODEN_SLAB_SPRUCE   = new DoubleWoodenSlabMat(WoodTypeMat.SPRUCE, SlabTypeMat.BOTTOM);
    public static final DoubleWoodenSlabMat DOUBLE_WOODEN_SLAB_BIRCH    = new DoubleWoodenSlabMat(WoodTypeMat.BIRCH, SlabTypeMat.BOTTOM);
    public static final DoubleWoodenSlabMat DOUBLE_WOODEN_SLAB_JUNGLE   = new DoubleWoodenSlabMat(WoodTypeMat.JUNGLE, SlabTypeMat.BOTTOM);
    public static final DoubleWoodenSlabMat DOUBLE_WOODEN_SLAB_DARK_OAK = new DoubleWoodenSlabMat(WoodTypeMat.DARK_OAK, SlabTypeMat.BOTTOM);
    public static final DoubleWoodenSlabMat DOUBLE_WOODEN_SLAB_ACACIA   = new DoubleWoodenSlabMat(WoodTypeMat.ACACIA, SlabTypeMat.BOTTOM);

    private static final Map<String, DoubleWoodenSlabMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DoubleWoodenSlabMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected DoubleWoodenSlabMat()
    {
        super("DOUBLE_WOODEN_SLAB", 125, "minecraft:double_wooden_slab", "OAK", WoodTypeMat.OAK, SlabTypeMat.BOTTOM, 2, 30);
    }

    protected DoubleWoodenSlabMat(final WoodTypeMat woodType, final SlabTypeMat slabType)
    {
        super(DOUBLE_WOODEN_SLAB_OAK.name(), DOUBLE_WOODEN_SLAB_OAK.ordinal(), DOUBLE_WOODEN_SLAB_OAK.getMinecraftId(), woodType.name() + (slabType.getFlag() == 0 ? "" : slabType.name()), woodType, slabType, DOUBLE_WOODEN_SLAB_OAK.getHardness(), DOUBLE_WOODEN_SLAB_OAK.getBlastResistance());
    }

    protected DoubleWoodenSlabMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final SlabTypeMat slabType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, slabType, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.WOODEN_SLAB.getWoodType(this.woodType);
    }

    @Override
    public DoubleWoodenSlabMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DoubleWoodenSlabMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public DoubleWoodenSlabMat getWoodType(final WoodTypeMat woodType)
    {
        return getByID(combine(woodType, this.slabType));
    }

    /**
     * Returns one of DoubleWoodenSlab sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DoubleWoodenSlab or null
     */
    public static DoubleWoodenSlabMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DoubleWoodenSlab sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DoubleWoodenSlab or null
     */
    public static DoubleWoodenSlabMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of DoubleWoodenSlab sub-type based on {@link WoodTypeMat} and {@link SlabTypeMat}
     * It will never return null.
     *
     * @param woodType type of wood.
     * @param slabType type of slab.
     *
     * @return sub-type of DoubleWoodenSlab
     */
    public static DoubleWoodenSlabMat getDoubleWoodenSlab(final WoodTypeMat woodType, final SlabTypeMat slabType)
    {
        return getByID(combine(woodType, slabType));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DoubleWoodenSlabMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DoubleWoodenSlabMat[] types()
    {
        return DoubleWoodenSlabMat.doubleWoodenSlabTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DoubleWoodenSlabMat[] doubleWoodenSlabTypes()
    {
        return byID.values(new DoubleWoodenSlabMat[byID.size()]);
    }

    static
    {
        DoubleWoodenSlabMat.register(DOUBLE_WOODEN_SLAB_OAK);
        DoubleWoodenSlabMat.register(DOUBLE_WOODEN_SLAB_SPRUCE);
        DoubleWoodenSlabMat.register(DOUBLE_WOODEN_SLAB_BIRCH);
        DoubleWoodenSlabMat.register(DOUBLE_WOODEN_SLAB_JUNGLE);
        DoubleWoodenSlabMat.register(DOUBLE_WOODEN_SLAB_DARK_OAK);
        DoubleWoodenSlabMat.register(DOUBLE_WOODEN_SLAB_ACACIA);
    }
}
