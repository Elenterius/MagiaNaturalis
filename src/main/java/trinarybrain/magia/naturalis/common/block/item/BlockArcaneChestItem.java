package trinarybrain.magia.naturalis.common.block.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import trinarybrain.magia.naturalis.common.MagiaNaturalis;
import trinarybrain.magia.naturalis.common.util.NBTUtil;
import trinarybrain.magia.naturalis.common.util.Platform;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.Constants.NBT;

public class BlockArcaneChestItem  extends ItemBlock
{
	final String unlocal[] = { "unknown", "gw", "sw" };

	public BlockArcaneChestItem(Block block)
	{
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setCreativeTab(MagiaNaturalis.creativeTab);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		NBTTagCompound data = NBTUtil.openNbtData(stack);
		if(data != null)
		{
			if(data.hasKey("Items"))
			{
				list.add("");
				list.add(EnumChatFormatting.DARK_GRAY + Platform.translate("hint.magianaturalis:chest.items"));
			}
			if(data.hasKey("AccessList"))
			{
				if(!data.hasKey("Items")) list.add("");
				list.add(EnumChatFormatting.DARK_GRAY + Platform.translate("hint.magianaturalis:chest.access"));
			}
		}		
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
