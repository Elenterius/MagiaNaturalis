package trinarybrain.magia.naturalis.common.item.artifact;

import java.io.File;
import java.util.List;
import java.util.UUID;

import com.google.common.base.Charsets;
import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.INode;
import thaumcraft.common.tiles.TileOwned;
import trinarybrain.magia.naturalis.common.item.BaseItem;
import trinarybrain.magia.naturalis.common.tile.TileArcaneChest;
import trinarybrain.magia.naturalis.common.tile.TileTranscribingTable;
import trinarybrain.magia.naturalis.common.util.Platform;
import trinarybrain.magia.naturalis.common.util.ResourceUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DevTool extends BaseItem
{
	private byte modes;

	public DevTool()
	{
		super();
		this.modes = 20;
		this.maxStackSize = 1;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		super.addInformation(stack, player, list, par4);
		list.add(EnumChatFormatting.DARK_PURPLE + "Modus Operandi: " + stack.getItemDamage());
	}

	@Override @SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister icon)
	{
		this.itemIcon = icon.registerIcon(ResourceUtil.PREFIX + "book_ouroboros");
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		//Change DEV Tool Modes!!!
		//		if(player.isSneaking())
		//		{
		//			stack.setItemDamage(stack.getItemDamage()+1);
		//			if(stack.getItemDamage() >= modes)
		//				stack.setItemDamage(0);
		//			return stack;
		//		}
		return stack;
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
	{
		if(Platform.isClient()) return false;
		
		// DO DEV MAGIC HERE !!!
		if(stack.getItemDamage() == 0)
		{
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile == null) return false;
			if(tile instanceof TileOwned)
			{
				TileOwned owned = (TileOwned) tile;
				System.out.print("\n---------------------------------------------------------------");
				System.out.printf("%nX= %d, Y= %d, Z= %d%nOwner: %s", x, y, z, owned.owner);
				System.out.print("\nAccesList:" + owned.accessList);
			}
			else if(tile instanceof TileArcaneChest)
			{
				TileArcaneChest chest = (TileArcaneChest) tile;
				System.out.print("\n---------------------------------------------------------------");
				System.out.printf("%nX= %d, Y= %d, Z= %d%nOwner UUID: %s%nPlayer UUID: %s", x, y, z, chest.owner.toString(), player.getGameProfile().getId());
				System.out.print("\nAccesList:" + chest.accessList.toString());
			}
			else if(tile instanceof TileTranscribingTable)
			{
				TileTranscribingTable table = (TileTranscribingTable) tile;
				ItemStack stack2 = table.getStackInSlot(0);
				if(stack2.getItem() instanceof ItemResearchLog)
				{
					ItemResearchLog log = (ItemResearchLog) stack2.getItem();
					int i = 6;
					if(log.getResearchPoint(stack2, Aspect.AIR) < 64) i--;
					if(log.getResearchPoint(stack2, Aspect.EARTH) < 64) i--;
					if(log.getResearchPoint(stack2, Aspect.WATER) < 64) i--;
					if(log.getResearchPoint(stack2, Aspect.FIRE)  < 64) i--;
					if(log.getResearchPoint(stack2, Aspect.ORDER)  < 64) i--;
					if(log.getResearchPoint(stack2, Aspect.ENTROPY)  < 64) i--;
					System.out.print("\n[DevTool]: ResearchLog " + i + " of 6 RP Maxed");
					return true;
				}
			}
			else if(tile instanceof INode)
			{
				INode node = (INode) tile;
				System.out.print("\nAspectBase: " + node.getAspectsBase());
			}
		}
		else if(stack.getItemDamage() == 1)
		{
			String name = "Lord_Cerberus";
			UUID uuid = Platform.generateOfflineUUIDforName(name);
			GameProfile gameprofile = Platform.findGameProfileByName(name);
			System.out.printf("%n%s UUID: %s", name + "Offline", uuid.toString());
			System.out.printf("%n%s GameProfile: %s%n", gameprofile.getName(), gameprofile.toString());
		}
		
		return false;
	}
}