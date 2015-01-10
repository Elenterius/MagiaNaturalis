package trinarybrain.magia.naturalis.common.item.baubles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.items.wands.ItemFocusPouch;
import trinarybrain.magia.naturalis.common.MagiaNaturalis;
import trinarybrain.magia.naturalis.common.util.Platform;

public class ItemFocusPouchEnder extends ItemFocusPouch
{

	public ItemFocusPouchEnder()
	{
		setMaxStackSize(1);
	    setHasSubtypes(false);
	    setMaxDamage(0);
	    this.setCreativeTab(MagiaNaturalis.creativeTab);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		this.icon = ir.registerIcon("thaumcraft:focuspouch");
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if(Platform.isServer())
		{
			InventoryEnderChest inventoryenderchest = player.getInventoryEnderChest();
			if(inventoryenderchest != null)
				player.displayGUIChest(inventoryenderchest);
		}

		//TODO: OPEN ENDERCHEST HERE
		
		return stack;
	}
}
