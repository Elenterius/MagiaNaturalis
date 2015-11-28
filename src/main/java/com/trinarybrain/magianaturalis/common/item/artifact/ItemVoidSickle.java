package com.trinarybrain.magianaturalis.common.item.artifact;

import com.trinarybrain.magianaturalis.common.Reference;
import com.trinarybrain.magianaturalis.common.util.Platform;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IWarpingGear;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.common.config.ConfigItems;

public class ItemVoidSickle extends ItemSickle implements IRepairable, IWarpingGear
{
	public ItemVoidSickle()
	{
		super(ThaumcraftApi.toolMatVoid);
		areaSize = 4;
	}

	@Override @SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister icon)
	{
		itemIcon = icon.registerIcon(Reference.ID + ":" + "sickle_void");
	}

	@Override
	public int getItemEnchantability()
	{
		return 5;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.uncommon;
	}

	@Override
	public boolean getIsRepairable(ItemStack stack, ItemStack stackMaterial)
	{
		return stackMaterial.isItemEqual(new ItemStack(ConfigItems.itemResource, 1, 15)) ? true : super.getIsRepairable(stack, stackMaterial);
	}

	@Override
	public int getWarp(ItemStack itemstack, EntityPlayer player)
	{
		return 1;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int n, boolean bool)
	{
		if(stack.isItemDamaged() && entity != null && entity.ticksExisted % 20 == 0 && entity instanceof EntityLivingBase)
		{
			stack.damageItem(-1, (EntityLivingBase)entity);
		}
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if(Platform.isServer() && entity instanceof EntityLivingBase && (!(entity instanceof EntityPlayer) || MinecraftServer.getServer().isPVPEnabled()))
		{
			((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.weakness.getId(), 80));
		}
		return false;
	}
}
