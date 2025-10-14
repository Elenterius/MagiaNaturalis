package com.trinarybrain.magianaturalis.common.block;

import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.block.item.BlockArcaneChestItem;
import com.trinarybrain.magianaturalis.common.block.item.BlockArcaneWoodItem;
import com.trinarybrain.magianaturalis.common.block.item.BlockBannerItem;
import com.trinarybrain.magianaturalis.common.block.item.BlockJarPrisonItem;
import com.trinarybrain.magianaturalis.common.tile.TileArcaneChest;
import com.trinarybrain.magianaturalis.common.tile.TileBannerCustom;
import com.trinarybrain.magianaturalis.common.tile.TileGeoMorpher;
import com.trinarybrain.magianaturalis.common.tile.TileJarPrison;
import com.trinarybrain.magianaturalis.common.tile.TileTranscribingTable;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.function.Supplier;

public class BlocksMN {

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

    private static <T extends Block, V extends ItemBlock> T registerBlock(String name, Class<V> itemClazz, Supplier<T> factory) {
        T block = factory.get();
        block.setBlockName(MagiaNaturalis.MOD_ID + ":" + name);
        block.setCreativeTab(MagiaNaturalis.CREATIVE_TAB);

        GameRegistry.registerBlock(block, itemClazz, "block." + name);

        return block;
    }

    private static <T extends Block> T registerBlock(String name, Supplier<T> factory) {
        T block = factory.get();
        block.setBlockName(MagiaNaturalis.MOD_ID + ":" + name);
        block.setCreativeTab(MagiaNaturalis.CREATIVE_TAB);

        GameRegistry.registerBlock(block, "block." + name);

        return block;
    }

    private static void registerOreDict() {
        OreDictionary.registerOre("plankWood", new ItemStack(BlocksMN.arcaneWood, 1, 0));
        OreDictionary.registerOre("plankWood", new ItemStack(BlocksMN.arcaneWood, 1, 1));
        OreDictionary.registerOre("plankWood", new ItemStack(BlocksMN.arcaneWood, 1, 2));
    }

    public static void initTileEntities() {
        GameRegistry.registerTileEntity(TileTranscribingTable.class, "tile." + "transcribingTable");
        GameRegistry.registerTileEntity(TileArcaneChest.class, "tile." + "arcaneChest");
        GameRegistry.registerTileEntity(TileJarPrison.class, "tile." + "jarPrison");
        GameRegistry.registerTileEntity(TileBannerCustom.class, "tile." + "banner");
        GameRegistry.registerTileEntity(TileGeoMorpher.class, "tile." + "geoMorpher");
    }

}
