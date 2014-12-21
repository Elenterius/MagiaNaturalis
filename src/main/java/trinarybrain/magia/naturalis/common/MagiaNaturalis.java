package trinarybrain.magia.naturalis.common;

import net.minecraft.creativetab.CreativeTabs;
import thaumcraft.common.Thaumcraft;
import trinarybrain.magia.naturalis.common.core.CommonProxy;
import trinarybrain.magia.naturalis.common.core.CreativeTab;
import trinarybrain.magia.naturalis.common.core.Log;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(name = ProjectInfo.NAME, modid = ProjectInfo.ID, version = ProjectInfo.VERSION, acceptedMinecraftVersions = ProjectInfo.MC_VERSION, dependencies = ProjectInfo.DEPENDENCIES)
public class MagiaNaturalis
{
	@Mod.Instance(ProjectInfo.ID)
	public static MagiaNaturalis instance;

	@SidedProxy(clientSide = ProjectInfo.CLIENT_PROXY, serverSide = ProjectInfo.COMMON_PROXY)
	public static CommonProxy proxy;
	public static thaumcraft.common.CommonProxy proxyTC4;

	public static CreativeTabs creativeTab = new CreativeTab(CreativeTabs.getNextID(), "magianaturalis");

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Log.initLog();
		proxyTC4 = Thaumcraft.proxy;
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init(event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}
}
