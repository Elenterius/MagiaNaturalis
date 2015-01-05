package trinarybrain.magia.naturalis.client.core;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import trinarybrain.magia.naturalis.client.gui.GuiArcaneChest;
import trinarybrain.magia.naturalis.client.gui.GuiEvilTrunk;
import trinarybrain.magia.naturalis.client.gui.GuiTranscribingTable;
import trinarybrain.magia.naturalis.client.render.block.BlockJarRenderer;
import trinarybrain.magia.naturalis.client.render.block.BlockRenderer;
import trinarybrain.magia.naturalis.client.render.entity.RenderEvilTrunk;
import trinarybrain.magia.naturalis.client.render.entity.RenderTaintBreeder;
import trinarybrain.magia.naturalis.client.render.item.RenderItemEvilTrunkSpawner;
import trinarybrain.magia.naturalis.client.render.tile.TileArcaneChestRenderer;
import trinarybrain.magia.naturalis.client.render.tile.TileBannerCustomRenderer;
import trinarybrain.magia.naturalis.client.render.tile.TileJarPrisonRenderer;
import trinarybrain.magia.naturalis.client.render.tile.TileTranscribingTableRenderer;
import trinarybrain.magia.naturalis.common.core.CommonProxy;
import trinarybrain.magia.naturalis.common.core.KeyHandler;
import trinarybrain.magia.naturalis.common.entity.EntityEvilTrunk;
import trinarybrain.magia.naturalis.common.entity.taint.EntityTaintBreeder;
import trinarybrain.magia.naturalis.common.item.ItemsMN;
import trinarybrain.magia.naturalis.common.tile.TileArcaneChest;
import trinarybrain.magia.naturalis.common.tile.TileBannerCustom;
import trinarybrain.magia.naturalis.common.tile.TileJarPrison;
import trinarybrain.magia.naturalis.common.tile.TileTranscribingTable;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy
{
	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		this.registerRenderer();
		EventHandlerRender.register();
		FMLCommonHandler.instance().bus().register(new KeyHandler());
	}

	public void registerRenderer()
	{
		this.registerBlockRenderer(new BlockRenderer());
		this.registerTileEntitySpecialRenderer(TileTranscribingTable.class, new TileTranscribingTableRenderer());
		this.registerTileEntitySpecialRenderer(TileArcaneChest.class, new TileArcaneChestRenderer());
		this.registerTileEntitySpecialRenderer(TileBannerCustom.class, new TileBannerCustomRenderer());

		this.registerBlockRenderer(new BlockJarRenderer());
		this.registerTileEntitySpecialRenderer(TileJarPrison.class, new TileJarPrisonRenderer());
		
		MinecraftForgeClient.registerItemRenderer(ItemsMN.evilTrunkSpawner, new RenderItemEvilTrunkSpawner());

		RenderingRegistry.registerEntityRenderingHandler(EntityTaintBreeder.class, new RenderTaintBreeder());
		RenderingRegistry.registerEntityRenderingHandler(EntityEvilTrunk.class, new RenderEvilTrunk());
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
