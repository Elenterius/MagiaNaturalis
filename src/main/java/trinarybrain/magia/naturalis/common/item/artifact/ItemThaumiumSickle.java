package trinarybrain.magia.naturalis.common.item.artifact;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import thaumcraft.api.IRepairable;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.common.config.ConfigItems;
import trinarybrain.magia.naturalis.common.util.ResourceUtil;

public class ItemThaumiumSickle extends ItemSickle implements IRepairable
{

	public ItemThaumiumSickle()
	{
		super(ThaumcraftApi.toolMatThaumium);
	}
	
	@Override @SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister icon)
	{
		this.itemIcon = icon.registerIcon(ResourceUtil.PREFIX + "sickle_thaumium");
	}

	public int getItemEnchantability()
	{
		return 5;
	}

	public EnumRarity getRarity(ItemStack itemstack)
	{
		return EnumRarity.uncommon;
	}

	public boolean getIsRepairable(ItemStack stack, ItemStack stack2)
	{
		return stack2.isItemEqual(new ItemStack(ConfigItems.itemResource, 1, 2)) ? true : super.getIsRepairable(stack, stack2);
	}
}
