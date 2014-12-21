package trinarybrain.magia.naturalis.common.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import trinarybrain.magia.naturalis.common.MagiaNaturalis;
import trinarybrain.magia.naturalis.common.block.BlocksMN;
import trinarybrain.magia.naturalis.common.container.ContainerArcaneChest;
import trinarybrain.magia.naturalis.common.container.ContainerTranscribingTable;
import trinarybrain.magia.naturalis.common.entity.EntitiesMN;
import trinarybrain.magia.naturalis.common.item.ItemsMN;
import trinarybrain.magia.naturalis.common.tile.TileArcaneChest;
import trinarybrain.magia.naturalis.common.tile.TileTranscribingTable;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonProxy implements IGuiHandler
{
	public void preInit(FMLPreInitializationEvent event)
	{
		ItemsMN.initItems();
		BlocksMN.initBlocks();
	}

	public void init(FMLInitializationEvent event)
	{
		//MinecraftForge.EVENT_BUS.register(new EventHandlerPlayer());
		BlocksMN.initTileEntities();
		NetworkRegistry.INSTANCE.registerGuiHandler(MagiaNaturalis.instance, this);
	}

	public void postInit(FMLPostInitializationEvent event)
	{
		EntitiesMN.registerEntities();
		Research.init();
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(ID)
		{
		case 1: 
			return new ContainerTranscribingTable(player.inventory, (TileTranscribingTable) world.getTileEntity(x, y, z));
		case 2: 
			return new ContainerArcaneChest(player.inventory, (TileArcaneChest) world.getTileEntity(x, y, z));
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}
}
