package com.trinarybrain.magianaturalis.common.block;

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
import com.trinarybrain.magianaturalis.common.util.ResourceUtil;

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

		arcaneChest = new BlockArcaneChest(); arcaneChest.setBlockName(ResourceUtil.PREFIX + NameUtil.ARCANE_CHEST);
		GameRegistry.registerBlock(arcaneChest, BlockArcaneChestItem.class, ResourceUtil.TAG_BLOCK + NameUtil.ARCANE_CHEST);

		jarPrison = new BlockJarPrison(); jarPrison.setBlockName(ResourceUtil.PREFIX + NameUtil.JAR_PRISON);
		GameRegistry.registerBlock(jarPrison, BlockJarPrisonItem.class, ResourceUtil.TAG_BLOCK + NameUtil.JAR_PRISON);

		arcaneWood = new BlockArcaneWood(); arcaneWood.setBlockName(ResourceUtil.PREFIX + NameUtil.ARCANE_WOOD);
		GameRegistry.registerBlock(arcaneWood, BlockArcaneWoodItem.class, ResourceUtil.TAG_BLOCK + NameUtil.ARCANE_WOOD);

		banner = new BlockBanner(); banner.setBlockName(ResourceUtil.PREFIX + NameUtil.BANNER);
		GameRegistry.registerBlock(banner, BlockBannerItem.class, ResourceUtil.TAG_BLOCK + NameUtil.BANNER);

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
		block.setBlockName(ResourceUtil.PREFIX + str);
		GameRegistry.registerBlock(block, ResourceUtil.TAG_BLOCK + str);
	}

	public static void initTileEntities()
	{
		GameRegistry.registerTileEntity(TileTranscribingTable.class, ResourceUtil.TAG_TILE + NameUtil.TRANS_TABLE);
		GameRegistry.registerTileEntity(TileArcaneChest.class, ResourceUtil.TAG_TILE + NameUtil.ARCANE_CHEST);
		GameRegistry.registerTileEntity(TileJarPrison.class, ResourceUtil.TAG_TILE + NameUtil.JAR_PRISON);
		GameRegistry.registerTileEntity(TileBannerCustom.class, ResourceUtil.TAG_TILE + NameUtil.BANNER);
		GameRegistry.registerTileEntity(TileGeoMorpher.class, ResourceUtil.TAG_TILE + NameUtil.GEO_MORPHER);
	}
}
