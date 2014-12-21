package trinarybrain.magia.naturalis.common.util;

import java.util.ArrayList;
import java.util.UUID;

import trinarybrain.magia.naturalis.common.util.access.UserAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public final class NBTUtil
{
	public static NBTTagCompound openNbtData(ItemStack stack)
	{
		NBTTagCompound data = stack.getTagCompound();
		if(data == null) stack.setTagCompound(data = new NBTTagCompound());
		return data;
	}

	public static NBTTagList newDoubleNBTList(double[] doubleArray)
	{
		NBTTagList nbttaglist = new NBTTagList();
		for(Double d1 : doubleArray)
		{
			nbttaglist.appendTag(new NBTTagDouble(d1));
		}
		return nbttaglist;
	}

	public static void saveInventoryToNBT(ItemStack stack, ItemStack[] inventory)
	{
		NBTUtil.saveInventoryToNBT(NBTUtil.openNbtData(stack), inventory);
	}

	public static void saveInventoryToNBT(NBTTagCompound data, ItemStack[] inventory)
	{
		if(inventory != null)
		{
			NBTTagList nbttaglist = new NBTTagList();
			for(int i = 0; i < inventory.length; i++)
			{
				if(inventory[i] != null)
				{
					NBTTagCompound tempData = new NBTTagCompound();
					tempData.setByte("Slot", (byte)i);
					inventory[i].writeToNBT(tempData);
					nbttaglist.appendTag(tempData);
				}
			}
			data.setTag("Items", nbttaglist);
		}
	}

	public static ArrayList<ItemStack> loadInventoryFromNBT(ItemStack stack)
	{
		NBTTagCompound data = NBTUtil.openNbtData(stack);
		if(!data.hasKey("Items")) return null;
		NBTTagList nbttaglist = data.getTagList("Items", NBT.TAG_COMPOUND);
		ArrayList<ItemStack> inventory = new ArrayList();
		for(int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound tempData = nbttaglist.getCompoundTagAt(i);
			byte j = tempData.getByte("Slot");
			if(j >= 0)
			{
				inventory.add(ItemStack.loadItemStackFromNBT(tempData));
			}
		}
		return inventory;
	}

	public static ItemStack[] loadInventoryFromNBT(ItemStack stack, int invSize)
	{
		return NBTUtil.loadInventoryFromNBT(NBTUtil.openNbtData(stack), invSize);
	}

	public static ItemStack[] loadInventoryFromNBT(NBTTagCompound data, int invSize)
	{
		if(!data.hasKey("Items")) return null;
		NBTTagList nbttaglist = data.getTagList("Items", NBT.TAG_COMPOUND);
		ItemStack[] inventory = new ItemStack[invSize];
		for(int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound tempData = nbttaglist.getCompoundTagAt(i);
			byte j = tempData.getByte("Slot");
			if(j >= 0)
			{
				inventory[i] = ItemStack.loadItemStackFromNBT(tempData);
			}
		}
		return inventory;
	}

	public static void saveUserAccesToNBT(ItemStack stack, ArrayList<UserAccess> list)
	{
		NBTUtil.saveUserAccesToNBT(NBTUtil.openNbtData(stack), list);
	}
	public static ArrayList<UserAccess> loadUserAccesFromNBT(ItemStack stack)
	{
		return NBTUtil.loadUserAccesFromNBT(NBTUtil.openNbtData(stack));
	}

	public static void saveUserAccesToNBT(NBTTagCompound data, ArrayList<UserAccess> list)
	{
		if(list != null)
		{
			NBTTagList accessList = new NBTTagList();
			for(UserAccess user : list)
			{
				NBTTagCompound tempData = new NBTTagCompound();
				tempData.setString("UUID", user.getUUID().toString());
				tempData.setByte("Type", user.getAccessLevel());	
				accessList.appendTag(tempData);
			}
			data.setTag("AccessList", accessList);
		}
	}

	public static ArrayList<UserAccess> loadUserAccesFromNBT(NBTTagCompound data)
	{
		if(!data.hasKey("AccessList")) return new ArrayList();
		NBTTagList tagList = data.getTagList("AccessList", NBT.TAG_COMPOUND);

		ArrayList<UserAccess> accessList = new ArrayList();
		UserAccess user;
		for(int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound tempData = tagList.getCompoundTagAt(i);
			user = new UserAccess();
			user.setUUID(UUID.fromString(tempData.getString("UUID")));
			user.setAccesLevel(tempData.getByte("Type"));
			accessList.add(user);
		}
		return accessList;
	}
	
	public static boolean spawnEntityFromNBT(NBTTagCompound data, World world, double x, double y, double z)
	{
		if(data != null && data.hasKey("id") && world != null)
		{
			data.setTag("Pos", NBTUtil.newDoubleNBTList(new double[] { x, y, z }));
			data.setTag("Motion", NBTUtil.newDoubleNBTList(new double[] { 0.0D, 0.0D, 0.0D }));
			data.setFloat("FallDistance", 0.0F);
			data.setInteger("Dimension", world.provider.dimensionId);
			Entity entity = EntityList.createEntityFromNBT(data, world);
			if(entity == null) return false;
			return world.spawnEntityInWorld(entity);
		}
		return false;
	}
}
