package com.trinarybrain.magianaturalis.common.block;

import com.trinarybrain.magianaturalis.client.render.RenderUtil;
import com.trinarybrain.magianaturalis.common.tile.TileGeoMorpher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGeoMorpher extends BlockContainer {

    public BlockGeoMorpher() {
        super(Material.wood);
        setHardness(2.5F);
        setResistance(10.0F);
        setStepSound(soundTypeWood);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {
        blockIcon = ir.registerIcon("thaumcraft:woodplain");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileGeoMorpher();
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
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        setBlockBounds(0.25F, 0.25F, 0.25F, 0.750F, 0.75F, 0.75F);
        //setBlockBounds(0.75F, 0.25F, 0.75F, 0.750F, 0.75F, 0.75F);
    }

    @Override
    public int getRenderType() {
        return RenderUtil.RenderID;
    }

}
