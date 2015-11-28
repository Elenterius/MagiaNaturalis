package com.trinarybrain.magianaturalis.client.render.tile;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.trinarybrain.magianaturalis.client.util.RenderUtil;
import com.trinarybrain.magianaturalis.common.Reference;
import com.trinarybrain.magianaturalis.common.tile.TileArcaneChest;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileArcaneChestRenderer extends TileEntitySpecialRenderer
{
	private static final ResourceLocation rl_gw = new ResourceLocation(Reference.ID, "textures/models/" + "chest_greatwood.png");
	private static final ResourceLocation rl_sw = new ResourceLocation(Reference.ID, "textures/models/" + "chest_silverwood.png");
	private final ModelChest chestModel = new ModelChest();

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f)
	{
		this.renderTileEntityAt((TileArcaneChest)tile, x, y, z, f);
	}

	public void renderTileEntityAt(TileArcaneChest tile, double x, double y, double z, float f)
	{
		int meta = 0;
		if(!tile.hasWorldObj()) {meta = 0;}
		else {meta = tile.getBlockMetadata();}

		if(tile.getChestType() == 1)
			RenderUtil.bindTexture(rl_gw);
		else if(tile.getChestType() == 2)
			RenderUtil.bindTexture(rl_sw);

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);

		short angle = 0;
		if(meta == 2) {angle = 180;}
		else if(meta == 3) {angle = 0;}
		else if(meta == 4) {angle = 90;}
		else if(meta == 5) {angle = -90;}

		GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		float anglef = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * f;

		anglef = 1.0F - anglef;
		anglef = 1.0F - anglef * anglef * anglef;
		this.chestModel.chestLid.rotateAngleX = (-(anglef * 3.141593F / 2.0F));
		this.chestModel.renderAll();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
