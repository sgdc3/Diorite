package org.diorite.material.blocks.stony.ore;

import java.util.Map;

import org.diorite.material.Material;
import org.diorite.material.blocks.stony.oreblocks.OreBlockMat;
import org.diorite.material.items.OreItemMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "EmeraldOre" and all its subtypes.
 */
public class EmeraldOreMat extends OreMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final EmeraldOreMat EMERALD_ORE = new EmeraldOreMat();

    private static final Map<String, EmeraldOreMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<EmeraldOreMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected EmeraldOreMat()
    {
        super("EMERALD_ORE", 129, "minecraft:emerald_ore", "EMERALD_ORE", (byte) 0x00, Material.EMERALD, Material.EMERALD_BLOCK, 3, 15);
    }

    protected EmeraldOreMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final OreItemMat item, final OreBlockMat block, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, item, block, hardness, blastResistance);
    }

    @Override
    public EmeraldOreMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public EmeraldOreMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of EmeraldOre sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of EmeraldOre or null
     */
    public static EmeraldOreMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of EmeraldOre sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of EmeraldOre or null
     */
    public static EmeraldOreMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final EmeraldOreMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public EmeraldOreMat[] types()
    {
        return EmeraldOreMat.emeraldOreTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static EmeraldOreMat[] emeraldOreTypes()
    {
        return byID.values(new EmeraldOreMat[byID.size()]);
    }

    static
    {
        EmeraldOreMat.register(EMERALD_ORE);
    }
}
