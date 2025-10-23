package com.github.elenterius.magianaturalis.init;

import com.github.elenterius.magianaturalis.MagiaNaturalis;
import com.github.elenterius.magianaturalis.block.chest.ArcaneChestBlockEntity;
import com.github.elenterius.magianaturalis.block.table.TranscribingTableBlockEntity;
import com.github.elenterius.magianaturalis.container.ContainerArcaneChest;
import com.github.elenterius.magianaturalis.container.ContainerEvilTrunk;
import com.github.elenterius.magianaturalis.container.ContainerTranscribingTable;
import com.github.elenterius.magianaturalis.entity.EntityEvilTrunk;
import com.github.elenterius.magianaturalis.event.PlayerEventHandler;
import com.github.elenterius.magianaturalis.event.WorldEventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class CommonSetup implements IGuiHandler {

    public void preInit(FMLPreInitializationEvent event) {
        MNItems.initItems();
        MNBlocks.initBlocks();
    }

    public void init(FMLInitializationEvent event) {
        MNBlocks.initTileEntities();
        MNEntities.register();
        MNEntities.registerThaumcraftChampions();
        NetworkRegistry.INSTANCE.registerGuiHandler(MagiaNaturalis.instance, this);
        WorldEventHandler.register();
        PlayerEventHandler.register();
    }

    public void postInit(FMLPostInitializationEvent event) {
        MNEntities.addMobSpawns();
        MNRecipes.register();
        MNResearch.register();
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case 1:
                return new ContainerTranscribingTable(player.inventory, (TranscribingTableBlockEntity) world.getTileEntity(x, y, z));
            case 2:
                return new ContainerArcaneChest(player.inventory, (ArcaneChestBlockEntity) world.getTileEntity(x, y, z));
            case 3:
                return new ContainerEvilTrunk(player.inventory, world, (EntityEvilTrunk) ((WorldServer) world).getEntityByID(x));
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

}
