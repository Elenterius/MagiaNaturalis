package com.trinarybrain.magianaturalis.common;

import com.github.elenterius.magianaturalis.Tags;
import com.trinarybrain.magianaturalis.common.core.CommonProxy;
import com.trinarybrain.magianaturalis.common.core.CreativeTab;
import com.trinarybrain.magianaturalis.common.core.Log;
import com.trinarybrain.magianaturalis.common.network.PacketHandler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import thaumcraft.common.Thaumcraft;


@Mod(name = Tags.MOD_NAME, modid = MagiaNaturalis.MOD_ID, version = Tags.MOD_VERSION, acceptedMinecraftVersions = Tags.MC_VERSIONS, dependencies = Tags.MOD_DEPENDENCIES)
public class MagiaNaturalis {

    public static final String MOD_ID = Tags.MOD_ID;
    public static final String COMMON_PROXY = "com.trinarybrain.magianaturalis.common.core.CommonProxy";
    public static final String CLIENT_PROXY = "com.trinarybrain.magianaturalis.client.core.ClientProxy";

    @Mod.Instance(MagiaNaturalis.MOD_ID)
    public static MagiaNaturalis instance;

    @SidedProxy(clientSide = MagiaNaturalis.CLIENT_PROXY, serverSide = MagiaNaturalis.COMMON_PROXY)
    public static CommonProxy proxy;
    public static thaumcraft.common.CommonProxy proxyTC4;

    public static CreativeTabs creativeTab = new CreativeTab(CreativeTabs.getNextID(), Reference.ID);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Log.initLog();
        proxyTC4 = Thaumcraft.proxy;
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        PacketHandler.initPackets();
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

}
