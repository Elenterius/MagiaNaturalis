package trinarybrain.magia.naturalis.client.render.tile;

import java.awt.Color;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import thaumcraft.client.renderers.models.ModelCrystal;
import thaumcraft.common.blocks.BlockCustomOreItem;

public class TileFloatingCrystalRenderer extends TileEntitySpecialRenderer
{
	private ModelCrystal model;
	Color color;
	private ResourceLocation rl = new ResourceLocation("thaumcraft", "textures/models/crystal.png");

	public TileFloatingCrystalRenderer()
	{
		this.color = new Color(0.0F, 0.5F, 0.5F);
		this.model = new ModelCrystal();
	}

	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float tick)
	{
		 this.drawCrystalMono((float)x, (float)y, (float)z, tick, color.getRGB(), new Random(entity.xCoord * entity.yCoord + entity.zCoord));
	}

	private void drawCrystalMono(float x, float y, float z, float tick, int color, Random rand)
	{
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		float bob = MathHelper.sin(player.ticksExisted / 14.0F) * 0.066F;
		float shade = MathHelper.sin((player.ticksExisted + rand.nextInt(10)) / (10.0F + rand.nextFloat())) * 0.05F + 0.95F;

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_NORMALIZE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		this.bindTexture(this.rl);
		GL11.glTranslatef(x + 0.5F, y - 0.07F + bob, z + 0.5F);
		GL11.glRotatef(tick + player.ticksExisted % 360, 0.0F, 1.0F, 0.0F);

		int shadeOffset = (int) (210F * shade);
		int lightX = shadeOffset % 65536;
		int lightY = shadeOffset / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightX / 1.0F, lightY / 1.0F);
		
		Color c = new Color(color);
		GL11.glColor4f(c.getRed() / 255F, c.getGreen() / 255F, c.getBlue() / 255F, 1F);

		GL11.glScalef(0.35F + rand.nextFloat() * 0.05F, 0.5F, 0.35F + rand.nextFloat() * 0.05F);

		this.model.render();
		GL11.glScalef(1.0F, 1.0F, 1.0F);

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}
}
