package trinarybrain.magia.naturalis.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import trinarybrain.magia.naturalis.common.core.Log;
import trinarybrain.magia.naturalis.common.entity.EntityEvilTrunk;
import trinarybrain.magia.naturalis.common.inventory.InventoryEvilTrunk;

public class ContainerEvilTrunk extends Container
{
	private InventoryEvilTrunk inventory;
	private EntityEvilTrunk entityTrunk;
	private int numRows = 4;

	public ContainerEvilTrunk(IInventory iinventory, World world, EntityEvilTrunk entity)
	{
		this.entityTrunk = entity;
		this.inventory = entity.inventory;

		for(int row = 0; row < this.numRows; row++)
			for(int slot = 0; slot < 9; slot++)
				addSlotToContainer(new Slot(this.inventory, slot + row * 9, 8 + slot * 18, 15 + row * 23));

		for(int row = 0; row < 3; row++)
			for(int slot = 0; slot < 9; slot++)
				addSlotToContainer(new Slot(iinventory, slot + row * 9 + 9, 8 + slot * 18, 118 + row * 18));

		for(int slot = 0; slot < 9; slot++)
			addSlotToContainer(new Slot(iinventory, slot, 8 + slot * 18, 176));

		entityTrunk.setOpen(true);
		entityTrunk.worldObj.playSoundAtEntity(entityTrunk, "random.chestopen", 0.5F, entityTrunk.worldObj.rand.nextFloat() * 0.1F + 0.9F);
	}

	public boolean enchantItem(EntityPlayer player, int button)
	{
		if(button == 1)
		{
			this.entityTrunk.setWaiting(!this.entityTrunk.isWaiting());
			return true;
		}
		return false;
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int index)
	{
		ItemStack stack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack())
		{
			ItemStack tempStack = slot.getStack();
			stack = tempStack.copy();	

			if(index < 37)
			{
				if(!this.mergeItemStack(tempStack, 36, this.inventorySlots.size(), true))
					return null;
			}
			else if(!this.mergeItemStack(tempStack, 0, 36, false))
			{
				return null;
			}

			if(tempStack.stackSize == 0) slot.putStack((ItemStack)null);
			else slot.onSlotChanged();

			if(tempStack.stackSize == stack.stackSize) return null;
			slot.onPickupFromSlot(player, tempStack);
		}
		return stack;
	}

	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);
		this.entityTrunk.setOpen(false);
		this.entityTrunk.worldObj.playSoundAtEntity(this.entityTrunk, "random.chestclosed", 0.5F,
				this.entityTrunk.worldObj.rand.nextFloat() * 0.1F + 0.9F);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}
}
