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
import com.trinarybrain.magianaturalis.common.util.NameUtil;

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
		transcribingTable = new BlockTranscribingTable(); registerBlock(transcribingTable, NameUtil.TRANS_TABLE);

		arcaneChest = new BlockArcaneChest(); arcaneChest.setBlockName(Reference.ID + ":" + NameUtil.ARCANE_CHEST);
		GameRegistry.registerBlock(arcaneChest, BlockArcaneChestItem.class, "block." + NameUtil.ARCANE_CHEST);

		jarPrison = new BlockJarPrison(); jarPrison.setBlockName(Reference.ID + ":" + NameUtil.JAR_PRISON);
		GameRegistry.registerBlock(jarPrison, BlockJarPrisonItem.class, "block." + NameUtil.JAR_PRISON);

		arcaneWood = new BlockArcaneWood(); arcaneWood.setBlockName(Reference.ID + ":" + NameUtil.ARCANE_WOOD);
		GameRegistry.registerBlock(arcaneWood, BlockArcaneWoodItem.class, "block." + NameUtil.ARCANE_WOOD);

		banner = new BlockBanner(); banner.setBlockName(Reference.ID + ":" + NameUtil.BANNER);
		GameRegistry.registerBlock(banner, BlockBannerItem.class, "block." + NameUtil.BANNER);

		geoMorpher = new BlockGeoMorpher(); registerBlock(geoMorpher, NameUtil.GEO_MORPHER);

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
		GameRegistry.registerTileEntity(TileTranscribingTable.class, "tile." + NameUtil.TRANS_TABLE);
		GameRegistry.registerTileEntity(TileArcaneChest.class, "tile." + NameUtil.ARCANE_CHEST);
		GameRegistry.registerTileEntity(TileJarPrison.class, "tile." + NameUtil.JAR_PRISON);
		GameRegistry.registerTileEntity(TileBannerCustom.class, "tile." + NameUtil.BANNER);
		GameRegistry.registerTileEntity(TileGeoMorpher.class, "tile." + NameUtil.GEO_MORPHER);
		//TODO: Remove "tile." prefix for compatibility with 1.8 port?
	}
}
