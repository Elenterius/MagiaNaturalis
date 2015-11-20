package com.trinarybrain.magianaturalis.common;

import com.trinarybrain.magianaturalis.common.core.CommonProxy;
import com.trinarybrain.magianaturalis.common.core.CreativeTab;
import com.trinarybrain.magianaturalis.common.core.Log;
import com.trinarybrain.magianaturalis.common.network.NetworkHandler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import thaumcraft.common.Thaumcraft;

@Mod(name = Reference.NAME, modid = Reference.ID, version = Reference.VERSION, acceptedMinecraftVersions = Reference.MC_VERSION, dependencies = Reference.DEPENDENCIES)
public class MagiaNaturalis
{
	@Mod.Instance(Reference.ID)
	public static MagiaNaturalis instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
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
		NetworkHandler.instance = new NetworkHandler("MagiaNaturC");
		proxy.init(event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}
}
