package com.trinarybrain.magianaturalis.common.block;

import com.trinarybrain.magianaturalis.common.Reference;
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
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class BlocksMN
{
	public static Block transcribingTable;
	public static Block arcaneChest;
	public static Block jarPrison;
	public static Block arcaneWood;
	public static Block banner;
	public static Block geoMorpher;

	public static void initBlocks()
	{
		transcribingTable = new BlockTranscribingTable(); registerBlock(transcribingTable, "transcribingTable");

		arcaneChest = new BlockArcaneChest(); arcaneChest.setBlockName(Reference.ID + ":" + "arcaneChest");
		GameRegistry.registerBlock(arcaneChest, BlockArcaneChestItem.class, "block." + "arcaneChest");

		jarPrison = new BlockJarPrison(); jarPrison.setBlockName(Reference.ID + ":" + "jarPrison");
		GameRegistry.registerBlock(jarPrison, BlockJarPrisonItem.class, "block." + "jarPrison");

		arcaneWood = new BlockArcaneWood(); arcaneWood.setBlockName(Reference.ID + ":" + "arcaneWood");
		GameRegistry.registerBlock(arcaneWood, BlockArcaneWoodItem.class, "block." + "arcaneWood");

		banner = new BlockBanner(); banner.setBlockName(Reference.ID + ":" + "banner");
		GameRegistry.registerBlock(banner, BlockBannerItem.class, "block." + "banner");

		geoMorpher = new BlockGeoMorpher(); registerBlock(geoMorpher, "geoMorpher");

		registerOreDict();
	}

	private static void registerOreDict()
	{
		OreDictionary.registerOre("plankWood", new ItemStack(BlocksMN.arcaneWood, 1, 0));
		OreDictionary.registerOre("plankWood", new ItemStack(BlocksMN.arcaneWood, 1, 1));
		OreDictionary.registerOre("plankWood", new ItemStack(BlocksMN.arcaneWood, 1, 2));
	}

	private static void registerBlock(Block block, String str)
	{
		block.setBlockName(Reference.ID + ":" + str);
		GameRegistry.registerBlock(block, "block." + str);
		//TODO: Remove "block." prefix for compatibility with 1.8 port?
	}

	public static void initTileEntities()
	{
		GameRegistry.registerTileEntity(TileTranscribingTable.class, "tile." + "transcribingTable");
		GameRegistry.registerTileEntity(TileArcaneChest.class, "tile." + "arcaneChest");
		GameRegistry.registerTileEntity(TileJarPrison.class, "tile." + "jarPrison");
		GameRegistry.registerTileEntity(TileBannerCustom.class, "tile." + "banner");
		GameRegistry.registerTileEntity(TileGeoMorpher.class, "tile." + "geoMorpher");
		//TODO: Remove "tile." prefix for compatibility with 1.8 port?
	}
}
