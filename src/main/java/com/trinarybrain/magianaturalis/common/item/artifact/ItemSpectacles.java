package com.trinarybrain.magianaturalis.common.item.artifact;

import java.util.List;

import com.trinarybrain.api.ISpectacles;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import thaumcraft.api.IGoggles;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.IRevealer;
import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.util.NameUtil;
import com.trinarybrain.magianaturalis.common.util.Platform;
import com.trinarybrain.magianaturalis.common.util.ResourceUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSpectacles extends ItemArmor implements IRepairable, IVisDiscountGear, IRevealer, IGoggles, ISpectacles
{
	public ItemSpectacles()
	{
		super(ThaumcraftApi.armorMatSpecial, 4, 0);
		this.setMaxDamage(350);
		this.setCreativeTab(MagiaNaturalis.creativeTab);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add(EnumChatFormatting.DARK_PURPLE + Platform.translate("tc.visdiscount") + ": " + getVisDiscount(stack, player, null) + "%");
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		this.itemIcon = ir.registerIcon(ResourceUtil.PREFIX + NameUtil.SPECTACLES);
	}

	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return ResourceUtil.PREFIX + ResourceUtil.PATH_MODEL + "armorSpectacles.png";
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.epic;
	}

	public boolean getIsRepairable(ItemStack stack, ItemStack stack2)
	{
		return stack2.isItemEqual(new ItemStack(Items.gold_ingot)) ? true : super.getIsRepairable(stack, stack2);
	}

	@Override
	public boolean showIngamePopups(ItemStack stack, EntityLivingBase player)
	{
		return true;
	}

	@Override
	public boolean showNodes(ItemStack stack, EntityLivingBase player)
	{
		return true;
	}

	@Override
	public int getVisDiscount(ItemStack stack, EntityPlayer player, Aspect aspect)
	{
		return 6;
	}

	@Override
	public boolean drawSpectacleHUD(ItemStack itemstack, EntityLivingBase player)
	{
		return true;
	}
}
