package trinarybrain.magia.naturalis.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import trinarybrain.magia.naturalis.common.tile.TileArcaneChest;

public class ContainerArcaneChest extends Container
{
	private TileArcaneChest chest;

	public ContainerArcaneChest(InventoryPlayer inventoryPlayer, TileArcaneChest tile)
	{
		this.chest = tile;
		this.chest.openInventory();
		int tempIndex = 0;
		for(int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(tile, tempIndex++ , 8 + j * 18, 18 + i * 18));
			}
		}

		for(int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 140 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 198));
		}		
	}

	public boolean canInteractWith(EntityPlayer player)
	{
		return this.chest.isUseableByPlayer(player);
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int index)
	{
		ItemStack stack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if((slot != null) && (slot.getHasStack()))
		{
			ItemStack tempStack = slot.getStack();
			stack = tempStack.copy();	

			if(index < 55)
			{
				if(!this.mergeItemStack(tempStack, 54, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if(!this.mergeItemStack(tempStack, 0, 54, false))
			{
				return null;
			}

			if(tempStack.stackSize == 0)
				slot.putStack((ItemStack)null);
			else
				slot.onSlotChanged();

			if(tempStack.stackSize == stack.stackSize) return null;

			slot.onPickupFromSlot(player, tempStack);
		}
		return stack;
	}
	
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);
        this.chest.closeInventory();
    }
}
