package com.trinarybrain.magianaturalis.common.block.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.util.NBTUtil;
import com.trinarybrain.magianaturalis.common.util.Platform;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class BlockJarPrisonItem extends ItemBlock
{
	public BlockJarPrisonItem(Block block)
	{
		super(block);
		this.setMaxDamage(0);
		this.setCreativeTab(MagiaNaturalis.creativeTab);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List lines, boolean bool)
	{
		super.addInformation(stack, player, lines, bool);
		String id = this.getEntityLivingID(stack);
		if(id != null)
		{
			lines.add(Platform.translate("entity." + id + ".name"));
		}
	}

	public String getEntityLivingID(ItemStack stack)
	{
		NBTTagCompound data = NBTUtil.openNbtData(stack);
		if(data.hasKey("entity")) { return data.getCompoundTag("entity").getString("id"); }
		return null;
	}

	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
	{
		if(Platform.isClient()) return false;
		if(NBTUtil.openNbtData(stack).hasKey("entity")) return false;

		ItemStack stack2 = stack.copy();
		stack2.stackSize = 1;
		if(this.storeEntityLiving(stack2, entity))
		{
			player.inventory.decrStackSize(player.inventory.currentItem, 1);
			player.inventory.addItemStackToInventory(stack2);
			player.inventoryContainer.detectAndSendChanges();
			return true;
		}
		return false;
	}

	public static boolean storeEntityLiving(ItemStack stack, EntityLivingBase entity)
	{
		if(entity == null || entity instanceof IBossDisplayData || entity instanceof EntityPlayer) return false;
		if(!(entity instanceof EntityCreature)) return false;
		if(stack == null) return false;

		NBTTagCompound data = new NBTTagCompound();
		if(!entity.writeMountToNBT(data)) return false;
		entity.setDead();
		stack.setTagInfo("entity", data);

		return true;
	}

	public boolean releaseEntityLiving(ItemStack stack, World world, double x, double y, double z)
	{
		NBTTagCompound data = NBTUtil.openNbtData(stack);
		if(data.hasKey("entity")) { return NBTUtil.spawnEntityFromNBT(data.getCompoundTag("entity"), world, x, y, z); }
		return false;
	}
}
