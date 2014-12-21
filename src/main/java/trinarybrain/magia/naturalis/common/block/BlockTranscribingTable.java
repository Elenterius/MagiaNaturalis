package trinarybrain.magia.naturalis.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.lib.utils.InventoryUtils;
import thaumcraft.common.tiles.TileDeconstructionTable;
import trinarybrain.magia.naturalis.client.util.RenderUtil;
import trinarybrain.magia.naturalis.common.MagiaNaturalis;
import trinarybrain.magia.naturalis.common.tile.TileTranscribingTable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTranscribingTable extends BlockContainer
{
	protected BlockTranscribingTable()
	{
		super(Material.wood);
		this.setHardness(2.5F);
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
		return new TileTranscribingTable();
	}

	public boolean isOpaqueCube() {return false;}
	public boolean renderAsNormalBlock() {return false;}

	public int getRenderType()
	{
		return RenderUtil.RenderID;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{
		if(rand.nextInt(3) == 0)
		{
			TileEntity tile = world.getTileEntity(x , y, z);
			if(tile != null && tile instanceof TileTranscribingTable && ((IInventory)tile).getStackInSlot(0) != null)
			{
				int xOffset = rand.nextInt(2) - rand.nextInt(2);
				int zOffset = rand.nextInt(2) - rand.nextInt(2);
				xOffset += xOffset; zOffset += zOffset;

				if(xOffset != 0 || zOffset != 0)
				{
					tile = world.getTileEntity(x + xOffset, y, z + zOffset);
					if(tile != null && tile instanceof TileDeconstructionTable && world.getBlock(x + xOffset, y, z + zOffset) == ConfigBlocks.blockTable)
					{
						if(((TileDeconstructionTable)tile).aspect != null)
							world.spawnParticle("enchantmenttable", x + 0.5D, y + 2.0D, z + 0.5D, xOffset, -1, zOffset);
					}
				}
			}
		}
	}

	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		InventoryUtils.dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, block, meta);
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f0, float f1, float f3)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if ((tileEntity instanceof TileTranscribingTable))
		{
			player.openGui(MagiaNaturalis.instance, 1, world, x, y, z);
			return true;
		}

		return false;
	}
}
