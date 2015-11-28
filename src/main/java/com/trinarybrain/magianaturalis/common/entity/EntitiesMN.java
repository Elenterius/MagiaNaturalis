package com.trinarybrain.magianaturalis.common.entity;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;
import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.core.Log;
import com.trinarybrain.magianaturalis.common.entity.taint.EntityTaintBreeder;
import com.trinarybrain.magianaturalis.common.util.NameUtil;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.EntityRegistry;

public class EntitiesMN
{
	public static void registerEntities()
	{
		int id = 0;
		// TODO: remove registerGlobalEntityID -> leading to major bug - make
		// own spawn eggs
		EntityRegistry.registerGlobalEntityID(EntityTaintBreeder.class, "taintBreeder", EntityRegistry.findGlobalUniqueEntityId(), 0xFFC0FF, 0x800090);
		EntityRegistry.registerModEntity(EntityTaintBreeder.class, "taintBreeder", id++, MagiaNaturalis.instance, 64, 3, false);

		EntityRegistry.registerModEntity(EntityEvilTrunk.class, "evilTrunk", id++, MagiaNaturalis.instance, 64, 3, false);
		EntityRegistry.registerModEntity(EntityZombieExtended.class, "ferociousRevenant", id++, MagiaNaturalis.instance, 64, 3, false);

		EntityRegistry.registerGlobalEntityID(EntityTaintman.class, "taintman", EntityRegistry.findGlobalUniqueEntityId(), 0xFFC0FF, 0x800090);
		EntityRegistry.registerModEntity(EntityTaintman.class, "taintman", id++, MagiaNaturalis.instance, 64, 3, false);
	}

	public static void addChampions()
	{
		FMLInterModComms.sendMessage("Thaumcraft", "championWhiteList", "taintBreeder:1");
	}

	public static void addEntitySpawns()
	{
		if(ThaumcraftWorldGenerator.biomeTaint != null)
		{
			BiomeGenBase biomeTaint = ThaumcraftWorldGenerator.biomeTaint;
			if(((biomeTaint.getSpawnableList(EnumCreatureType.monster) != null ? 1 : 0) & (biomeTaint.getSpawnableList(EnumCreatureType.monster).size() > 0 ? 1 : 0)) != 0)
			{
				EntityRegistry.addSpawn(EntityTaintBreeder.class, 30, 1, 1, EnumCreatureType.monster, new BiomeGenBase[] { biomeTaint });
			}
			else
			{
				Log.logger.error("Failed to add Entity Spawns to Biome: " + biomeTaint);
			}
		}
		else
		{
			Log.logger.error("Failed to add Entity Spawns to Biome: Taint");
		}
	}
}
