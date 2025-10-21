package com.github.elenterius.magianaturalis.init.client;

import com.github.elenterius.magianaturalis.block.banner.CustomBannerBlockEntity;
import com.github.elenterius.magianaturalis.block.chest.ArcaneChestBlockEntity;
import com.github.elenterius.magianaturalis.block.geopylon.GeoPylonBlockEntity;
import com.github.elenterius.magianaturalis.block.jar.PrisonJarBlockEntity;
import com.github.elenterius.magianaturalis.block.table.TranscribingTableBlockEntity;
import com.github.elenterius.magianaturalis.client.gui.ArcaneChestGui;
import com.github.elenterius.magianaturalis.client.gui.EvilTrunkGui;
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
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
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
        MNKeyBindings.register();
        KeyEventHandler.register();
    }

    public void registerRenderer() {
        registerBlockRenderer(new BlockEntityRenderer());
        registerTileEntitySpecialRenderer(TranscribingTableBlockEntity.class, new TileTranscribingTableRenderer());
        registerTileEntitySpecialRenderer(ArcaneChestBlockEntity.class, new TileArcaneChestRenderer());
        registerTileEntitySpecialRenderer(CustomBannerBlockEntity.class, new TileBannerCustomRenderer());
        registerBlockRenderer(new BlockJarRenderer());
        registerTileEntitySpecialRenderer(PrisonJarBlockEntity.class, new TileJarPrisonRenderer());
        registerTileEntitySpecialRenderer(GeoPylonBlockEntity.class, new TileGeoMorpherRenderer());

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
                return new GuiTranscribingTable(player.inventory, (TranscribingTableBlockEntity) world.getTileEntity(x, y, z));
            case 2:
                return new ArcaneChestGui(player.inventory, (ArcaneChestBlockEntity) world.getTileEntity(x, y, z));
            case 3:
                return new EvilTrunkGui(player, (EntityEvilTrunk) world.getEntityByID(x));
            default:
                return null;
        }
    }

}
