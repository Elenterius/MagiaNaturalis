package trinarybrain.magia.naturalis.client.render.tile;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import thaumcraft.client.renderers.models.ModelJar;
import trinarybrain.magia.naturalis.common.tile.TileArcaneChest;
import trinarybrain.magia.naturalis.common.tile.TileJarPrison;

public class TileJarPrisonRenderer extends TileEntitySpecialRenderer
{
	private final ModelJar jarModel = new ModelJar();

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f)
	{
		this.renderTileEntityAt((TileJarPrison)tile, x, y, z, f);
	}
	
	public void renderTileEntityAt(TileJarPrison tile, double x, double y, double z, float f)
	{
		Entity entity = tile.getCachedEntity();
		if(entity != null)
		{
			GL11.glPushMatrix();
            GL11.glTranslated(x, y, z);
            GL11.glTranslatef(0.5F, 0.1F, 0.5F);
            
            float f1 = 0.21875F;
            GL11.glScalef(f1, f1, f1);
            
            RenderManager.instance.renderEntityWithPosYaw(entity, 0D, 0D, 0D, 0F, 0F);
            GL11.glPopMatrix();
		}
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
//		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		
		GL11.glTranslatef((float)x + 0.5F, (float)y + 0.01F, (float)z + 0.5F);
		GL11.glRotatef(180.0F, 1.0F, 0.0F, 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
//		this.bindTexture(tile.getTexture());
//		jarModel.renderAll();
		
//		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
}
