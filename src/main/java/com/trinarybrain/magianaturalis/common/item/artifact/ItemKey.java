package com.trinarybrain.magianaturalis.common.item.artifact;

import java.util.List;
import java.util.UUID;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import thaumcraft.common.tiles.TileOwned;
import com.trinarybrain.magianaturalis.common.item.BaseItem;
import com.trinarybrain.magianaturalis.common.tile.TileArcaneChest;
import com.trinarybrain.magianaturalis.common.util.NBTUtil;
import com.trinarybrain.magianaturalis.common.util.Platform;
import com.trinarybrain.magianaturalis.common.util.ResourceUtil;
import com.trinarybrain.magianaturalis.common.util.access.TileAccess;
import com.trinarybrain.magianaturalis.common.util.access.UserAccess;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemKey extends BaseItem
{
	//TODO: IMPLEMENT OFFLINE PLAYER ADDING - WITH ANVIL? HOW WILL THAT WORK?
	public ItemKey()
	{
		super();
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		super.addInformation(stack, player, list, par4);
		list.add(new StringBuilder().append(EnumChatFormatting.DARK_PURPLE).append(Platform.translate("flavor.magianaturalis:key." + stack.getItemDamage())).toString());

		NBTTagCompound data = NBTUtil.openNbtData(stack);

		if(data.hasKey("owner") && data.hasKey("ownerNick"))
		{
			list.add("");
			list.add("Soulbound to " + data.getString("ownerNick"));
		}
		if(data.hasKey("forger") && data.hasKey("forgerNick"))
		{
			if(!data.hasKey("owner") && !data.hasKey("ownerNick"))
				list.add("");
			list.add("Forged by " + data.getString("forgerNick"));
		}
	}

	@Override @SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister icon)
	{
		this.itemIcon = icon.registerIcon(ResourceUtil.PREFIX + "key_thaumium");
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		list.add(new ItemStack(this, 1, 0)); //This Key grants Access for the Bound Player to any Tile owned by the Key Forger
		list.add(new ItemStack(this, 1, 1)); //This Key adds a list of players to the access list of the Tile owned by the Bound Player or Key Forger
	}

	public EnumRarity getRarity(ItemStack itemstack)
	{
		return EnumRarity.uncommon;
	}

	@Override
	public boolean hasEffect(ItemStack stack)
	{
		return stack.getTagCompound() != null && (stack.getTagCompound().hasKey("forger") || stack.getTagCompound().hasKey("owner"));
	}

	public String getUnlocalizedName(ItemStack stack)
	{
		return new StringBuilder().append(super.getUnlocalizedName()).append(".").append(stack.getItemDamage()).toString();
	}

	public UUID getBoundPlayer(ItemStack stack)
	{
		if(stack == null) return null;
		return UUID.fromString(NBTUtil.openNbtData(stack).getString("owner"));
	}

	public boolean setBoundPlayer(ItemStack stack, GameProfile gameprofile)
	{
		if(stack == null) return false;
		NBTTagCompound data = NBTUtil.openNbtData(stack);
		data.setString("owner", gameprofile.getId().toString());
		data.setString("ownerNick", gameprofile.getName());
		return true;
	}

	public UUID getKeyForger(ItemStack stack)
	{
		if(stack == null) return null;
		return UUID.fromString(NBTUtil.openNbtData(stack).getString("forger"));
	}

	public boolean setKeyForger(ItemStack stack, GameProfile gameprofile)
	{
		if(stack == null) return false;
		NBTTagCompound data = NBTUtil.openNbtData(stack);
		data.setString("forger", gameprofile.getId().toString());
		data.setString("forgerNick", gameprofile.getName());
		return true;
	}

	public byte getAccessLevel(ItemStack stack)
	{
		if(stack == null) return 0;
		return NBTUtil.openNbtData(stack).getByte("accessLevel");
	}

	public boolean setAccessLevel(ItemStack stack, byte level)
	{
		if(stack == null) return false;
		NBTUtil.openNbtData(stack).setByte("accessLevel", level);
		return true;
	}

	public void onCreated(ItemStack stack, World world, EntityPlayer player)
	{
		this.setKeyForger(stack, player.getGameProfile());
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if(Platform.isServer() && player.isSneaking())
		{
			NBTTagCompound data = NBTUtil.openNbtData(stack);
			if(!data.hasKey("forger"))
			{
				this.setKeyForger(stack, player.getGameProfile());
				player.addChatMessage(new ChatComponentText(new StringBuilder().append(EnumChatFormatting.DARK_PURPLE).append(String.format(Platform.translate("chat.magianaturalis:key.owner"), player.getGameProfile().getName())).toString()));
			}
			else if(!data.hasKey("owner"))
			{
				this.setBoundPlayer(stack, player.getGameProfile());
				player.addChatMessage(new ChatComponentText(new StringBuilder().append(EnumChatFormatting.DARK_PURPLE).append(String.format(Platform.translate("chat.magianaturalis:key.boundplayer.1"), player.getGameProfile().getName())).toString()));
			}
			return stack;
		}
		return stack;
	}

	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(Platform.isServer())
		{
			if(stack.getItemDamage() == 0) // This Key grants Access for the Bound Player to any Tile owned by the Key Forger
			{
				NBTTagCompound data = NBTUtil.openNbtData(stack);
				if(!data.hasKey("forger"))
				{
					//					this.setKeyForger(stack, player.getGameProfile());
					return false;
				}

				if(data.hasKey("owner"))
				{
					if(!(UUID.fromString(data.getString("owner")).equals(this.getBoundPlayer(stack))))
					{
						return false;
					}
				}

				TileEntity tile = world.getTileEntity(x, y, z);
				if(tile == null) return false;

				boolean bool = false;
				if(tile instanceof TileArcaneChest)
				{
					bool = ((TileArcaneChest) tile).owner.equals(this.getKeyForger(stack));
				}
				else if(tile instanceof TileOwned)
				{
					if(!data.hasKey("forgerNick")) return false;
					bool = ((TileOwned) tile).owner.equals(stack.getTagCompound().getString("forgerNick"));
				}

				if(bool) TileAccess.addPlayerToAccesList(player, this.getAccessLevel(stack), world, x, y, z);
				return bool;
			}
			else if(stack.getItemDamage() == 1) //This Key adds a list of players to the access list of the Tile owned by the Bound Player or Key Forger
			{
				NBTTagCompound data = NBTUtil.openNbtData(stack);
				boolean hasAccess = false;

				if(data.hasKey("owner"))
				{
					if(!(UUID.fromString(data.getString("owner")).equals(this.getBoundPlayer(stack))))
					{
						hasAccess = false;
					}
				}

				TileEntity tile = world.getTileEntity(x, y, z);
				if(tile == null) return false;

				if(tile instanceof TileArcaneChest)
				{
					TileArcaneChest chest = (TileArcaneChest) tile;
					if(player.getGameProfile().getId().equals(chest.owner))
					{
						hasAccess = true;
					}
					else
					{
						hasAccess = chest.accessList.contains(new UserAccess(player.getUniqueID(), (byte) 1));
					}
				}
				else if(tile instanceof TileOwned)
				{
					TileOwned owned = (TileOwned) tile;
					if(player.getGameProfile().getName().equals(owned.owner))
					{
						hasAccess = true;
					}
					else
					{
						hasAccess = owned.accessList.contains(new StringBuilder().append(1).append(player.getGameProfile().getName()).toString());
					}
				}
				if(!hasAccess) return false;

				if(!data.hasKey("AccessList")) return false;
				NBTTagList accessList = data.getTagList("AccessList", NBT.TAG_COMPOUND);
				for(int i = 0; i < accessList.tagCount(); i++)
				{
					NBTTagCompound tempData = accessList.getCompoundTagAt(i);
					TileAccess.addPlayerToAccesList(new GameProfile(UUID.fromString(tempData.getString("UUID")), tempData.getString("Nick")), tempData.getByte("Type"), world, x, y, z);
				}
				return true;
			}
		}
		return false;
	}

	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if(stack.getItemDamage() == 0)
		{
			if(!(entity instanceof EntityPlayer)) return false;
			EntityPlayer player2 = (EntityPlayer) entity;

			boolean bool = this.setBoundPlayer(stack, player2.getGameProfile());
			if(bool) player.addChatMessage(new ChatComponentText(new StringBuilder().append(EnumChatFormatting.DARK_PURPLE).append(String.format(Platform.translate("chat.magianaturalis:key.boundplayer"), player2.getGameProfile().getName())).toString()));
		}
		else if(stack.getItemDamage() == 1)
		{
			if(!(entity instanceof EntityPlayer)) return false;
			EntityPlayer player2 = (EntityPlayer) entity;

			NBTTagCompound data = NBTUtil.openNbtData(stack);
			NBTTagList accessList;

			if(!data.hasKey("AccessList"))
				accessList = new NBTTagList();
			else
				accessList = data.getTagList("AccessList", NBT.TAG_COMPOUND);

			NBTTagCompound tempData = new NBTTagCompound();
			tempData.setString("UUID", player2.getGameProfile().getId().toString());
			tempData.setString("Nick", player2.getGameProfile().getName());
			tempData.setByte("Type", data.getByte("AccessLevel"));
			accessList.appendTag(tempData);
			data.setTag("AccessList", accessList);

			player.addChatMessage(new ChatComponentText(new StringBuilder().append(EnumChatFormatting.DARK_PURPLE).append(String.format(Platform.translate("chat.magianaturalis:key.accesslist"), player2.getGameProfile().getName())).toString()));
		}
		return false;
	}
}
