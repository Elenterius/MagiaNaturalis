package trinarybrain.magia.naturalis.common.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import trinarybrain.magia.naturalis.common.item.ItemsMN;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTab extends CreativeTabs
{
	public CreativeTab(int id, String name)
	{
		super(id, name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return ItemsMN.researchLog;
	}
}
