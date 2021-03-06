package org.diorite.material.blocks.others;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.Material;
import org.diorite.material.blocks.AttachableMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "SkullBlock" and all its subtypes.
 * <p>
 * NOTE: Will crash game when in inventory.
 */
public class SkullBlockMat extends BlockMaterialData implements AttachableMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 5;

    public static final SkullBlockMat SKULL_BLOCK_FLOOR      = new SkullBlockMat();
    public static final SkullBlockMat SKULL_BLOCK_WALL_NORTH = new SkullBlockMat("WALL_NORTH", BlockFace.NORTH);
    public static final SkullBlockMat SKULL_BLOCK_WALL_SOUTH = new SkullBlockMat("WALL_SOUTH", BlockFace.SOUTH);
    public static final SkullBlockMat SKULL_BLOCK_WALL_EAST  = new SkullBlockMat("WALL_EAST", BlockFace.EAST);
    public static final SkullBlockMat SKULL_BLOCK_WALL_WEST  = new SkullBlockMat("WALL_WEST", BlockFace.WEST);

    private static final Map<String, SkullBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SkullBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final BlockFace face;
    protected final boolean   onWall;

    @SuppressWarnings("MagicNumber")
    protected SkullBlockMat()
    {
        super("SKULL_BLOCK_FLOOR", 144, "minecraft:skull", "FLOOR", (byte) 0x01, 1, 5);
        this.face = null;
        this.onWall = false;
    }

    protected SkullBlockMat(final String enumName, final BlockFace face)
    {
        super(SKULL_BLOCK_FLOOR.name(), SKULL_BLOCK_FLOOR.ordinal(), SKULL_BLOCK_FLOOR.getMinecraftId(), enumName, combine(face), SKULL_BLOCK_FLOOR.getHardness(), SKULL_BLOCK_FLOOR.getBlastResistance());
        this.face = face;
        this.onWall = true;
    }

    protected SkullBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final boolean onWall, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
        this.onWall = onWall;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.SKULL;
    }

    @Override
    public SkullBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SkullBlockMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("onWall", this.onWall).toString();
    }

    /**
     * @return facing firection of skull, or null if skull is on ground.
     */
    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    /**
     * Returns sub-type of SkullBlock based on {@link BlockFace}.
     * Use null or {@link BlockFace#SELF} or {@link BlockFace#UP} to get floor type.
     *
     * @param face facing of SkullBlock
     *
     * @return sub-type of SkullBlock
     */
    @Override
    public SkullBlockMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Returns sub-type of SkullBlock based on attached {@link BlockFace}.
     * Use null or {@link BlockFace#SELF} or {@link BlockFace#DOWN} to get floor type.
     *
     * @param face facing of SkullBlock
     *
     * @return sub-type of SkullBlock
     */
    @Override
    public SkullBlockMat getAttachedFace(final BlockFace face)
    {
        return getByID(combine(face.getOppositeFace()));
    }

    private static byte combine(final BlockFace face)
    {
        if (face == null)
        {
            return 0x1;
        }
        switch (face)
        {
            case NORTH:
                return 0x2;
            case SOUTH:
                return 0x3;
            case EAST:
                return 0x4;
            case WEST:
                return 0x5;
            default:
                return 0x1;
        }
    }

    /**
     * Returns one of SkullBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of SkullBlock or null
     */
    public static SkullBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of SkullBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of SkullBlock or null
     */
    public static SkullBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of SkullBlock sub-type based on {@link BlockFace}.
     * Use null of {@link BlockFace#SELF} to get floor type.
     * It will never return null;
     *
     * @param face facing of SkullBlock
     *
     * @return sub-type of SkullBlock
     */
    public static SkullBlockMat getSkullBlock(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SkullBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public SkullBlockMat[] types()
    {
        return SkullBlockMat.skullBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static SkullBlockMat[] skullBlockTypes()
    {
        return byID.values(new SkullBlockMat[byID.size()]);
    }

    static
    {
        SkullBlockMat.register(SKULL_BLOCK_FLOOR);
        SkullBlockMat.register(SKULL_BLOCK_WALL_NORTH);
        SkullBlockMat.register(SKULL_BLOCK_WALL_SOUTH);
        SkullBlockMat.register(SKULL_BLOCK_WALL_EAST);
        SkullBlockMat.register(SKULL_BLOCK_WALL_WEST);
    }
}
