package com.trinarybrain.magianaturalis.init;

import com.trinarybrain.magianaturalis.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.block.*;
import com.trinarybrain.magianaturalis.common.block.item.BlockArcaneChestItem;
import com.trinarybrain.magianaturalis.common.block.item.BlockArcaneWoodItem;
import com.trinarybrain.magianaturalis.common.block.item.BlockBannerItem;
import com.trinarybrain.magianaturalis.common.block.item.BlockJarPrisonItem;
import com.trinarybrain.magianaturalis.common.tile.*;
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
        transcribingTable = registerBlock("transcribingTable", BlockTranscribingTable::new);
        arcaneChest = registerBlock("arcaneChest", BlockArcaneChestItem.class, BlockArcaneChest::new);
        jarPrison = registerBlock("jarPrison", BlockJarPrisonItem.class, BlockJarPrison::new);
        arcaneWood = registerBlock("arcaneWood", BlockArcaneWoodItem.class, BlockArcaneWood::new);
        banner = registerBlock("banner", BlockBannerItem.class, BlockBanner::new);
        geoMorpher = registerBlock("geoMorpher", BlockGeoMorpher::new);

        registerOreDict();
    }

    private static <T extends Block, V extends ItemBlock> T registerBlock(String name, Class<V> blockItem, Supplier<T> factory) {
        T block = factory.get();
        block.setBlockName(MagiaNaturalis.translationKey(name));
        block.setCreativeTab(MagiaNaturalis.CREATIVE_TAB);

        GameRegistry.registerBlock(block, blockItem, name);

        return block;
    }

    private static <T extends Block> T registerBlock(String name, Supplier<T> factory) {
        T block = factory.get();
        block.setBlockName(MagiaNaturalis.translationKey(name));
        block.setCreativeTab(MagiaNaturalis.CREATIVE_TAB);

        GameRegistry.registerBlock(block, name);

        return block;
    }

    private static void registerOreDict() {
        OreDictionary.registerOre("plankWood", new ItemStack(MNBlocks.arcaneWood, 1, 0));
        OreDictionary.registerOre("plankWood", new ItemStack(MNBlocks.arcaneWood, 1, 1));
        OreDictionary.registerOre("plankWood", new ItemStack(MNBlocks.arcaneWood, 1, 2));
    }

    public static void initTileEntities() {
        GameRegistry.registerTileEntity(TileTranscribingTable.class, MagiaNaturalis.rlString("transcribingTable"));
        GameRegistry.registerTileEntity(TileArcaneChest.class, MagiaNaturalis.rlString("arcaneChest"));
        GameRegistry.registerTileEntity(TileJarPrison.class, MagiaNaturalis.rlString("jarPrison"));
        GameRegistry.registerTileEntity(TileBannerCustom.class, MagiaNaturalis.rlString("banner"));
        GameRegistry.registerTileEntity(TileGeoMorpher.class, MagiaNaturalis.rlString("geoMorpher"));
    }

}
