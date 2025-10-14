package com.trinarybrain.magianaturalis.common.core;

import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.block.BlocksMN;
import com.trinarybrain.magianaturalis.common.container.ContainerArcaneChest;
import com.trinarybrain.magianaturalis.common.container.ContainerEvilTrunk;
import com.trinarybrain.magianaturalis.common.container.ContainerTranscribingTable;
import com.trinarybrain.magianaturalis.common.entity.EntitiesMN;
import com.trinarybrain.magianaturalis.common.entity.EntityEvilTrunk;
import com.trinarybrain.magianaturalis.common.item.ItemsMN;
import com.trinarybrain.magianaturalis.common.recipe.Recipes;
import com.trinarybrain.magianaturalis.common.research.Research;
import com.trinarybrain.magianaturalis.common.tile.TileArcaneChest;
import com.trinarybrain.magianaturalis.common.tile.TileTranscribingTable;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class CommonProxy implements IGuiHandler {

    public void preInit(FMLPreInitializationEvent event) {
        ItemsMN.initItems();
        BlocksMN.initBlocks();
    }

    public void init(FMLInitializationEvent event) {
        BlocksMN.initTileEntities();
        EntitiesMN.registerEntities();
        EntitiesMN.addChampions();
        NetworkRegistry.INSTANCE.registerGuiHandler(MagiaNaturalis.instance, this);
        EventHandlerWorld.register();
    }

    public void postInit(FMLPostInitializationEvent event) {
        EntitiesMN.addEntitySpawns();
        Recipes.init();
        Research.init();
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case 1:
                return new ContainerTranscribingTable(player.inventory, (TileTranscribingTable) world.getTileEntity(x, y, z));
            case 2:
                return new ContainerArcaneChest(player.inventory, (TileArcaneChest) world.getTileEntity(x, y, z));
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
