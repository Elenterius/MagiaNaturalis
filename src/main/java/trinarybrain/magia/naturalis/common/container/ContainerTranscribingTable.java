package trinarybrain.magia.naturalis.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import thaumcraft.common.container.SlotOutput;
import trinarybrain.magia.naturalis.common.tile.TileTranscribingTable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerTranscribingTable extends Container
{
	private TileTranscribingTable table;
	private int lastBreakTime;

	public ContainerTranscribingTable(InventoryPlayer inventoryPlayer, TileTranscribingTable tile)
	{
		this.table = tile;
		this.addSlotToContainer(new Slot(tile, 0, 64, 16));
		this.addSlotToContainer(new SlotOutput(tile, 1, 64, 48));

		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}
	}

	public void addCraftingToCrafters(ICrafting crafting)
	{
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, this.table.timer);
	}

	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for(int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)this.crafters.get(i);

			if(this.lastBreakTime != this.table.timer)
			{
				icrafting.sendProgressBarUpdate(this, 0, this.table.timer);
			}
		}
		this.lastBreakTime = this.table.timer;
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int i, int j)
	{
		if(i == 0)
		{
			this.table.timer = j;
		}
	}

	public boolean canInteractWith(EntityPlayer player)
	{
		return this.table.isUseableByPlayer(player);
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int index)
	{
		ItemStack stack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if((slot != null) && (slot.getHasStack()))
		{
			ItemStack tempStack = slot.getStack();
			stack = tempStack.copy();

			if(index != 0)
			{
				if ((index >= 1) && (index < 29))
				{
					if (!mergeItemStack(tempStack, 0, 38, false))
					{
						return null;
					}
				}
				else if ((index >= 29) && (index < 38) && (!mergeItemStack(tempStack, 0, 29, false)))
				{
					return null;
				}
			}
			else if(!mergeItemStack(tempStack, 2, 38, false))
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
}
