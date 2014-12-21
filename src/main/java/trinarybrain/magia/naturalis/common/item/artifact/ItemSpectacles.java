package trinarybrain.magia.naturalis.common.item.artifact;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import thaumcraft.api.IGoggles;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.IRevealer;
import trinarybrain.magia.naturalis.api.ISpectacles;
import trinarybrain.magia.naturalis.common.MagiaNaturalis;
import trinarybrain.magia.naturalis.common.util.NameUtil;
import trinarybrain.magia.naturalis.common.util.Platform;
import trinarybrain.magia.naturalis.common.util.ResourceUtil;
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

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		super.addInformation(stack, player, list, par4);
		list.add(EnumChatFormatting.DARK_PURPLE + Platform.translate("tc.visdiscount") + ": " + getVisDiscount(stack, player, null) + "%");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		this.itemIcon = ir.registerIcon(ResourceUtil.PREFIX + NameUtil.SPECTACLES);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return ResourceUtil.PREFIX + ResourceUtil.PATH_MODEL + "armorSpectacles.png";
	}

	@Override
	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.epic;
	}

	@Override
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
