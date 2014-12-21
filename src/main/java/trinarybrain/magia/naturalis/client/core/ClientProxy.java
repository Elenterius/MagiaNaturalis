package trinarybrain.magia.naturalis.client.core;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import trinarybrain.magia.naturalis.client.gui.GuiArcaneChest;
import trinarybrain.magia.naturalis.client.gui.GuiTranscribingTable;
import trinarybrain.magia.naturalis.client.render.block.BlockJarRenderer;
import trinarybrain.magia.naturalis.client.render.block.BlockRenderer;
import trinarybrain.magia.naturalis.client.render.entity.RenderTaintBreeder;
import trinarybrain.magia.naturalis.client.render.tile.TileArcaneChestRenderer;
import trinarybrain.magia.naturalis.client.render.tile.TileJarPrisonRenderer;
import trinarybrain.magia.naturalis.client.render.tile.TileTranscribingTableRenderer;
import trinarybrain.magia.naturalis.common.core.CommonProxy;
import trinarybrain.magia.naturalis.common.entity.taint.EntityTaintBreeder;
import trinarybrain.magia.naturalis.common.tile.TileArcaneChest;
import trinarybrain.magia.naturalis.common.tile.TileJarPrison;
import trinarybrain.magia.naturalis.common.tile.TileTranscribingTable;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy
{
	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		this.registerRenderer();
		EventHandlerRender.register();
	}

	public void registerRenderer()
	{
		this.registerBlockRenderer(new BlockRenderer());
		this.registerTileEntitySpecialRenderer(TileTranscribingTable.class, new TileTranscribingTableRenderer());
		this.registerTileEntitySpecialRenderer(TileArcaneChest.class, new TileArcaneChestRenderer());
		
		this.registerBlockRenderer(new BlockJarRenderer());
		this.registerTileEntitySpecialRenderer(TileJarPrison.class, new TileJarPrisonRenderer());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityTaintBreeder.class, new RenderTaintBreeder());
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
		default:
			return null;
		}
	}
}
