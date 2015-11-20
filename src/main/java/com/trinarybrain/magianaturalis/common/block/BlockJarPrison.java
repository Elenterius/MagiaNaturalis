package com.trinarybrain.magianaturalis.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.common.blocks.CustomStepSound;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.tiles.TileDeconstructionTable;
import com.trinarybrain.magianaturalis.client.util.RenderUtil;
import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.block.item.BlockJarPrisonItem;
import com.trinarybrain.magianaturalis.common.core.Log;
import com.trinarybrain.magianaturalis.common.item.ItemsMN;
import com.trinarybrain.magianaturalis.common.tile.TileArcaneChest;
import com.trinarybrain.magianaturalis.common.tile.TileJarPrison;
import com.trinarybrain.magianaturalis.common.tile.TileTranscribingTable;
import com.trinarybrain.magianaturalis.common.util.NBTUtil;
import com.trinarybrain.magianaturalis.common.util.ResourceUtil;
import com.trinarybrain.magianaturalis.common.util.access.UserAccess;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockJarPrison extends BlockContainer
{
	public IIcon iconJarSide;
	public IIcon iconJarTop;
	public IIcon iconJarBottom;

	public BlockJarPrison()
	{
		super(Material.glass);
	    this.setHardness(0.3F);
	    this.setStepSound(new CustomStepSound("jar", 1.0F, 1.0F));
	    this.setLightLevel(0.66F);
	    this.setCreativeTab(MagiaNaturalis.creativeTab);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		this.iconJarSide = ir.registerIcon(ResourceUtil.PREFIX + "jar_prison_side");
		this.iconJarTop = ir.registerIcon("thaumcraft:jar_top");
		this.iconJarBottom = ir.registerIcon("thaumcraft:jar_bottom");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return side == 1 ? this.iconJarTop : side == 0 ? this.iconJarBottom : this.iconJarSide;
	}

	public boolean isOpaqueCube() {return false;}
	public boolean renderAsNormalBlock() {return false;}

	public int getRenderBlockPass()
	{
		return 1;
	}

	public int getRenderType()
	{
		return RenderUtil.RenderID2;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{
		if(rand.nextInt(4) == 0)
		{
			TileEntity tile = world.getTileEntity(x , y, z);
			if(tile != null && tile instanceof TileJarPrison)
			{
				MagiaNaturalis.proxyTC4.blockSparkle(world, x, y, z, 0xFFD700, 1);
			}
		}
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		TileJarPrison jar = (TileJarPrison) world.getTileEntity(x, y, z);
		if(jar != null)
		{
			EntityPlayer player = (EntityPlayer)entity;

			NBTTagCompound data = NBTUtil.openNbtData(stack);
			if(data.hasKey("entity"))
				jar.setEntityData(data);
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null && tile instanceof TileJarPrison)
		{
			TileJarPrison jarPrison = (TileJarPrison) tile;
			this.nbtCacheEntity = jarPrison.getEntityDataPrimitive();
		}
		super.breakBlock(world, x, y, z, block, meta);
	}

	private NBTTagCompound nbtCacheEntity;

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		if(this.nbtCacheEntity != null)
		{
			ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
			ItemStack stack = new ItemStack(this, 1, 0);
			stack.stackTagCompound = this.nbtCacheEntity;
			drops.add(stack);
			this.nbtCacheEntity = null;
			return drops;
		}
		return super.getDrops(world, x, y, z, metadata, fortune);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileJarPrison();
	}

	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.75F, 0.8125F);
		super.setBlockBoundsBasedOnState(world, x, y, z);
	}

	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aaBB, List list, Entity entity)
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(world, x, y, z, aaBB, list, entity);
	}
}
