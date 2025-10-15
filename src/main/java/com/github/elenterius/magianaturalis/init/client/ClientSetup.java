package com.github.elenterius.magianaturalis.init.client;

import com.github.elenterius.magianaturalis.client.gui.GuiArcaneChest;
import com.github.elenterius.magianaturalis.client.gui.GuiEvilTrunk;
import com.github.elenterius.magianaturalis.client.gui.GuiTranscribingTable;
import com.github.elenterius.magianaturalis.client.render.RenderEventHandler;
import com.github.elenterius.magianaturalis.client.render.block.BlockEntityRenderer;
import com.github.elenterius.magianaturalis.client.render.block.BlockJarRenderer;
import com.github.elenterius.magianaturalis.client.render.entity.breeder.TaintBreederRenderer;
import com.github.elenterius.magianaturalis.client.render.entity.trunk.EvilTrunkRenderer;
import com.github.elenterius.magianaturalis.client.render.item.RenderItemEvilTrunkSpawner;
import com.github.elenterius.magianaturalis.client.render.tile.*;
import com.github.elenterius.magianaturalis.entity.EntityEvilTrunk;
import com.github.elenterius.magianaturalis.entity.EntityZombieExtended;
import com.github.elenterius.magianaturalis.entity.taint.EntityTaintBreeder;
import com.github.elenterius.magianaturalis.init.CommonSetup;
import com.github.elenterius.magianaturalis.init.MNItems;
import com.github.elenterius.magianaturalis.tile.*;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientSetup extends CommonSetup {

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        registerRenderer();
        RenderEventHandler.register();
        FMLCommonHandler.instance().bus().register(new KeyEventHandler());
    }

    public void registerRenderer() {
        registerBlockRenderer(new BlockEntityRenderer());
        registerTileEntitySpecialRenderer(TileTranscribingTable.class, new TileTranscribingTableRenderer());
        registerTileEntitySpecialRenderer(TileArcaneChest.class, new TileArcaneChestRenderer());
        registerTileEntitySpecialRenderer(TileBannerCustom.class, new TileBannerCustomRenderer());
        registerBlockRenderer(new BlockJarRenderer());
        registerTileEntitySpecialRenderer(TileJarPrison.class, new TileJarPrisonRenderer());
        registerTileEntitySpecialRenderer(TileGeoMorpher.class, new TileGeoMorpherRenderer());

        MinecraftForgeClient.registerItemRenderer(MNItems.evilTrunkSpawner, new RenderItemEvilTrunkSpawner());

        RenderingRegistry.registerEntityRenderingHandler(EntityTaintBreeder.class, new TaintBreederRenderer());
        RenderingRegistry.registerEntityRenderingHandler(EntityEvilTrunk.class, new EvilTrunkRenderer());
        RenderingRegistry.registerEntityRenderingHandler(EntityZombieExtended.class, new RenderZombie());
        //		RenderingRegistry.registerEntityRenderingHandler(EntityTaintman.class, new RenderTaintman());
    }

    public void registerTileEntitySpecialRenderer(Class<? extends TileEntity> clazz, TileEntitySpecialRenderer renderer) {
        ClientRegistry.bindTileEntitySpecialRenderer(clazz, renderer);
    }

    public void registerBlockRenderer(ISimpleBlockRenderingHandler renderer) {
        RenderingRegistry.registerBlockHandler(renderer);
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case 1:
                return new GuiTranscribingTable(player.inventory, (TileTranscribingTable) world.getTileEntity(x, y, z));
            case 2:
                return new GuiArcaneChest(player.inventory, (TileArcaneChest) world.getTileEntity(x, y, z));
            case 3:
                return new GuiEvilTrunk(player, (EntityEvilTrunk) world.getEntityByID(x));
            default:
                return null;
        }
    }

}
