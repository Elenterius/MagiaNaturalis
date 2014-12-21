package trinarybrain.magia.naturalis.common.block;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import thaumcraft.common.blocks.CustomStepSound;
import trinarybrain.magia.naturalis.client.util.RenderUtil;
import trinarybrain.magia.naturalis.common.MagiaNaturalis;
import trinarybrain.magia.naturalis.common.tile.TileArcaneChest;
import trinarybrain.magia.naturalis.common.tile.TileJarPrison;
import trinarybrain.magia.naturalis.common.util.NBTUtil;
import trinarybrain.magia.naturalis.common.util.access.UserAccess;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
		this.iconJarSide = ir.registerIcon("thaumcraft:jar_side");
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
