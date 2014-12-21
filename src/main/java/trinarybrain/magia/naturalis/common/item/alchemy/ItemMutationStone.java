package trinarybrain.magia.naturalis.common.item.alchemy;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import trinarybrain.magia.naturalis.common.item.BaseItem;
import trinarybrain.magia.naturalis.common.util.Platform;
import trinarybrain.magia.naturalis.common.util.ResourceUtil;
import trinarybrain.magia.naturalis.common.util.alchemy.BlockMorpher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMutationStone extends BaseItem
{

	public ItemMutationStone()
	{
		super();
		this.maxStackSize = 1;
	}

	@Override @SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister icon)
	{
		this.itemIcon = icon.registerIcon(ResourceUtil.PREFIX + "mutation_stone");
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		super.addInformation(stack, player, list, par4);
		list.add(EnumChatFormatting.DARK_PURPLE + "Mold the Visual");
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
	{
		if(Platform.isClient()) return false;
		return BlockMorpher.setMorphOrRotation(world, x, y, z, world.getBlock(x, y, z), world.getBlockMetadata(x, y, z), player.isSneaking());
	}
}
