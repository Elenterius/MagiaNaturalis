package com.trinarybrain.magianaturalis.common.container;

import com.trinarybrain.magianaturalis.common.item.artifact.ItemResearchLog;
import com.trinarybrain.magianaturalis.common.tile.TileTranscribingTable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTranscribingTable extends Container
{
	private TileTranscribingTable table;
	private int lastPauseTime;

	public ContainerTranscribingTable(InventoryPlayer inventoryPlayer, TileTranscribingTable tile)
	{
		table = tile;
		addSlotToContainer(new Slot(tile, 0, 64, 16));
		addSlotToContainer(new SlotInvalid(tile, 1, 64, 48));

		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for(int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
	}

	public void addCraftingToCrafters(ICrafting crafting)
	{
		super.addCraftingToCrafters(crafting);
		crafting.sendProgressBarUpdate(this, 0, table.timer);
	}

	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for(int i = 0; i < crafters.size(); i++)
		{
			ICrafting icrafting = (ICrafting)crafters.get(i);

			if(lastPauseTime != table.timer)
			{
				icrafting.sendProgressBarUpdate(this, 0, table.timer);
			}
		}
		lastPauseTime = table.timer;
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int i, int j)
	{
		if(i == 0)
		{
			table.timer = j;
		}
	}

	public boolean canInteractWith(EntityPlayer player)
	{
		return table.isUseableByPlayer(player);
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int index)
	{
		ItemStack stack = null;
		Slot slot = (Slot) inventorySlots.get(index);

		if(slot != null && slot.getHasStack())
		{
			ItemStack selectedStack = slot.getStack();
			stack = selectedStack.copy();

			if(index == 1)
			{
				if (!mergeItemStack(selectedStack, 2, 38, true))
				{
					return null;
				}

				slot.onSlotChange(selectedStack, stack);
			}
			if(index > 1)
			{
				if(selectedStack.getItem() instanceof ItemResearchLog)
				{
					if(!mergeItemStack(selectedStack, 0, 1, false))
						return null;
				}
				else if(index > 1 && index < 29)
				{
					if(!mergeItemStack(selectedStack, 29, 38, false))
						return null;
				}
				else if(index >= 29 && index < 38 && !mergeItemStack(selectedStack, 2, 29, false))
				{
					return null;
				}
			}
			else if(!mergeItemStack(selectedStack, 2, 38, false))
			{
				return null;
			}

			if(selectedStack.stackSize == 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();

			if(selectedStack.stackSize == stack.stackSize)
				return null;

			slot.onPickupFromSlot(player, selectedStack);
		}

		return stack;
	}
}
