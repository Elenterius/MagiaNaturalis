package trinarybrain.magia.naturalis.client.util;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public final class RenderUtil
{
	public static int RenderID = RenderingRegistry.getNextAvailableRenderId();
	public static int RenderID2 = RenderingRegistry.getNextAvailableRenderId();
	
	public static void bindTexture(ResourceLocation resource)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(resource);
	}
	
	public static float getTicks()
	{
		return Minecraft.getMinecraft().renderViewEntity.ticksExisted;
	}
	
	public static EntityPlayer getRenderViewPlayer()
	{
		return (EntityPlayer) Minecraft.getMinecraft().renderViewEntity;
	}
}
