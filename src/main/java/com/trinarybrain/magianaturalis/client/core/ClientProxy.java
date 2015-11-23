package com.trinarybrain.magianaturalis.client.core;

import com.trinarybrain.magianaturalis.client.gui.GuiArcaneChest;
import com.trinarybrain.magianaturalis.client.gui.GuiEvilTrunk;
import com.trinarybrain.magianaturalis.client.gui.GuiTranscribingTable;
import com.trinarybrain.magianaturalis.client.render.block.BlockJarRenderer;
import com.trinarybrain.magianaturalis.client.render.block.BlockRenderer;
import com.trinarybrain.magianaturalis.client.render.entity.RenderEvilTrunk;
import com.trinarybrain.magianaturalis.client.render.entity.RenderTaintBreeder;
import com.trinarybrain.magianaturalis.client.render.item.RenderItemEvilTrunkSpawner;
import com.trinarybrain.magianaturalis.client.render.tile.TileArcaneChestRenderer;
import com.trinarybrain.magianaturalis.client.render.tile.TileBannerCustomRenderer;
import com.trinarybrain.magianaturalis.client.render.tile.TileGeoMorpherRenderer;
import com.trinarybrain.magianaturalis.client.render.tile.TileJarPrisonRenderer;
import com.trinarybrain.magianaturalis.client.render.tile.TileTranscribingTableRenderer;
import com.trinarybrain.magianaturalis.common.core.CommonProxy;
import com.trinarybrain.magianaturalis.common.core.KeyHandler;
import com.trinarybrain.magianaturalis.common.entity.EntityEvilTrunk;
import com.trinarybrain.magianaturalis.common.entity.EntityZombieExtended;
import com.trinarybrain.magianaturalis.common.entity.taint.EntityTaintBreeder;
import com.trinarybrain.magianaturalis.common.item.ItemsMN;
import com.trinarybrain.magianaturalis.common.tile.TileArcaneChest;
import com.trinarybrain.magianaturalis.common.tile.TileBannerCustom;
import com.trinarybrain.magianaturalis.common.tile.TileGeoMorpher;
import com.trinarybrain.magianaturalis.common.tile.TileJarPrison;
import com.trinarybrain.magianaturalis.common.tile.TileTranscribingTable;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{
	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		registerRenderer();
		EventHandlerRender.register();
		FMLCommonHandler.instance().bus().register(new KeyHandler());
	}

	public void registerRenderer()
	{
		registerBlockRenderer(new BlockRenderer());
		registerTileEntitySpecialRenderer(TileTranscribingTable.class, new TileTranscribingTableRenderer());
		registerTileEntitySpecialRenderer(TileArcaneChest.class, new TileArcaneChestRenderer());
		registerTileEntitySpecialRenderer(TileBannerCustom.class, new TileBannerCustomRenderer());
		registerBlockRenderer(new BlockJarRenderer());
		registerTileEntitySpecialRenderer(TileJarPrison.class, new TileJarPrisonRenderer());
		registerTileEntitySpecialRenderer(TileGeoMorpher.class, new TileGeoMorpherRenderer());

		MinecraftForgeClient.registerItemRenderer(ItemsMN.evilTrunkSpawner, new RenderItemEvilTrunkSpawner());

		RenderingRegistry.registerEntityRenderingHandler(EntityTaintBreeder.class, new RenderTaintBreeder());
		RenderingRegistry.registerEntityRenderingHandler(EntityEvilTrunk.class, new RenderEvilTrunk());
		RenderingRegistry.registerEntityRenderingHandler(EntityZombieExtended.class, new RenderZombie());
	}

	public void registerTileEntitySpecialRenderer(Class clazz, TileEntitySpecialRenderer tESR)
	{
		ClientRegistry.bindTileEntitySpecialRenderer(clazz, tESR);
	}

	public void registerBlockRenderer(ISimpleBlockRenderingHandler iSBR)
	{
		RenderingRegistry.registerBlockHandler(iSBR);
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(ID)
		{
		case 1:
			return new GuiTranscribingTable(player.inventory, (TileTranscribingTable) world.getTileEntity(x, y, z));
		case 2:
			return new GuiArcaneChest(player.inventory, (TileArcaneChest) world.getTileEntity(x, y, z));
		case 3:
			return new GuiEvilTrunk(player, (EntityEvilTrunk) ((WorldClient)world).getEntityByID(x));
		default:
			return null;
		}
	}
}
