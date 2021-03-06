package org.diorite.material.blocks.redstone.piston;

import java.util.Map;

import org.diorite.BlockFace;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Piston" and all its subtypes.
 */
public class PistonMat extends PistonBaseMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 12;

    public static final PistonMat PISTON_DOWN  = new PistonMat();
    public static final PistonMat PISTON_UP    = new PistonMat(BlockFace.UP, false);
    public static final PistonMat PISTON_NORTH = new PistonMat(BlockFace.NORTH, false);
    public static final PistonMat PISTON_SOUTH = new PistonMat(BlockFace.SOUTH, false);
    public static final PistonMat PISTON_WEST  = new PistonMat(BlockFace.WEST, false);
    public static final PistonMat PISTON_EAST  = new PistonMat(BlockFace.EAST, false);

    public static final PistonMat PISTON_DOWN_EXTENDED  = new PistonMat(BlockFace.DOWN, true);
    public static final PistonMat PISTON_UP_EXTENDED    = new PistonMat(BlockFace.UP, true);
    public static final PistonMat PISTON_NORTH_EXTENDED = new PistonMat(BlockFace.NORTH, true);
    public static final PistonMat PISTON_SOUTH_EXTENDED = new PistonMat(BlockFace.SOUTH, true);
    public static final PistonMat PISTON_WEST_EXTENDED  = new PistonMat(BlockFace.WEST, true);
    public static final PistonMat PISTON_EAST_EXTENDED  = new PistonMat(BlockFace.EAST, true);

    private static final Map<String, PistonMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PistonMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected PistonMat()
    {
        super("PISTON", 33, "minecraft:piston", BlockFace.DOWN, false, 0.5f, 2.5f);
    }

    protected PistonMat(final BlockFace face, final boolean extended)
    {
        super(PISTON_DOWN.name(), PISTON_DOWN.ordinal(), PISTON_DOWN.getMinecraftId(), face, extended, PISTON_DOWN.getHardness(), PISTON_DOWN.getBlastResistance());
    }

    protected PistonMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace facing, final boolean extended, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, facing, extended, hardness, blastResistance);
    }

    @Override
    public PistonMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PistonMat getExtended(final boolean extended)
    {
        return getPiston(this.facing, extended);
    }

    @Override
    public PistonMat getType(final BlockFace face, final boolean extended)
    {
        return getPiston(face, extended);
    }

    @Override
    public PistonMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public PistonMat getBlockFacing(final BlockFace face)
    {
        return getPiston(face, this.extended);
    }

    @Override
    public PistonMat getPowered(final boolean powered)
    {
        return this.getExtended(powered);
    }

    /**
     * Returns one of Piston sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Piston or null
     */
    public static PistonMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Piston sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Piston or null
     */
    public static PistonMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Piston sub-type based on {@link BlockFace} and extended state.
     * It will never return null.
     *
     * @param face     facing direction of piston.
     * @param extended if piston should be extended.
     *
     * @return sub-type of Piston
     */
    public static PistonMat getPiston(final BlockFace face, final boolean extended)
    {
        return getByID(combine(face, extended));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PistonMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public PistonMat[] types()
    {
        return PistonMat.pistonTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static PistonMat[] pistonTypes()
    {
        return byID.values(new PistonMat[byID.size()]);
    }

    static
    {
        PistonMat.register(PISTON_DOWN);
        PistonMat.register(PISTON_UP);
        PistonMat.register(PISTON_NORTH);
        PistonMat.register(PISTON_SOUTH);
        PistonMat.register(PISTON_WEST);
        PistonMat.register(PISTON_EAST);
        PistonMat.register(PISTON_DOWN_EXTENDED);
        PistonMat.register(PISTON_UP_EXTENDED);
        PistonMat.register(PISTON_NORTH_EXTENDED);
        PistonMat.register(PISTON_SOUTH_EXTENDED);
        PistonMat.register(PISTON_WEST_EXTENDED);
        PistonMat.register(PISTON_EAST_EXTENDED);
    }
}
