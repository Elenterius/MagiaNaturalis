package trinarybrain.magia.naturalis.common.tile;

import java.util.ArrayList;
import java.util.UUID;

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
import trinarybrain.magia.naturalis.common.block.BlockArcaneChest;
import trinarybrain.magia.naturalis.common.block.BlocksMN;
import trinarybrain.magia.naturalis.common.util.NBTUtil;
import trinarybrain.magia.naturalis.common.util.Platform;
import trinarybrain.magia.naturalis.common.util.ResourceUtil;
import trinarybrain.magia.naturalis.common.util.access.UserAccess;

public class TileArcaneChest extends TileThaumcraft implements ISidedInventory, IWandable
{
	private ItemStack[] inventory = new ItemStack[54];
	public float lidAngle;
	public float prevLidAngle;
	public int numUsingPlayers;

	public UUID owner;
	//UserAccess - accessLevel: 0 - nothing, 1 - access, 2 - administrator;
	public ArrayList<UserAccess> accessList = new ArrayList();
	private byte chestType = 0;

	public final String name[] = new String[] {"unknown", "gw", "sw"};
	private String customName;
	private static final int[] sides = { 0, 1, 2, 3, 4, 5 };

	@Override
	public int getSizeInventory()
	{
		return 54;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.inventory[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int amount)
	{
		if(this.inventory[index] == null) return null;

		if(this.inventory[index].stackSize <= amount)
		{
			ItemStack stack = this.inventory[index];
			this.inventory[index] = null;
			this.markDirty();
			return stack;
		}

		ItemStack stack = this.inventory[index].splitStack(amount);
		if(this.inventory[index].stackSize == 0)
		{
			this.inventory[index] = null;
		}
		this.markDirty();
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index)
	{
		if(this.inventory[index] == null) return null;

		ItemStack stack = this.inventory[index];
		this.inventory[index] = null;
		this.markDirty();
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.inventory[index] = stack;
		if(stack != null && stack.stackSize > this.getInventoryStackLimit())
		{
			stack.stackSize = this.getInventoryStackLimit();
		}
		this.markDirty();
	}

	public void setInvetory(ItemStack[] inventory)
	{
		this.inventory = inventory;
	}

	@Override
	public String getInventoryName()
	{
		return this.hasCustomInventoryName() ? this.customName : Platform.translate("tile." + ResourceUtil.PREFIX + "arcaneChest." + name[this.getChestType()] + ".name");
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return this.customName != null && this.customName.length() > 0;
	}

	public void setGuiName(String name)
	{
		this.customName = name;
	}

	public int getChestType()
	{
		return this.chestType;
	}

	public void setChestType(byte type)
	{
		this.chestType = type;
	}

	@Override
	public void readFromNBT(NBTTagCompound data)
	{
		super.readFromNBT(data);

		NBTUtil.loadInventoryFromNBT(data, this.getSizeInventory());
		if(data.hasKey("CustomName"))
		{
			this.customName = data.getString("CustomName");
		}
	}
	
	public void readCustomNBT(NBTTagCompound data)
    {
		this.owner = UUID.fromString(data.getString("owner"));
		this.chestType = data.getByte("Type");
		this.accessList = NBTUtil.loadUserAccesFromNBT(data);
    }

	@Override
	public void writeToNBT(NBTTagCompound data)
	{
		super.writeToNBT(data);

		NBTUtil.saveInventoryToNBT(data, this.inventory);
		if(this.hasCustomInventoryName())
		{
			data.setString("CustomName", this.customName);
		}
	}
	
	public void writeCustomNBT(NBTTagCompound data)
    {
		data.setString("owner", this.owner.toString());
		data.setByte("Type", this.chestType);
		NBTUtil.saveUserAccesToNBT(data, this.accessList);
    }

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this;
	}

	@Override
	public void openInventory()
	{
		if(this.numUsingPlayers < 0)
		{
			this.numUsingPlayers = 0;
		}
		this.numUsingPlayers += 1;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, BlocksMN.arcaneChest, 1, this.numUsingPlayers);
	}

	@Override
	public void closeInventory()
	{
		this.numUsingPlayers -= 1;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, BlocksMN.arcaneChest, 1, this.numUsingPlayers);
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack)
	{
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side)
	{
		return sides;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, int side)
	{
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, int side)
	{
		return false;
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		this.prevLidAngle = this.lidAngle;
		float angle = 0.1F;

		if(this.numUsingPlayers > 0 && this.lidAngle == 0.0F)
			this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);

		if((this.numUsingPlayers == 0 && this.lidAngle > 0.0F) || (this.numUsingPlayers > 0 && this.lidAngle < 1.0F))
		{
			float currAngle = this.lidAngle;

			if(this.numUsingPlayers > 0)
				this.lidAngle += angle;
			else
				this.lidAngle -= angle;

			if(this.lidAngle > 1.0F)
				this.lidAngle = 1.0F;

			if(this.lidAngle < 0.5F && currAngle >= 0.5F)
				this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);

			if(this.lidAngle < 0.0F)
				this.lidAngle = 0.0F;
		}
	}

	@Override
	public boolean receiveClientEvent(int eventID, int arg)
	{
		if(eventID == 1)
		{
			this.numUsingPlayers = arg;
			return true;
		}

		if(eventID == 2)
		{
			if(this.lidAngle < arg / 10.0F) this.lidAngle = (arg / 10.0F);
			return true;
		}
		return this.tileEntityInvalid;
	}

	@Override
	public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side, int md)
	{
		return 0;
	}

	@Override
	public ItemStack onWandRightClick(World world, ItemStack stack, EntityPlayer player)
	{
		if(Platform.isServer() && !world.restoringBlockSnapshots)
		{
			boolean hasAccess = false;
			if(player.capabilities.isCreativeMode || this.owner.equals(player.getGameProfile().getId()))
			{
				hasAccess = true;
			}
			else
			{
				hasAccess = this.accessList.contains(new UserAccess(player.getGameProfile().getId(), (byte) 2));				
			}
			if(!hasAccess)
			{
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + Platform.translate("chat.magianaturalis:chest.resist")));
				return stack;
			}

			Block block = world.getBlock(this.xCoord, this.yCoord, this.zCoord);
			if(block == null) return stack;	
			ItemStack stack1 = new ItemStack(BlocksMN.arcaneChest, 1, this.chestType);
			BlockArcaneChest.setChestType(stack1, this.chestType);
			NBTUtil.saveInventoryToNBT(stack1, this.inventory);
			if(!this.accessList.isEmpty()) NBTUtil.saveUserAccesToNBT(stack1, this.accessList);

			//chest.breakBlock(world, this.xCoord, this.yCoord, this.zCoord, chest, this.blockMetadata);
			world.removeTileEntity(this.xCoord, this.yCoord, this.zCoord);
			world.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);

			float f = 0.7F;
			double d0 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
			double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
			double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
			EntityItem entityitem = new EntityItem(world, (double)this.xCoord + d0, (double)this.yCoord + d1, (double)this.zCoord + d2, stack1);
			entityitem.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(entityitem);
		}
		return stack;
	}

	@Override
	public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count) {}

	@Override
	public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count) {}
}
