package trinarybrain.magia.naturalis.common.item.relic;

import java.util.List;

import com.google.common.collect.Multimap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumChatFormatting;
import thaumcraft.api.IRepairable;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import trinarybrain.magia.naturalis.common.MagiaNaturalis;
import trinarybrain.magia.naturalis.common.item.BaseItem;
import trinarybrain.magia.naturalis.common.util.NBTUtil;
import trinarybrain.magia.naturalis.common.util.Platform;
import trinarybrain.magia.naturalis.common.util.ResourceUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemVisSword extends ItemSword
{
	protected int maxVisSize = 25;

	public ItemVisSword()
	{
		super(ThaumcraftApi.toolMatElemental);
		this.setCreativeTab(MagiaNaturalis.creativeTab);
	}

	@Override @SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister icon)
	{
		this.itemIcon = icon.registerIcon(ResourceUtil.PREFIX + "wakizashi");
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		if(Minecraft.getMinecraft().currentScreen.isCtrlKeyDown())
		{
			list.add(String.format("%dx %s%s", this.getVis(stack, Aspect.AIR), EnumChatFormatting.YELLOW, Aspect.AIR.getName()));
			list.add(String.format("%dx %s%s", this.getVis(stack, Aspect.EARTH), EnumChatFormatting.GREEN, Aspect.EARTH.getName()));
			list.add(String.format("%dx %s%s", this.getVis(stack, Aspect.WATER), EnumChatFormatting.AQUA, Aspect.WATER.getName()));
			list.add(String.format("%dx %s%s", this.getVis(stack, Aspect.FIRE), EnumChatFormatting.RED, Aspect.FIRE.getName()));
			list.add(String.format("%dx %s%s", this.getVis(stack, Aspect.ORDER), EnumChatFormatting.WHITE, Aspect.ORDER.getName()));
			list.add(String.format("%dx %s%s", this.getVis(stack, Aspect.ENTROPY), EnumChatFormatting.DARK_GRAY, Aspect.ENTROPY.getName()));
		}
		else
		{
			list.add(EnumChatFormatting.DARK_GRAY + Platform.translate("hint.magianaturalis:ctrl"));
		}
	}

	public short getVis(ItemStack stack, Aspect aspect)
	{
		if(stack != null && aspect != null)
			return NBTUtil.openNbtData(stack).getShort("vis:" + aspect.getTag());

		return 0;
	}

	public boolean setVis(ItemStack stack, Aspect aspect, short size)
	{
		if(stack != null && aspect != null)
		{
			if(size > this.maxVisSize) return false;
			if(size < 0) size = 0;
			NBTUtil.openNbtData(stack).setShort("vis:" + aspect.getTag(), size);
			return true;
		}
		return false;
	}

	public boolean addVis(ItemStack stack, Aspect aspect, short size)
	{
		if(stack != null && aspect != null && size > 0)
		{
			size += NBTUtil.openNbtData(stack).getShort("vis:" + aspect.getTag());
			if(size > this.maxVisSize) return false;
			NBTUtil.openNbtData(stack).setShort("vis:" + aspect.getTag(), size);
			return true;
		}
		return false;
	}
}
