package com.trinarybrain.magianaturalis.common.tile;

import java.util.ArrayList;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.block.BlockArcaneChest;
import com.trinarybrain.magianaturalis.common.block.BlocksMN;
import com.trinarybrain.magianaturalis.common.util.NBTUtil;
import com.trinarybrain.magianaturalis.common.util.Platform;
import com.trinarybrain.magianaturalis.common.util.access.UserAccess;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.wands.IWandable;

public class TileArcaneChest extends TileThaumcraft implements ISidedInventory, IWandable {

    private ItemStack[] inventory = new ItemStack[54];
    public float lidAngle;
    public float prevLidAngle;
    public int numUsingPlayers;

    public UUID owner;
    private String ownerName;
    //UserAccess - accessLevel: 0 - nothing, 1 - access, 2 - administrator;
    public ArrayList<UserAccess> accessList = new ArrayList<>();
    private byte chestType = 0;

    public final String[] name = new String[]{"unknown", "gw", "sw"};
    private String customName;
    private static final int[] sides = {0, 1, 2, 3, 4, 5};

    public String getOwnerName() {
        if (ownerName != null && !ownerName.isEmpty())
            return ownerName;

        if (Platform.isServer() && owner != null) {
            GameProfile profile = Platform.findGameProfileByUUID(owner);
            if (profile != null) {
                return ownerName = profile.getName();
            }
            else {
                return ownerName = "Only UUID Available";
            }
        }

        return "Unknown";
    }

    @Override
    public int getSizeInventory() {
        return getChestType() == 2 ? 77 : 54;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return inventory[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int amount) {
        if (inventory[index] == null) return null;

        if (inventory[index].stackSize <= amount) {
            ItemStack stack = inventory[index];
            inventory[index] = null;
            return stack;
        }

        ItemStack stack = inventory[index].splitStack(amount);
        if (inventory[index].stackSize == 0) {
            inventory[index] = null;
        }
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int index) {
        if (inventory[index] == null) return null;

        ItemStack stack = inventory[index];
        inventory[index] = null;
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventory[index] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }
    }

    public void setInvetory(ItemStack[] inventory) {
        this.inventory = inventory;
    }

    @Override
    public String getInventoryName() {
        return hasCustomInventoryName() ? customName : Platform.translate("tile." + MagiaNaturalis.MOD_ID + ":" + "arcaneChest." + name[getChestType()] + ".name");
    }

    @Override
    public boolean hasCustomInventoryName() {
        return customName != null && !customName.isEmpty();
    }

    public void setGuiName(String name) {
        customName = name;
    }

    public int getChestType() {
        return chestType;
    }

    public void setChestType(byte type) {
        chestType = type;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        inventory = NBTUtil.loadInventoryFromNBT(data, getSizeInventory());
        if (data.hasKey("CustomName")) {
            customName = data.getString("CustomName");
        }
    }

    public void readCustomNBT(NBTTagCompound data) {
        owner = UUID.fromString(data.getString("owner"));
        chestType = data.getByte("Type");
        accessList = NBTUtil.loadUserAccessFromNBT(data);
    }

    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        NBTUtil.saveInventoryToNBT(data, inventory);
        if (hasCustomInventoryName()) {
            data.setString("CustomName", customName);
        }
    }

    public void writeCustomNBT(NBTTagCompound data) {
        data.setString("owner", owner.toString());
        data.setByte("Type", chestType);
        NBTUtil.saveUserAccessToNBT(data, accessList);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this;
    }

    @Override
    public void openInventory() {
        if (numUsingPlayers < 0)
            numUsingPlayers = 0;

        numUsingPlayers += 1;
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, BlocksMN.arcaneChest, 1, numUsingPlayers);
    }

    @Override
    public void closeInventory() {
        numUsingPlayers -= 1;
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, BlocksMN.arcaneChest, 1, numUsingPlayers);
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack) {
        return true;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return sides;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, int side) {
        return false;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, int side) {
        return false;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        prevLidAngle = lidAngle;
        float angle = 0.1F;

        if (numUsingPlayers > 0 && lidAngle == 0.0F)
            worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);

        if ((numUsingPlayers == 0 && lidAngle > 0.0F) || (numUsingPlayers > 0 && lidAngle < 1.0F)) {
            float currAngle = lidAngle;

            if (numUsingPlayers > 0)
                lidAngle += angle;
            else
                lidAngle -= angle;

            if (lidAngle > 1.0F)
                lidAngle = 1.0F;

            if (lidAngle < 0.5F && currAngle >= 0.5F)
                worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);

            if (lidAngle < 0.0F)
                lidAngle = 0.0F;
        }
    }

    @Override
    public boolean receiveClientEvent(int eventID, int arg) {
        if (eventID == 1) {
            numUsingPlayers = arg;
            return true;
        }

        if (eventID == 2) {
            if (lidAngle < arg / 10.0F) lidAngle = (arg / 10.0F);
            return true;
        }
        return tileEntityInvalid;
    }

    @Override
    public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side, int md) {
        return 0;
    }

    @Override
    public ItemStack onWandRightClick(World world, ItemStack stack, EntityPlayer player) {
        if (Platform.isServer() && !world.restoringBlockSnapshots) {
            boolean hasAccess = false;
            if (player.capabilities.isCreativeMode || owner.equals(player.getGameProfile().getId())) {
                hasAccess = true;
            }
            else {
                hasAccess = accessList.contains(new UserAccess(player.getGameProfile().getId(), (byte) 2));
            }
            if (!hasAccess) {
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + Platform.translate("chat.magianaturalis:chest.resist")));
                return stack;
            }

            Block block = world.getBlock(xCoord, yCoord, zCoord);
            if (block == null) return stack;
            ItemStack stack1 = new ItemStack(BlocksMN.arcaneChest, 1, chestType);
            BlockArcaneChest.setChestType(stack1, chestType);
            NBTUtil.saveInventoryToNBT(stack1, inventory);
            if (!accessList.isEmpty()) NBTUtil.saveUserAccessToNBT(stack1, accessList);

            //chest.breakBlock(world, xCoord, yCoord, zCoord, chest, blockMetadata);
            world.removeTileEntity(xCoord, yCoord, zCoord);
            world.setBlockToAir(xCoord, yCoord, zCoord);

            float f = 0.7F;
            double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, (double) xCoord + d0, (double) yCoord + d1, (double) zCoord + d2, stack1);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }
        return stack;
    }

    @Override
    public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count) {
    }

    @Override
    public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count) {
    }
}
