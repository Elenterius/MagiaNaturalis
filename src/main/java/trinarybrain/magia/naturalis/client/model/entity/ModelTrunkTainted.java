package trinarybrain.magia.naturalis.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import trinarybrain.magia.naturalis.common.entity.EntityEvilTrunk;

public class ModelTrunkTainted extends ModelBase
{
	public ModelRenderer chestSkull;
	public ModelRenderer chestJaw;
	public ModelRenderer chestTooth1;
	public ModelRenderer chestToothTop1;
	public ModelRenderer chestTooth2;
	public ModelRenderer chestToothTop2;
	public ModelRenderer chestTooth3;
	public ModelRenderer chestTooth4;
	public ModelRenderer chestHorn1;
	public ModelRenderer chestHorn2;
	public ModelRenderer chestHornTop;

	public ModelTrunkTainted()
	{
		this.chestSkull = new ModelRenderer(this, 0, 19).setTextureSize(64, 64);
		this.chestSkull.addBox(0F, -10F, -14F, 14, 10, 14);
		this.chestSkull.setRotationPoint(1F, 11F, 15F);

		this.chestJaw = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
		this.chestJaw.addBox(0F, 0F, -14F, 14, 5, 14);
		this.chestJaw.setRotationPoint(1F, 11F, 15F);

		this.chestTooth1 = new ModelRenderer(this, 7, 0).setTextureSize(64, 64);
		this.chestTooth1.addBox(-1F, -1F, -14.5F, 2, 3, 1);
		this.chestTooth1.setRotationPoint(13F, 11F, 15F);

		this.chestToothTop1 = new ModelRenderer(this, 9, 0).setTextureSize(64, 64);
		this.chestToothTop1.addBox(0F, -2F, -14.5F, 1, 1, 1);
		this.chestToothTop1.setRotationPoint(13F, 11F, 15F);

		this.chestTooth2 = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
		this.chestTooth2.addBox(-1F, -1F, -14.5F, 2, 3, 1);
		this.chestTooth2.setRotationPoint(3F, 11F, 15F);

		this.chestToothTop2 = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
		this.chestToothTop2.addBox(-1F, -2F, -14.5F, 1, 1, 1);
		this.chestToothTop2.setRotationPoint(3F, 11F, 15F);

		this.chestTooth3 = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
		this.chestTooth3.addBox(-0.7F, -1F, -14.5F, 2, 3, 1);
		this.chestTooth3.setRotationPoint(6F, 11F, 15F);

		this.chestTooth4 = new ModelRenderer(this, 7, 0).setTextureSize(64, 64);
		this.chestTooth4.addBox(-1.25F, -1F, -14.5F, 2, 3, 1);
		this.chestTooth4.setRotationPoint(10F, 11F, 15F);

		this.chestHorn1 = new ModelRenderer(this, 0, 5).setTextureSize(64, 64);
		this.chestHorn1.addBox(6F, -12F, -13F, 2, 2, 2);
		this.chestHorn1.setRotationPoint(1F, 11F, 15F);

		this.chestHorn2 = new ModelRenderer(this, 0, 5).setTextureSize(64, 64);
		this.chestHorn2.addBox(6F, -14F, -13.4F, 2, 2, 2);
		this.chestHorn2.setRotationPoint(1F, 11F, 15F);

		this.chestHornTop = new ModelRenderer(this, 3, 6).setTextureSize(64, 64);
		this.chestHornTop.addBox(6.5F, -16F, -13.2F, 1, 2, 1);
		this.chestHornTop.setRotationPoint(1F, 11F, 15F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.5F, 0.5F, -0.5F);

		if(entity != null)
		{
			float f11 = 1.0F - ((EntityEvilTrunk)entity).skullrot;
			f11 = 1.0F - f11 * f11 * f11;
			this.chestSkull.rotateAngleX = (-(f11 * 3.141593F / 2.0F));
			this.chestHornTop.rotateAngleX = this.chestHorn2.rotateAngleX = this.chestHorn1.rotateAngleX = this.chestSkull.rotateAngleX;
		}

		float f6 = 0.0625F;
		chestSkull.render(f6);
		chestJaw.render(f6);
		chestTooth1.render(f6);
		chestToothTop1.render(f6);
		chestTooth2.render(f6);
		chestToothTop2.render(f6);
		chestTooth3.render(f6);
		chestTooth4.render(f6);
		chestHorn1.render(f6);
		chestHorn2.render(f6);
		chestHornTop.render(f6);
		GL11.glPopMatrix();
	}
}