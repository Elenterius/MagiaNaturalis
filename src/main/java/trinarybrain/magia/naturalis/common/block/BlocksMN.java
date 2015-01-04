package trinarybrain.magia.naturalis.common.block;

import net.minecraft.block.Block;
import trinarybrain.magia.naturalis.common.block.item.BlockArcaneChestItem;
import trinarybrain.magia.naturalis.common.block.item.BlockArcaneWoodItem;
import trinarybrain.magia.naturalis.common.block.item.BlockBannerItem;
import trinarybrain.magia.naturalis.common.block.item.BlockJarPrisonItem;
import trinarybrain.magia.naturalis.common.tile.TileArcaneChest;
import trinarybrain.magia.naturalis.common.tile.TileBannerCustom;
import trinarybrain.magia.naturalis.common.tile.TileJarPrison;
import trinarybrain.magia.naturalis.common.tile.TileTranscribingTable;
import trinarybrain.magia.naturalis.common.util.NameUtil;
import trinarybrain.magia.naturalis.common.util.ResourceUtil;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlocksMN
{
	public static Block transcribingTable;
	public static Block arcaneChest;
	public static Block jarPrison;
	public static Block arcaneWood;
	public static Block banner;

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
	}
}
