package com.github.elenterius.magianaturalis.init;

import com.github.elenterius.magianaturalis.MagiaNaturalis;
import com.github.elenterius.magianaturalis.block.*;
import com.github.elenterius.magianaturalis.block.item.BlockArcaneChestItem;
import com.github.elenterius.magianaturalis.block.item.BlockArcaneWoodItem;
import com.github.elenterius.magianaturalis.block.item.BlockBannerItem;
import com.github.elenterius.magianaturalis.block.item.BlockJarPrisonItem;
import com.github.elenterius.magianaturalis.tile.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.function.Supplier;

public class MNBlocks {

    public static Block transcribingTable;
    public static Block arcaneChest;
    public static Block jarPrison;
    public static Block arcaneWood;
    public static Block banner;
    public static Block geoMorpher;

    public static void initBlocks() {
        transcribingTable = registerBlock("transcribing_table", BlockTranscribingTable::new);
        arcaneChest = registerBlock("arcane_chest", BlockArcaneChestItem.class, BlockArcaneChest::new);
        jarPrison = registerBlock("prison_jar", BlockJarPrisonItem.class, BlockJarPrison::new);
        arcaneWood = registerBlock("arcane_wood", BlockArcaneWoodItem.class, BlockArcaneWood::new);
        banner = registerBlock("banner", BlockBannerItem.class, BlockBanner::new);
        geoMorpher = registerBlock("geo_morpher", BlockGeoMorpher::new);

        registerOreDict();
    }

    private static <T extends Block, V extends ItemBlock> T registerBlock(String name, Class<V> blockItem, Supplier<T> factory) {
        T block = factory.get();
        block.setBlockName(MagiaNaturalis.translationKey(name));
        block.setCreativeTab(MNCreativeTabs.MAIN);

        GameRegistry.registerBlock(block, blockItem, name);

        return block;
    }

    private static <T extends Block> T registerBlock(String name, Supplier<T> factory) {
        T block = factory.get();
        block.setBlockName(MagiaNaturalis.translationKey(name));
        block.setCreativeTab(MNCreativeTabs.MAIN);

        GameRegistry.registerBlock(block, name);

        return block;
    }

    private static void registerOreDict() {
        OreDictionary.registerOre("plankWood", new ItemStack(MNBlocks.arcaneWood, 1, 0));
        OreDictionary.registerOre("plankWood", new ItemStack(MNBlocks.arcaneWood, 1, 1));
        OreDictionary.registerOre("plankWood", new ItemStack(MNBlocks.arcaneWood, 1, 2));
    }

    public static void initTileEntities() {
        GameRegistry.registerTileEntity(TileTranscribingTable.class, MagiaNaturalis.rlString("transcribing_table"));
        GameRegistry.registerTileEntity(TileArcaneChest.class, MagiaNaturalis.rlString("arcane_chest"));
        GameRegistry.registerTileEntity(TileJarPrison.class, MagiaNaturalis.rlString("prison_jar"));
        GameRegistry.registerTileEntity(TileBannerCustom.class, MagiaNaturalis.rlString("banner"));
        GameRegistry.registerTileEntity(TileGeoMorpher.class, MagiaNaturalis.rlString("geo_morpher"));
    }

}
