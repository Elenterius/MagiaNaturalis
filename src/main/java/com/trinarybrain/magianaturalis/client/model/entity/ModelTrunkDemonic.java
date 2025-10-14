package com.trinarybrain.magianaturalis.client.model.entity;

import org.lwjgl.opengl.GL11;

import com.trinarybrain.magianaturalis.common.entity.EntityEvilTrunk;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelTrunkDemonic extends ModelBase {

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
    public ModelRenderer chestHorn3;
    public ModelRenderer chestHorn4;
    public ModelRenderer chestHornTop1;
    public ModelRenderer chestHornTop2;
    public ModelRenderer chestRightWing;
    public ModelRenderer chestOuterRightWing;
    public ModelRenderer chestLeftWing;
    public ModelRenderer chestOuterLeftWing;

    public ModelTrunkDemonic() {
        chestSkull = new ModelRenderer(this, 0, 19).setTextureSize(128, 64);
        chestSkull.addBox(0F, -10F, -14F, 14, 10, 14);
        chestSkull.setRotationPoint(1F, 11F, 15F);

        chestJaw = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);
        chestJaw.addBox(0F, 0F, -14F, 14, 5, 14);
        chestJaw.setRotationPoint(1F, 11F, 15F);

        chestTooth1 = new ModelRenderer(this, 7, 0).setTextureSize(128, 64);
        chestTooth1.addBox(-1F, -1F, -14.5F, 2, 3, 1);
        chestTooth1.setRotationPoint(13F, 11F, 15F);

        chestToothTop1 = new ModelRenderer(this, 9, 0).setTextureSize(128, 64);
        chestToothTop1.addBox(0F, -2F, -14.5F, 1, 1, 1);
        chestToothTop1.setRotationPoint(13F, 11F, 15F);

        chestTooth2 = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);
        chestTooth2.addBox(-1F, -1F, -14.5F, 2, 3, 1);
        chestTooth2.setRotationPoint(3F, 11F, 15F);

        chestToothTop2 = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);
        chestToothTop2.addBox(-1F, -2F, -14.5F, 1, 1, 1);
        chestToothTop2.setRotationPoint(3F, 11F, 15F);

        chestTooth3 = new ModelRenderer(this, 0, 0).setTextureSize(128, 64);
        chestTooth3.addBox(-0.7F, -1F, -14.5F, 2, 3, 1);
        chestTooth3.setRotationPoint(6F, 11F, 15F);

        chestTooth4 = new ModelRenderer(this, 7, 0).setTextureSize(128, 64);
        chestTooth4.addBox(-1.25F, -1F, -14.5F, 2, 3, 1);
        chestTooth4.setRotationPoint(10F, 11F, 15F);

        chestHorn1 = new ModelRenderer(this, 0, 5).setTextureSize(128, 64);
        chestHorn1.addBox(1F, -12F, -13F, 2, 2, 2);
        chestHorn1.setRotationPoint(1F, 11F, 15F);

        chestHorn2 = new ModelRenderer(this, 0, 5).setTextureSize(128, 64);
        chestHorn2.addBox(2F, -14F, -13F, 2, 2, 2);
        chestHorn2.setRotationPoint(1F, 11F, 15F);

        chestHorn3 = new ModelRenderer(this, 0, 5).setTextureSize(128, 64);
        chestHorn3.addBox(11F, -12F, -13F, 2, 2, 2);
        chestHorn3.setRotationPoint(1F, 11F, 15F);

        chestHorn4 = new ModelRenderer(this, 0, 5).setTextureSize(128, 64);
        chestHorn4.addBox(10F, -14F, -13F, 2, 2, 2);
        chestHorn4.setRotationPoint(1F, 11F, 15F);

        chestHornTop1 = new ModelRenderer(this, 3, 6).setTextureSize(128, 64);
        chestHornTop1.addBox(10F, -15F, -12F, 1, 1, 1);
        chestHornTop1.setRotationPoint(1F, 11F, 15F);

        chestHornTop2 = new ModelRenderer(this, 3, 6).setTextureSize(128, 64);
        chestHornTop2.addBox(3F, -15F, -12F, 1, 1, 1);
        chestHornTop2.setRotationPoint(1F, 11F, 15F);

        chestRightWing = new ModelRenderer(this, 56, 0).setTextureSize(128, 64);
        chestRightWing.addBox(0F, -12F, -0.5F, 10, 16, 1);
        chestRightWing.setRotationPoint(15F, 11F, 9F);

        chestOuterRightWing = new ModelRenderer(this, 56, 17).setTextureSize(128, 64);
        chestOuterRightWing.addBox(0F, -10F, -0.5F, 8, 13, 1);
        chestOuterRightWing.setRotationPoint(25F, 11F, 9F);

        chestLeftWing = new ModelRenderer(this, 56, 0).setTextureSize(128, 64);
        chestLeftWing.mirror = true;
        chestLeftWing.addBox(-10F, -12F, -0.5F, 10, 16, 1);
        chestLeftWing.setRotationPoint(1F, 11F, 9F);

        chestOuterLeftWing = new ModelRenderer(this, 56, 17).setTextureSize(128, 64);
        chestOuterLeftWing.mirror = true;
        chestOuterLeftWing.addBox(-8F, -10F, -0.5F, 8, 13, 1);
        chestOuterLeftWing.setRotationPoint(-9F, 11F, 9F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.5F, 0.5F, -0.5F);

        if (entity != null) {
            float f7 = 1.0F - ((EntityEvilTrunk) entity).skullrot;
            f7 = 1.0F - f7 * f7 * f7;
            f7 = (-(f7 * 3.141593F / 2.0F));

            this.chestJaw.rotateAngleX = f7;
            this.chestTooth1.rotateAngleX = f7;
            this.chestTooth2.rotateAngleX = f7;
            this.chestTooth3.rotateAngleX = f7;
            this.chestTooth4.rotateAngleX = f7;
            this.chestToothTop1.rotateAngleX = f7;
            this.chestToothTop2.rotateAngleX = f7;
        }

        this.chestRightWing.rotateAngleY = MathHelper.cos(f2 * 0.5F) * (float) Math.PI * 0.25F;
        this.chestLeftWing.rotateAngleY = -this.chestRightWing.rotateAngleY;
        double X = 10 * Math.cos(this.chestRightWing.rotateAngleY);
        double Z = 10 * Math.sin(this.chestRightWing.rotateAngleY);
        this.chestOuterRightWing.setRotationPoint(15F + (float) X, 11F, (float) (9F - Z));
        this.chestOuterLeftWing.setRotationPoint((float) (1F - X), 11F, (float) (9F - Z));
        this.chestOuterRightWing.rotateAngleY = (float) this.chestRightWing.rotateAngleY * 1.9F;
        this.chestOuterLeftWing.rotateAngleY = (float) -this.chestRightWing.rotateAngleY * 1.9F;

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
        chestHorn3.render(f6);
        chestHorn4.render(f6);
        chestHornTop1.render(f6);
        chestHornTop2.render(f6);
        chestRightWing.render(f6);
        chestOuterRightWing.render(f6);
        chestLeftWing.render(f6);
        chestOuterLeftWing.render(f6);
        GL11.glPopMatrix();
    }
}
