package trinarybrain.magia.naturalis.common.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import trinarybrain.magia.naturalis.common.MagiaNaturalis;
import trinarybrain.magia.naturalis.common.tile.TileGeoMorpher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
}
