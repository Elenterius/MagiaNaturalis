package trinarybrain.magia.naturalis.common.util;

import java.util.ArrayList;
import java.util.UUID;

import thaumcraft.common.entities.EntityFollowingItem;
import thaumcraft.common.lib.utils.EntityUtils;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.FMLCommonHandler;

public final class Platform
{
	public static boolean isClient()
	{
		return FMLCommonHandler.instance().getEffectiveSide().isClient();
	}

	public static boolean isServer()
	{
		return FMLCommonHandler.instance().getEffectiveSide().isServer();
	}

	public static String translate(String str)
	{
		return StatCollector.translateToLocal(str);
	}

	/**
	 * Finds the GameProfile for the given Player Name.
	 * Only Authenticated Clients/Server can retrieve the online UUID else the UUID will be a offline generated one. 
	 */
	public static GameProfile findGameProfileByName(String playerName)
	{
		return MinecraftServer.getServer().func_152358_ax().func_152655_a(playerName);
	}

	public static UUID generateOfflineUUIDforName(String playerName)
	{
		return UUID.nameUUIDFromBytes(("OfflinePlayer:" + playerName).getBytes(Charsets.UTF_8));
	}
}
