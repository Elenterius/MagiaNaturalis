package trinarybrain.magia.naturalis.common.item.baubles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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
			InventoryEnderChest invEnderchest = player.getInventoryEnderChest();
			if(invEnderchest != null) player.displayGUIChest(invEnderchest);
		}
		return stack;
	}

	public void onUpdate(ItemStack stack, World world, Entity entity, int ticks, boolean bool)
	{
		if(Platform.isServer() && entity instanceof EntityPlayer)
		{
			ItemStack[] stackList = this.getInventory(stack);
			InventoryEnderChest invEnderchest = ((EntityPlayer) entity).getInventoryEnderChest();
			if(invEnderchest != null && stackList != null)
			{
				//TODO: DO STUFF HERE!
			}
		}
	}

	public ItemStack[] getInventory(ItemStack stack)
	{
		ItemStack[] stackList = new ItemStack[27];
		if(stack.hasTagCompound())
		{
			NBTTagList dataList = stack.stackTagCompound.getTagList("Inventory", 10);
			for(int index = 0; index < dataList.tagCount(); index++)
			{
				NBTTagCompound data = dataList.getCompoundTagAt(index);
				byte slot = data.getByte("Slot");
				if(slot >= 0 && slot < stackList.length) stackList[slot] = ItemStack.loadItemStackFromNBT(data);
			}
		}
		return stackList;
	}

	public void setInventory(ItemStack stack, ItemStack[] stackList)
	{
		NBTTagList dataList = new NBTTagList();
		for(int index = 0; index < stackList.length; index++)
		{
			if(stackList[index] != null)
			{
				NBTTagCompound data = new NBTTagCompound();
				data.setByte("Slot", (byte) index);
				stackList[index].writeToNBT(data);
				dataList.appendTag(data);
			}
		}
		stack.setTagInfo("Inventory", dataList);
	}
}
