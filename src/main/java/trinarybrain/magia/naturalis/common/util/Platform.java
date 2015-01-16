package trinarybrain.magia.naturalis.common.util;

import java.util.ArrayList;
import java.util.UUID;

import thaumcraft.common.entities.EntityFollowingItem;
import thaumcraft.common.lib.utils.EntityUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

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

	public static boolean harvestBlock(World world, EntityPlayer player, int x, int y, int z, boolean followItem, int fortune, int abundance, int color)
	{
		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);

		if(block == null || block.getBlockHardness(world, x, y, z) < 0.0F) return false;

		world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));


		if((player.capabilities.isCreativeMode || block.canHarvestBlock(player, meta)) && emulateBlockHarvestByPlayer(world, x, y, z, player))
		{
			block.harvestBlock(world, player, x, y, z, meta);
			if(!player.capabilities.isCreativeMode)
			{
				ArrayList<ItemStack> drops = block.getDrops(world, x, y, z, meta, 3);
			}

			if(followItem)
			{
				ArrayList<Entity> entities = EntityUtils.getEntitiesInRange(world, x + 0.5D, y + 0.5D, z + 0.5D, player, EntityItem.class, 2.0D);
				if(entities != null && entities.size() > 0)
				{
					for(Entity entity : entities)
						if(!entity.isDead && entity instanceof EntityItem && entity.ticksExisted == 0 && !(entity instanceof EntityFollowingItem))
						{
							EntityFollowingItem followingItem = new EntityFollowingItem(world, entity.posX, entity.posY, entity.posZ, ((EntityItem)entity).getEntityItem().copy(), player, color);
							followingItem.motionX = entity.motionX;
							followingItem.motionY = entity.motionY;
							followingItem.motionZ = entity.motionZ;
							world.spawnEntityInWorld(followingItem);
							entity.setDead();
						}
				}
			}

		}
		return true;
	}

	public static boolean emulateBlockHarvestByPlayer(World world, int x, int y, int z, EntityPlayer player)
	{
		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if(block != null)
		{
			block.onBlockHarvested(world, x, y, z, meta, player);
			if(block.removedByPlayer(world, player, x, y, z)) block.onBlockDestroyedByPlayer(world, x, y, z, meta);
			return true;
		}
		return false;
	}
}
