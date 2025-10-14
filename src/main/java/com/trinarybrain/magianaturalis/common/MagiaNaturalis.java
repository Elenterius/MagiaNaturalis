package com.trinarybrain.magianaturalis.common;

import com.github.elenterius.magianaturalis.Tags;
import com.trinarybrain.magianaturalis.common.core.CommonProxy;
import com.trinarybrain.magianaturalis.common.core.CreativeTab;
import com.trinarybrain.magianaturalis.common.network.PacketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thaumcraft.common.Thaumcraft;


@Mod(name = Tags.MOD_NAME, modid = MagiaNaturalis.MOD_ID, version = Tags.MOD_VERSION, acceptedMinecraftVersions = Tags.MC_VERSIONS, dependencies = Tags.MOD_DEPENDENCIES)
public class MagiaNaturalis {

    public static final String MOD_ID = Tags.MOD_ID;
    public static final String COMMON_PROXY = "com.trinarybrain.magianaturalis.common.core.CommonProxy";
    public static final String CLIENT_PROXY = "com.trinarybrain.magianaturalis.client.core.ClientProxy";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Mod.Instance(MOD_ID)
    public static MagiaNaturalis instance;

    @SidedProxy(clientSide = MagiaNaturalis.CLIENT_PROXY, serverSide = MagiaNaturalis.COMMON_PROXY)
    public static CommonProxy proxy;
    public static thaumcraft.common.CommonProxy proxyTC4;

    public static final CreativeTabs CREATIVE_TAB = new CreativeTab(CreativeTabs.getNextID(), MOD_ID);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
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

    public static String translationKey(String name) {
        return MagiaNaturalis.MOD_ID + "." + name;
    }

    public static String rlString(String path) {
        return MagiaNaturalis.MOD_ID + ":" + path;
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MagiaNaturalis.MOD_ID, path);
    }

}
