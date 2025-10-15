package com.github.elenterius.magianaturalis.client.render.block;

import com.github.elenterius.magianaturalis.block.BlockArcaneChest;
import com.github.elenterius.magianaturalis.block.BlockBanner;
import com.github.elenterius.magianaturalis.block.BlockGeoMorpher;
import com.github.elenterius.magianaturalis.block.BlockTranscribingTable;
import com.github.elenterius.magianaturalis.client.render.RenderUtil;
import com.github.elenterius.magianaturalis.tile.TileArcaneChest;
import com.github.elenterius.magianaturalis.tile.TileBannerCustom;
import com.github.elenterius.magianaturalis.tile.TileGeoMorpher;
import com.github.elenterius.magianaturalis.tile.TileTranscribingTable;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class BlockEntityRenderer implements ISimpleBlockRenderingHandler {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        if (block instanceof BlockTranscribingTable) {
            TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileTranscribingTable(), 0.0D, 0.0D, 0.0D, 0.0F);
        }
        else if (block instanceof BlockArcaneChest) {
            TileArcaneChest tile = new TileArcaneChest();
            tile.setChestType((byte) metadata);
            TileEntityRendererDispatcher.instance.renderTileEntityAt(tile, 0.0D, 0.0D, 0.0D, 0.0F);
        }
        else if (block instanceof BlockBanner) {
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-1.0F, -0.5F, 0.0F);
            TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileBannerCustom(), 0.0D, 0.0D, 0.0D, 0.0F);
        }
        else if (block instanceof BlockGeoMorpher) {
            TileGeoMorpher tile = new TileGeoMorpher();
            tile.idle = true;
            TileEntityRendererDispatcher.instance.renderTileEntityAt(tile, 0.0D, 0.0D, 0.0D, 0.0F);
        }

        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return RenderUtil.RenderID;
    }

}
