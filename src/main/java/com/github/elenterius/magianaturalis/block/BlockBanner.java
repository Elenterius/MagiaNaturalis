package com.github.elenterius.magianaturalis.block;

import com.github.elenterius.magianaturalis.client.render.RenderUtil;
import com.github.elenterius.magianaturalis.tile.TileBannerCustom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBanner extends BlockContainer {

    public BlockBanner() {
        super(Material.wood);
        setHardness(2.5F);
        setResistance(10.0F);
        setStepSound(soundTypeWood);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        blockIcon = ir.registerIcon("thaumcraft:woodplain");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileBannerCustom();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return RenderUtil.RenderID;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileBannerCustom) {
            if (((TileBannerCustom) tile).getWall())
                switch (((TileBannerCustom) tile).getFacing()) {
                    case 0:
                        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 0.25F);
                        break;
                    case 8:
                        setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 2.0F, 1.0F);
                        break;
                    case 12:
                        setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 2.0F, 1.0F);
                        break;
                    case 4:
                        setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
                }
            else {
                setBlockBounds(0.33F, 0.0F, 0.33F, 0.66F, 2.0F, 0.66F);
            }
        }
    }

    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
        dropBlockAsItem(world, x, y, z, meta, 0);
    }

}
