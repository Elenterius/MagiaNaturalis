package trinarybrain.magia.naturalis.common.item.focus;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
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
import trinarybrain.magia.naturalis.common.MagiaNaturalis;
import trinarybrain.magia.naturalis.common.util.FocusBuildHelper;
import trinarybrain.magia.naturalis.common.util.FocusBuildHelper.Meta;
import trinarybrain.magia.naturalis.common.util.FocusBuildHelper.Shape;
import trinarybrain.magia.naturalis.common.util.Platform;
import trinarybrain.magia.naturalis.common.util.WorldCoord;
import trinarybrain.magia.naturalis.common.util.WorldUtils;

public class ItemFocusBuild extends ItemFocusBasic implements IArchitect
{
	private static final AspectList cost = new AspectList().add(Aspect.ORDER, 5).add(Aspect.EARTH, 5);
	public int maxSize = 16;

	public ItemFocusBuild()
	{
		super();
		this.setCreativeTab(MagiaNaturalis.creativeTab);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List lines, boolean advancedItemTooltips)
	{
		super.addInformation(stack, player, lines, advancedItemTooltips);
		lines.add("");
		lines.add(EnumChatFormatting.BLUE+"Meta: "+FocusBuildHelper.getMeta(stack));
		lines.add(EnumChatFormatting.BLUE+"Shape: "+FocusBuildHelper.getShape(stack)+"  Size: "+FocusBuildHelper.getSize(stack));
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister ir)
	{
		this.icon = ir.registerIcon("thaumcraft:focus_trade");
	}

	@Override
	public int getFocusColor(ItemStack focusstack)
	{
		return 0;
	}

	@Override
	public AspectList getVisCost(ItemStack focusstack)
	{
		return cost;
	}

	@Override
	public FocusUpgradeType[] getPossibleUpgradesByRank(ItemStack focusstack, int rank)
	{
		return new FocusUpgradeType[] {FocusUpgradeType.architect};
	}

	public ItemStack onFocusRightClick(ItemStack wandstack, World world, EntityPlayer player, MovingObjectPosition movingobjectposition)
	{
		if(Platform.isClient()) 
			return wandstack;
		
		MovingObjectPosition target = this.getMovingObjectPositionFromPlayer(world, player, true);

		if(target == null)
			return wandstack;

		if(target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
		{
			int x = target.blockX;
			int y = target.blockY;
			int z = target.blockZ;
			
			if(player.isSneaking())
			{
				ItemWandCasting wand = (ItemWandCasting) wandstack.getItem();
				ItemStack stackFocus = wand.getFocusItem(wandstack);
				
				FocusBuildHelper.setMeta(stackFocus, Meta.UNIFORM);
				FocusBuildHelper.setShape(stackFocus, Shape.CUBE);
				FocusBuildHelper.setSize(stackFocus, 2);
				FocusBuildHelper.setpickedBlock(stackFocus, world.getBlock(x, y, z), world.getBlockMetadata(x, y, z));
				
				wand.setFocus(wandstack, stackFocus);
				
				return wandstack;
			}
			
			float hitX = (float) (target.hitVec.xCoord - x);
			float hitY = (float) (target.hitVec.yCoord - y);
			float hitZ = (float) (target.hitVec.zCoord - z);

			hitX = Math.abs(hitX);
			hitY = Math.abs(hitY); //Wasted Operation since blocks can't be placed below y=0 in "default" minecraft!
			hitZ = Math.abs(hitZ);

			this.onFocusUse(wandstack, player, world, x, y, z, target.sideHit, hitX, hitY, hitZ);
		}

		return wandstack;
	}

	public boolean onFocusUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!player.capabilities.allowEdit) 
			return false;

		ItemWandCasting wand = (ItemWandCasting) stack.getItem();
		
		int size = FocusBuildHelper.getSize(wand.getFocusItem(stack));
		if(size < 1 || size > this.maxSize)
			return false;

		Shape lshape = FocusBuildHelper.getShape(wand.getFocusItem(stack));
		if(lshape == Shape.NONE)
			return false;

		int[] i = FocusBuildHelper.getPickedBlock(wand.getFocusItem(stack));
		Block pblock = Block.getBlockById(i[0]);
		int pbdata = i[1];

		if(FocusBuildHelper.getMeta(wand.getFocusItem(stack)) == Meta.UNIFORM)
		{
			pblock = world.getBlock(x, y, z);
			pbdata = world.getBlockMetadata(x, y, z);
		}

		if(pblock == null)
			return false;

		if(pbdata < 0 || pbdata > 15)
			return false;

		return this.buildAction(stack, player, world, x, y, z, side, hitX, hitY, hitZ, size, pblock, pbdata);
	}

	private boolean buildAction(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int size, Block pickedblock, int pbData)
	{
		List blocks = null;
		ForgeDirection face = ForgeDirection.getOrientation(side);
		ItemWandCasting wand = (ItemWandCasting) stack.getItem();
		
		switch(FocusBuildHelper.getShape(wand.getFocusItem(stack)))
		{
		case CUBE:
			x += face.offsetX * size;
			y += face.offsetY * size;
			z += face.offsetZ * size;
			blocks = WorldUtils.plot3DCubeArea(player, world, x, y, z, side, hitX, hitY, hitZ, size);
			break;

		case PLANE:
			x += face.offsetX;
			y += face.offsetY;
			z += face.offsetZ;
			blocks = WorldUtils.plot2DPlane(player, world, x, y, z, side, hitX, hitY, hitZ, size);
			break;

		case PLANE_EXTEND:
			x += face.offsetX*(size+1)/2;
			y += face.offsetY*(size+1)/2;
			z += face.offsetZ*(size+1)/2;
			blocks = WorldUtils.plot2DPlaneExtension(player, world, x, y, z, side, hitX, hitY, hitZ, size);
			break;

		case SPHERE:
			x += face.offsetX * size;
			y += face.offsetY * size;
			z += face.offsetZ * size;
			blocks = WorldUtils.plot3DCubeArea(player, world, x, y, z, side, hitX, hitY, hitZ, size);
			break;

		case NONE:
			break;
		default:
			break;
		}

		if(blocks.size() == 0)
			return false;

		if(blocks.size() > 0)
		{
			int ls = blocks.size();
			if(!player.capabilities.isCreativeMode)
			{
				double costD = blocks.size() * 5;
				if(costD > wand.getVis(stack, Aspect.ORDER))
				{
					int i = (int) (blocks.size() - (blocks.size() - wand.getVis(stack, Aspect.ORDER) / 5));
					ls = i;
				}

				if(ls == 0)
					return false;

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

					if(ls == 0)
						return false;

					for(int j = 0; j < ls; j++)
					{
						player.inventory.consumeInventoryItem(tempStack.getItem());	
					}

					player.inventoryContainer.detectAndSendChanges();

					int costN = 5 * ls;
					if(!ThaumcraftApiHelper.consumeVisFromWand(stack, player, new AspectList().add(Aspect.ORDER, costN).add(Aspect.EARTH, costN), true, false))
					{
						return false;
					}
				}
				else
				{
					ls = 0;
				}
			}

			if(ls == 0)
				return false;

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
		MovingObjectPosition target = this.getMovingObjectPositionFromPlayer(world, player, true);
		if(target == null) return null;

		if(target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
		{
			x = target.blockX;
			y = target.blockY;
			z = target.blockZ;

			Block block1 = player.worldObj.getBlock(x, y, z);
			if(block1 == null) return null;

			int b1damage = block1.getDamageValue(player.worldObj, x, y, z);
			
			if(stack != null && stack.getItem() instanceof ItemWandCasting)
			{
				float hitX = (float) (target.hitVec.xCoord - x);
				float hitY = (float) (target.hitVec.yCoord - y);
				float hitZ = (float) (target.hitVec.zCoord - z);

				hitX = Math.abs(hitX);
				hitY = Math.abs(hitY);
				hitZ = Math.abs(hitZ);

				ForgeDirection face = ForgeDirection.getOrientation(target.sideHit);
				ItemWandCasting wand = ((ItemWandCasting) stack.getItem());
				ItemStack stackFocus = wand.getFocusItem(stack);
				ArrayList blocks = null;
				int size = FocusBuildHelper.getSize(stackFocus);
				
				switch(FocusBuildHelper.getShape(stackFocus))
				{
				case CUBE:
					x += face.offsetX * size;
					y += face.offsetY * size;
					z += face.offsetZ * size;
					blocks = (ArrayList) WorldUtils.plot3DCubeArea(player, player.worldObj, x, y, z, target.sideHit, hitX, hitY, hitZ, size);
					break;

				case PLANE:
					x += face.offsetX;
					y += face.offsetY;
					z += face.offsetZ;
					blocks = (ArrayList) WorldUtils.plot2DPlane(player, player.worldObj, x, y, z, target.sideHit, hitX, hitY, hitZ, size);
					break;

				case PLANE_EXTEND:
					x += face.offsetX*(size+1)/2;
					y += face.offsetY*(size+1)/2;
					z += face.offsetZ*(size+1)/2;
					blocks = (ArrayList) WorldUtils.plot2DPlaneExtension(player, player.worldObj, x, y, z, target.sideHit, hitX, hitY, hitZ, size);
					break;

				case SPHERE:
					x += face.offsetX * size;
					y += face.offsetY * size;
					z += face.offsetZ * size;
					blocks = (ArrayList) WorldUtils.plot3DCubeArea(player, player.worldObj, x, y, z, target.sideHit, hitX, hitY, hitZ, size);
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
