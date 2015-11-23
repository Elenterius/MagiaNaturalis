package com.trinarybrain.magianaturalis.common.item.focus;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.BlockCoordinates;
import thaumcraft.api.IArchitect;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.ItemWandCasting;
import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.util.FocusBuildHelper;
import com.trinarybrain.magianaturalis.common.util.FocusBuildHelper.Meta;
import com.trinarybrain.magianaturalis.common.util.FocusBuildHelper.Shape;
import com.trinarybrain.magianaturalis.common.util.Platform;
import com.trinarybrain.magianaturalis.common.util.ResourceUtil;
import com.trinarybrain.magianaturalis.common.util.WorldCoord;
import com.trinarybrain.magianaturalis.common.util.WorldUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFocusBuild extends ItemFocusBasic implements IArchitect
{
	public static double reachDistance = 5.0D;

	public ItemFocusBuild()
	{
		super();
		setCreativeTab(MagiaNaturalis.creativeTab);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List lines, boolean advancedItemTooltips)
	{
		super.addInformation(stack, player, lines, advancedItemTooltips);
		lines.add("");
		lines.add(EnumChatFormatting.DARK_GRAY + "Meta: " + FocusBuildHelper.getMeta(stack));
		lines.add(EnumChatFormatting.DARK_GRAY + "Shape: " + FocusBuildHelper.getShape(stack) + "  Size: "+FocusBuildHelper.getSize(stack));
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTabs, List list)
	{
		ItemStack stack = new ItemStack(item, 1, 0);
		applyUpgrade(stack, FocusUpgradeType.architect, 1);
		FocusBuildHelper.setSize(stack, 1);
		FocusBuildHelper.setShape(stack, Shape.CUBE);
		list.add(stack);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		icon = ir.registerIcon(ResourceUtil.PREFIX +  "focus_build");
	}

	@Override
	public int getFocusColor(ItemStack focusstack)
	{
		return 0x857B93;
	}

	@Override
	public AspectList getVisCost(ItemStack focusstack)
	{
		return new AspectList().add(Aspect.ORDER, 5).add(Aspect.EARTH, 5);
	}

	public int getMaxAreaSize(ItemStack focusstack)
	{
		return 3 + getUpgradeLevel(focusstack, FocusUpgradeType.enlarge) * 3 + 1;
	}

	@Override
	public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack focusstack, int rank)
	{
		return new FocusUpgradeType[] {FocusUpgradeType.enlarge, FocusUpgradeType.frugal};
	}

	public ItemStack onFocusRightClick(ItemStack wandstack, World world, EntityPlayer player, MovingObjectPosition movingobjectposition)
	{
		player.swingItem();
		if(Platform.isClient()) return wandstack;

		MovingObjectPosition target = WorldUtil.getMovingObjectPositionFromPlayer(world, player, reachDistance, true);

		if(target != null && target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
		{
			int x = target.blockX;
			int y = target.blockY;
			int z = target.blockZ;

			ItemWandCasting wand = (ItemWandCasting) wandstack.getItem();

//			if(player.isSneaking())
//			{
//				FocusBuildHelper.setpickedBlock(focusStack, world.getBlock(x, y, z), world.getBlockMetadata(x, y, z));
//				wand.setFocus(wandstack, focusStack); //update focus with new NBT data in wand
//				return wandstack;
//			}

			float hitX = (float) (target.hitVec.xCoord - x);
			float hitY = (float) (target.hitVec.yCoord - y);
			float hitZ = (float) (target.hitVec.zCoord - z);

			hitX = Math.abs(hitX);
			hitY = Math.abs(hitY); //Wasted Operation since blocks can't be placed below y=0 in "default" Minecraft!
			hitZ = Math.abs(hitZ);

			if(!onFocusUse(wandstack, player, world, x, y, z, target.sideHit, hitX, hitY, hitZ))
				world.playSoundAtEntity(player, "thaumcraft:wandfail", 0.5F, 0.8F + world.rand.nextFloat() * 0.1F);
		}

		return wandstack;
	}

	public boolean onFocusUse(ItemStack wandStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!player.capabilities.allowEdit) return false;

		ItemWandCasting wand = (ItemWandCasting) wandStack.getItem();
		ItemStack focusStack = wand.getFocusItem(wandStack);

		int size = FocusBuildHelper.getSize(focusStack);
		if(size < 1 || size > getMaxAreaSize(focusStack)) return false;

		Shape shape = FocusBuildHelper.getShape(focusStack);
		if(shape == Shape.NONE) return false;

		Block pblock = null;
		int pbdata = 0;

		if(FocusBuildHelper.getMeta(focusStack) == Meta.UNIFORM)
		{
			pblock = world.getBlock(x, y, z);
			pbdata = world.getBlockMetadata(x, y, z);
		}
		else
		{
			int[] i = FocusBuildHelper.getPickedBlock(focusStack);
			pblock = Block.getBlockById(i[0]);
			pbdata = i[1];
		}

		if(pblock == null || pblock == Blocks.air) return false;
		if(pbdata < 0 || pbdata > 15) return false;

		return buildAction(wandStack, player, world, x, y, z, side, hitX, hitY, hitZ, size, pblock, pbdata);
	}

	private boolean buildAction(ItemStack wandStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int size, Block pickedblock, int pbData)
	{
		List blocks = null;
		ForgeDirection face = ForgeDirection.getOrientation(side);
		ItemWandCasting wand = (ItemWandCasting) wandStack.getItem();

		switch(FocusBuildHelper.getShape(wand.getFocusItem(wandStack)))
		{
		case CUBE:
			x += face.offsetX * size;
			y += face.offsetY * size;
			z += face.offsetZ * size;
			blocks = WorldUtil.plot3DCubeArea(player, world, x, y, z, side, hitX, hitY, hitZ, size);
			break;

		case PLANE:
			x += face.offsetX;
			y += face.offsetY;
			z += face.offsetZ;
			blocks = WorldUtil.plot2DPlane(player, world, x, y, z, side, hitX, hitY, hitZ, size);
			break;

		case PLANE_EXTEND:
			x += face.offsetX*(size+1)/2;
			y += face.offsetY*(size+1)/2;
			z += face.offsetZ*(size+1)/2;
			blocks = WorldUtil.plot2DPlaneExtension(player, world, x, y, z, side, hitX, hitY, hitZ, size);
			break;

		case SPHERE:
			x += face.offsetX * size;
			y += face.offsetY * size;
			z += face.offsetZ * size;
			blocks = WorldUtil.plot3DCubeArea(player, world, x, y, z, side, hitX, hitY, hitZ, size);
			break;

		case NONE:
			break;
		default:
			break;
		}

		if(blocks.size() == 0) return false;

		if(blocks.size() > 0)
		{
			int ls = blocks.size();
			if(!player.capabilities.isCreativeMode)
			{
				double costD = blocks.size() * 5;
				if(costD > wand.getVis(wandStack, Aspect.ORDER))
				{
					int i = (int) (blocks.size() - (blocks.size() - wand.getVis(wandStack, Aspect.ORDER) / 5));
					ls = i;
				}

				if(ls == 0) return false;

				ItemStack tempStack = new ItemStack(pickedblock, 1, pbData);
				if(tempStack.getItem() != null)
				{
					int itemAmount = 0;
					for(int i = 0; i < player.inventory.mainInventory.length; ++i)
					{
						if(player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].isItemEqual(tempStack))
						{
							itemAmount += player.inventory.mainInventory[i].stackSize;
						}
					}

					if(itemAmount < ls)
						ls = itemAmount;

					if(ls == 0) return false;

					for(int j = 0; j < ls; j++)
					{
						player.inventory.consumeInventoryItem(tempStack.getItem());
					}

					player.inventoryContainer.detectAndSendChanges();

					int costN = 5 * ls;
					if(!ThaumcraftApiHelper.consumeVisFromWand(wandStack, player, new AspectList().add(Aspect.ORDER, costN).add(Aspect.EARTH, costN), true, false))
					{
						return false;
					}
				}
				else
				{
					ls = 0;
				}
			}

			if(ls == 0) return false;

			for(int i = 0; i < ls; i++)
			{
				WorldCoord temp = (WorldCoord)blocks.get(i);
				world.setBlock(temp.x, temp.y, temp.z, pickedblock, pbData, 3);
			}
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<BlockCoordinates> getArchitectBlocks(ItemStack stack, World world, int x, int y, int z, int side, EntityPlayer player)
	{
		//TODO: render not working with extendeed reach distance
		MovingObjectPosition target = WorldUtil.getMovingObjectPositionFromPlayer(world, player, reachDistance, true); //have to call it here for hitVec

		if(target != null && target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
		{
			x = target.blockX;
			y = target.blockY;
			z = target.blockZ;

			Block block1 = player.worldObj.getBlock(x, y, z);
			if(block1 == null) return null;

			int b1damage = block1.getDamageValue(player.worldObj, x, y, z);

			if(stack != null)
			{
				ItemStack stackFocus = null;

				if(stack.getItem() instanceof ItemWandCasting)
				{
					ItemWandCasting wand = ((ItemWandCasting) stack.getItem());
					stackFocus = wand.getFocusItem(stack);
				}
				else if(stack.getItem() instanceof ItemFocusBuild)
				{
					stackFocus = stack;
				}
				else
				{
					return null;
				}

				float hitX = (float) (target.hitVec.xCoord - x);
				float hitY = (float) (target.hitVec.yCoord - y);
				float hitZ = (float) (target.hitVec.zCoord - z);

				hitX = Math.abs(hitX);
				hitY = Math.abs(hitY);
				hitZ = Math.abs(hitZ);

				ForgeDirection face = ForgeDirection.getOrientation(target.sideHit);

				ArrayList blocks = null;
				int size = FocusBuildHelper.getSize(stackFocus);
				if(size < 1 || size > getMaxAreaSize(stackFocus)) return null;

				switch(FocusBuildHelper.getShape(stackFocus))
				{
				case CUBE:
					x += face.offsetX * size;
					y += face.offsetY * size;
					z += face.offsetZ * size;
					blocks = (ArrayList) WorldUtil.plot3DCubeArea(player, player.worldObj, x, y, z, target.sideHit, hitX, hitY, hitZ, size);
					break;

				case PLANE:
					x += face.offsetX;
					y += face.offsetY;
					z += face.offsetZ;
					blocks = (ArrayList) WorldUtil.plot2DPlane(player, player.worldObj, x, y, z, target.sideHit, hitX, hitY, hitZ, size);
					break;

				case PLANE_EXTEND:
					x += face.offsetX*(size+1)/2;
					y += face.offsetY*(size+1)/2;
					z += face.offsetZ*(size+1)/2;
					blocks = (ArrayList) WorldUtil.plot2DPlaneExtension(player, player.worldObj, x, y, z, target.sideHit, hitX, hitY, hitZ, size);
					break;

				case SPHERE:
					x += face.offsetX * size;
					y += face.offsetY * size;
					z += face.offsetZ * size;
					blocks = (ArrayList) WorldUtil.plot3DCubeArea(player, player.worldObj, x, y, z, target.sideHit, hitX, hitY, hitZ, size);
					break;

				case NONE:
					break;
				default:
					break;
				}

				if(blocks == null) return null;
				return (ArrayList<BlockCoordinates>) blocks;
			}
		}
		return null;
	}

	@Override
	public boolean showAxis(ItemStack stack, World world, EntityPlayer player, int side, EnumAxis axis)
	{
		return false;
	}
}
