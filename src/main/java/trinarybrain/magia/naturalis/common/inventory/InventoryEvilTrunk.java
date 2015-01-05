package trinarybrain.magia.naturalis.common.inventory;

import trinarybrain.magia.naturalis.common.entity.EntityEvilTrunk;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryEvilTrunk implements IInventory
{
	public ItemStack[] inventory;
	public EntityEvilTrunk entityTrunk;
	public boolean inventoryChanged;
	public int slotCount;
	public int stacklimit = 64;

	public InventoryEvilTrunk(EntityEvilTrunk entity, int slots)
	{
		this.slotCount = slots;
		this.inventory = new ItemStack[36];
		this.inventoryChanged = false;
		this.entityTrunk = entity;
	}

	@Override
	public int getSizeInventory()
	{
		return this.slotCount;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.inventory[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int amount)
	{
		ItemStack[] inv = this.inventory;
		if(inv[index] != null)
		{
			if(inv[index].stackSize <= amount)
			{
				ItemStack stack = inv[index];
				inv[index] = null;
				return stack;
			}

			ItemStack stack = inv[index].splitStack(amount);
			if(inv[index].stackSize == 0) inv[index] = null;
			return stack;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index)
	{
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.inventory[index] = stack;
	}

	@Override
	public String getInventoryName()
	{
		return "Inventory";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return this.stacklimit;
	}

	@Override
	public void markDirty()
	{
		this.inventoryChanged = true;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return false;
	}

	@Override
	public void openInventory()
	{
		this.entityTrunk.setOpen(true);
	}

	@Override
	public void closeInventory()
	{
		this.entityTrunk.setOpen(false);
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
	}

	public void dropAllItems()
	{
		for(int index = 0; index < this.inventory.length; index++)
			if(this.inventory[index] != null)
			{
				this.entityTrunk.entityDropItem(this.inventory[index], 0.0F);
				this.inventory[index] = null;
			}
	}

	public NBTBase writeToNBT(NBTTagList dataList)
	{
		for(int index = 0; index < this.inventory.length; index++)
			if(this.inventory[index] != null)
			{
				NBTTagCompound data = new NBTTagCompound();
				data.setByte("Slot", (byte)index);
				this.inventory[index].writeToNBT(data);
				dataList.appendTag(data);
			}
		return dataList;
	}

	public void readFromNBT(NBTTagList dataList)
	{
		this.inventory = new ItemStack[this.inventory.length];
		for(int i = 0; i < dataList.tagCount(); i++)
		{
			NBTTagCompound data = dataList.getCompoundTagAt(i);
			byte index = data.getByte("Slot");
			ItemStack stack = ItemStack.loadItemStackFromNBT(data);

			if(stack.getItem() != null)
			{
				if(index >= 0 && index < this.inventory.length)
					this.inventory[index] = stack;
			}
		}		
	}
}
