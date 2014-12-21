package trinarybrain.magia.naturalis.common.entity;

import trinarybrain.magia.naturalis.common.MagiaNaturalis;
import trinarybrain.magia.naturalis.common.entity.taint.EntityTaintBreeder;
import trinarybrain.magia.naturalis.common.util.ModUtil;
import trinarybrain.magia.naturalis.common.util.NameUtil;
import cpw.mods.fml.common.registry.EntityRegistry;

public class EntitiesMN
{
	public static void registerEntities()
	{
		EntityRegistry.registerGlobalEntityID(EntityTaintBreeder.class, NameUtil.TAINT_BREEDER, EntityRegistry.findGlobalUniqueEntityId(), 16761087, 8388752);
		EntityRegistry.registerModEntity(EntityTaintBreeder.class, NameUtil.TAINT_BREEDER, ModUtil.idTaintBreeder, MagiaNaturalis.instance, 64, 3, false);
	}
}
