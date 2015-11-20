package com.trinarybrain.magianaturalis.common.block;

import com.trinarybrain.magianaturalis.client.util.RenderUtil;
import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.tile.TileGeoMorpher;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGeoMorpher extends BlockContainer
{
	protected BlockGeoMorpher()
	{
		super(Material.wood);
		this.setHardness(2.5F);
		this.setResistance(10.0F);
		this.setStepSound(this.soundTypeWood);
		this.setCreativeTab(MagiaNaturalis.creativeTab);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ir)
	{
		this.blockIcon = ir.registerIcon("thaumcraft:woodplain");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i)
	{
		return new TileGeoMorpher();
	}

	public boolean isOpaqueCube() {return false;}
	public boolean renderAsNormalBlock() {return false;}

	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		this.setBlockBounds(0.25F, 0.25F, 0.25F, 0.750F, 0.75F, 0.75F);
//		this.setBlockBounds(0.75F, 0.25F, 0.75F, 0.750F, 0.75F, 0.75F);
	}

	public int getRenderType()
	{
		return RenderUtil.RenderID;
	}
}
