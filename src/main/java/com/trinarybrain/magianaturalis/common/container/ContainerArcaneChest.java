package com.trinarybrain.magianaturalis.common.container;

import com.trinarybrain.magianaturalis.common.tile.TileArcaneChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerArcaneChest extends Container {

    int type;

    private final TileArcaneChest chest;

    public ContainerArcaneChest(InventoryPlayer inventoryPlayer, TileArcaneChest tile) {
        chest = tile;
        chest.openInventory();
        type = chest.getChestType();

        int tempIndex = 0;
        int offset = ((type - 1) * 18);

        for (int i = 0; i < 6 - 1 + type; i++)
            for (int j = 0; j < 9 + ((type - 1) * 2); j++)
                addSlotToContainer(new Slot(tile, tempIndex++, 8 + j * 18, 18 + i * 18));

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18 + offset, 140 + i * 18 + offset));

        for (int i = 0; i < 9; i++)
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18 + offset, 198 + offset));
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return chest.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack stack = null;
        Slot slot = (Slot) inventorySlots.get(index);

        if ((slot != null) && (slot.getHasStack())) {
            ItemStack tempStack = slot.getStack();
            stack = tempStack.copy();

            int inv = 54 + ((type - 1) * 23);

            if (index < inv + 1) {
                if (!mergeItemStack(tempStack, inv, inventorySlots.size(), true)) {
                    return null;
                }
            }
            else if (!mergeItemStack(tempStack, 0, inv, false)) {
                return null;
            }

            if (tempStack.stackSize == 0)
                slot.putStack((ItemStack) null);
            else
                slot.onSlotChanged();

            if (tempStack.stackSize == stack.stackSize) return null;

            slot.onPickupFromSlot(player, tempStack);
        }
        return stack;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        chest.closeInventory();
    }

}
