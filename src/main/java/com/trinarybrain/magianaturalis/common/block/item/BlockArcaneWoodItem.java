package com.trinarybrain.magianaturalis.common.block.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.util.Platform;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class BlockArcaneWoodItem extends ItemBlock
{
	final String unlocal[] = {"gwPlanks", "gwOrn", "swPlanks", "gwGoldOrn.0", "gwGoldOrn.1", "gwGoldTrim" };

	public BlockArcaneWoodItem(Block block)
	{
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(MagiaNaturalis.creativeTab);
	}

	public int getMetadata(int meta)
	{
		return meta;
	}

	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName() + "." + unlocal[stack.getItemDamage()];
	}
}
