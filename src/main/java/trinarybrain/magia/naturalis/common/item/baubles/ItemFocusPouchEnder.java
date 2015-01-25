package trinarybrain.magia.naturalis.common.item.baubles;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import thaumcraft.common.items.wands.ItemFocusPouch;
import trinarybrain.magia.naturalis.common.MagiaNaturalis;
import trinarybrain.magia.naturalis.common.util.NBTUtil;
import trinarybrain.magia.naturalis.common.util.Platform;
import trinarybrain.magia.naturalis.common.util.ResourceUtil;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFocusPouchEnder extends ItemFocusPouch implements IBauble
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
		this.icon = ir.registerIcon(ResourceUtil.PREFIX + "focus_pouch_ender");
	}

	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.epic;
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
		//TODO: Remove this Hacky/Brutforce Way and asm the shit out of thaumcraft
		if(Platform.isServer() && entity instanceof EntityPlayer && entity.ticksExisted % 5 == 0)
		{
			InventoryEnderChest invEnderchest = ((EntityPlayer) entity).getInventoryEnderChest();
			if(invEnderchest != null)
			{
				NBTTagCompound data = NBTUtil.openNbtData(stack);
				boolean isInvDirty = data.getBoolean("isInvDirty");

				if(isInvDirty) // Push InvPouch to InvEnderChest
				{
					NBTTagList dataList = data.getTagList("Inventory", 10);
					invEnderchest.loadInventoryFromNBT(dataList);
					data.setBoolean("isInvDirty", false);
				}
				else // Push InvEnderChest to InvPouch
				{
					NBTTagList dataListChest = invEnderchest.saveInventoryToNBT();
					NBTTagList dataListPouch = data.getTagList("Inventory", 10);

					if(!dataListChest.equals(dataListPouch))
					{
						data.setTag("Inventory", dataListChest);
					}
				}
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
		stack.stackTagCompound.setBoolean("isInvDirty", true);
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return BaubleType.BELT;
	}

	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player)
	{
		this.onUpdate(stack, player.worldObj, player, player.ticksExisted, false);
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
	{
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
	{
		return true;
	}
}
