package com.trinarybrain.magianaturalis.common.entity;

import com.trinarybrain.magianaturalis.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.entity.taint.EntityTaintBreeder;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;

public class EntitiesMN {

    //  Entity Names
    //	public static final String WRATHGAZER = "wrathgazer";
    //	public static final String SNAPJAW = "snapjaw";
    //	public static final String DOG_ONYX = "dogOnyx";

    public static void registerEntities() {
        int id = 0;
        // TODO: remove registerGlobalEntityID -> leading to major bug - make own spawn eggs
        EntityRegistry.registerGlobalEntityID(EntityTaintBreeder.class, "taintBreeder", EntityRegistry.findGlobalUniqueEntityId(), 0xFFC0FF, 0x800090);
        EntityRegistry.registerModEntity(EntityTaintBreeder.class, "taintBreeder", id++, MagiaNaturalis.instance, 64, 3, false);

        EntityRegistry.registerModEntity(EntityEvilTrunk.class, "evilTrunk", id++, MagiaNaturalis.instance, 64, 3, false);
        EntityRegistry.registerModEntity(EntityZombieExtended.class, "ferociousRevenant", id++, MagiaNaturalis.instance, 64, 3, false);

        //EntityRegistry.registerGlobalEntityID(EntityTaintman.class, "taintman", EntityRegistry.findGlobalUniqueEntityId(), 0xFFC0FF, 0x800090);
        //EntityRegistry.registerModEntity(EntityTaintman.class, "taintman", id++, MagiaNaturalis.instance, 64, 3, false);
    }

    public static void addChampions() {
        FMLInterModComms.sendMessage("Thaumcraft", "championWhiteList", "taintBreeder:1");
    }

    public static void addEntitySpawns() {
        if (ThaumcraftWorldGenerator.biomeTaint != null) {
            BiomeGenBase biomeTaint = ThaumcraftWorldGenerator.biomeTaint;
            if (((biomeTaint.getSpawnableList(EnumCreatureType.monster) != null ? 1 : 0) & (!biomeTaint.getSpawnableList(EnumCreatureType.monster).isEmpty() ? 1 : 0)) != 0) {
                EntityRegistry.addSpawn(EntityTaintBreeder.class, 30, 1, 1, EnumCreatureType.monster, new BiomeGenBase[]{biomeTaint});
            }
            else {
                MagiaNaturalis.LOGGER.error("Failed to add Entity Spawns to Biome: {}", biomeTaint);
            }
        }
        else {
            MagiaNaturalis.LOGGER.error("Failed to add Entity Spawns to Biome: Taint");
        }
    }

}
