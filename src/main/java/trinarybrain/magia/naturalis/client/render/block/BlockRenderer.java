package trinarybrain.magia.naturalis.client.render.block;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import thaumcraft.common.blocks.BlockJar;
import trinarybrain.magia.naturalis.client.util.RenderUtil;
import trinarybrain.magia.naturalis.common.block.BlockArcaneChest;
import trinarybrain.magia.naturalis.common.block.BlockBanner;
import trinarybrain.magia.naturalis.common.block.BlockJarPrison;
import trinarybrain.magia.naturalis.common.block.BlockTranscribingTable;
import trinarybrain.magia.naturalis.common.tile.TileArcaneChest;
import trinarybrain.magia.naturalis.common.tile.TileBannerCustom;
import trinarybrain.magia.naturalis.common.tile.TileJarPrison;
import trinarybrain.magia.naturalis.common.tile.TileTranscribingTable;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockRenderer implements ISimpleBlockRenderingHandler
{
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		GL11.glPushMatrix();
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		if(block instanceof BlockTranscribingTable)
		{
			TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileTranscribingTable(), 0.0D, 0.0D, 0.0D, 0.0F);
		}
		else if(block instanceof BlockArcaneChest)
		{
			TileArcaneChest tile = new TileArcaneChest();
			tile.setChestType((byte) metadata);
			TileEntityRendererDispatcher.instance.renderTileEntityAt(tile, 0.0D, 0.0D, 0.0D, 0.0F);
		}
		else if(block instanceof BlockBanner)
		{
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(-1.0F, -0.5F, 0.0F);
			TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileBannerCustom(), 0.0D, 0.0D, 0.0D, 0.0F);
		}

		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return RenderUtil.RenderID;
	}

}
