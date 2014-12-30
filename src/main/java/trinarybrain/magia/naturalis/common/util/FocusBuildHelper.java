package trinarybrain.magia.naturalis.common.util;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public final class FocusBuildHelper
{
	public static Meta getMeta(ItemStack stack)
	{
		if(stack == null || !stack.hasTagCompound())
		{
			return Meta.NONE;
		}
		else
		{
			NBTTagList nbttaglist = stack.stackTagCompound.getTagList("hitu", 10);
			if(nbttaglist == null)
			{
				return Meta.NONE;
			}
			else
			{
				int i = nbttaglist.getCompoundTagAt(0).getByte("meta");
				switch(i)
				{
				case 0:
					return Meta.NONE;
				case 1:
					return Meta.UNIFORM;
				default:
					return Meta.NONE;
				}
			}
		}
	}

	public static boolean setMeta(ItemStack stack, Meta meta)
	{
		if(stack == null)
		{
			return false;
		}
		else
		{
			NBTTagList nbttaglist = stack.stackTagCompound.getTagList("hitu", 10);
			NBTTagCompound nbttagcompound;

			if(nbttaglist == null)
			{
				nbttaglist = new NBTTagList();
				nbttagcompound = new NBTTagCompound();
			}
			else
			{
				nbttagcompound = nbttaglist.getCompoundTagAt(0);
			}

			nbttagcompound.setByte("meta", (byte)meta.ordinal());			
			nbttaglist.appendTag(nbttagcompound);

			if (nbttaglist.tagCount() > 0)
			{

				stack.setTagInfo("hitu", nbttaglist);
				return true;

			}
			else if (stack.hasTagCompound())
			{
				stack.getTagCompound().removeTag("hitu");
			}
		}
		return false;
	}

	public static Shape getShape(ItemStack stack)
	{
		if (stack == null || !stack.hasTagCompound())
		{
			return Shape.NONE;
		}
		else
		{
			NBTTagList nbttaglist = stack.stackTagCompound.getTagList("hitu", 10);
			if (nbttaglist == null)
			{
				return Shape.NONE;
			}
			else
			{
				return getShapeByID(nbttaglist.getCompoundTagAt(0).getByte("shape"));
			}
		}
	}

	public static boolean setShape(ItemStack stack, Shape shape)
	{
		if(stack == null) return false;

		NBTTagCompound data = NBTUtil.openNbtData(stack);
		NBTTagList nbttaglist = data.getTagList("hitu", 10);
		NBTTagCompound tempData;
		
		if(nbttaglist == null)
		{
			nbttaglist = new NBTTagList();
			tempData = new NBTTagCompound();
		}
		else
		{
			tempData = nbttaglist.getCompoundTagAt(0);
		}

		tempData.setByte("shape", (byte)shape.ordinal());			
		nbttaglist.appendTag(tempData);

		if(nbttaglist.tagCount() > 0)
		{

			stack.setTagInfo("hitu", nbttaglist);
			return true;

		}

		return false;
	}

	public static int getSize(ItemStack stack)
	{
		if(stack == null || !stack.hasTagCompound())
		{
			return 1;
		}
		else
		{
			NBTTagList nbttaglist = stack.stackTagCompound.getTagList("hitu", 10);
			if(nbttaglist == null)
			{
				return 1;
			}
			else
			{
				return nbttaglist.getCompoundTagAt(0).getByte("size");
			}
		}
	}

	public static boolean setSize(ItemStack stack, int size)
	{
		if(stack == null) return false;

		NBTTagCompound data = NBTUtil.openNbtData(stack);
		NBTTagList nbttaglist = data.getTagList("hitu", 10);
		NBTTagCompound tempData;

		if(nbttaglist == null)
		{
			nbttaglist = new NBTTagList();
			tempData = new NBTTagCompound();
		}
		else
		{
			tempData = nbttaglist.getCompoundTagAt(0);
		}

		tempData.setByte("size", (byte)size);			
		nbttaglist.appendTag(tempData);

		if(nbttaglist.tagCount() > 0)
		{
			stack.setTagInfo("hitu", nbttaglist);
			return true;
		}
		
		return false;
	}

	public static boolean setpickedBlock(ItemStack stack, Block block, int metadata)
	{
		if(stack == null) return false;

		int bid = Block.getIdFromBlock(block);
		if(metadata < 0 || metadata > 15) metadata = 0;
		
		NBTTagCompound data = NBTUtil.openNbtData(stack);
		NBTTagList nbttaglist = data.getTagList("hitu", 10);
		NBTTagCompound nbttagcompound;

		if(nbttaglist == null)
		{
			nbttaglist = new NBTTagList();
			nbttagcompound = new NBTTagCompound();
		}
		else
		{
			nbttagcompound = nbttaglist.getCompoundTagAt(0);
		}

		nbttagcompound.setInteger("bid", bid);
		nbttagcompound.setByte("bdata", (byte)metadata);

		nbttaglist.appendTag(nbttagcompound);

		if(nbttaglist.tagCount() > 0)
		{

			stack.setTagInfo("hitu", nbttaglist);
			return true;

		}
		return false;
	}

	public static int[] getPickedBlock(ItemStack stack)
	{
		int[] i = {0, 0};
		if (stack == null || !stack.hasTagCompound())
		{
			return i;
		}
		else
		{
			NBTTagList nbttaglist = stack.stackTagCompound.getTagList("hitu", 10);
			if(nbttaglist == null)
			{
				return i;
			}
			else
			{
				i[0] = nbttaglist.getCompoundTagAt(0).getInteger("bid");
				i[1] = nbttaglist.getCompoundTagAt(0).getByte("bdata");
				return i;
			}
		}
	}
	
	public enum Meta
	{
		NONE,
		UNIFORM;
		
		public String toString()
		{
			switch(this)
			{
			case NONE:
				return Platform.translate("enum.hitu.none");
			case UNIFORM:
				return Platform.translate("enum.hitu.uniform");
			default:
				break;			
			}
			return Platform.translate("enum.hitu.unknown");
		}
	}

	public enum Shape
	{
		NONE,
		PLANE,
		CUBE,
		PLANE_EXTEND,
		SPHERE;
		
		public String toString()
		{
			switch(this)
			{
			case CUBE:
				return Platform.translate("enum.hitu.cube");
			case NONE:
				return Platform.translate("enum.hitu.none");
			case PLANE:
				return Platform.translate("enum.hitu.plane");
			case PLANE_EXTEND:
				return Platform.translate("enum.hitu.plane.extend");
			case SPHERE:
				return Platform.translate("enum.hitu.sphere");
			default:
				break;
			}
			return Platform.translate("enum.hitu.unknown");
		}
	}

	public static Shape getShapeByID(int i)
	{
		switch(i)
		{
		case 0:
			return Shape.NONE;
		case 1:
			return Shape.PLANE;
		case 2:
			return Shape.CUBE;
		case 3:
			return Shape.PLANE_EXTEND;
		case 4:
			return Shape.SPHERE;
		default:
			return Shape.NONE;
		}
	}
}
