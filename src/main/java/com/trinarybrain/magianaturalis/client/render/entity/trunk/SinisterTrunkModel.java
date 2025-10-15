package com.trinarybrain.magianaturalis.client.render.entity.trunk;

import com.trinarybrain.magianaturalis.common.entity.EntityEvilTrunk;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class SinisterTrunkModel extends ModelBase {

    public ModelRenderer chestSkull;
    public ModelRenderer chestJaw;
    public ModelRenderer chestTooth1;
    public ModelRenderer chestToothTop1;
    public ModelRenderer chestTooth2;
    public ModelRenderer chestToothTop2;
    public ModelRenderer chestTooth3;
    public ModelRenderer chestKTooth4;
    public ModelRenderer chestJar;
    public ModelRenderer chestBrain;

    public SinisterTrunkModel() {
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

        chestKTooth4 = new ModelRenderer(this, 7, 0).setTextureSize(128, 64);
        chestKTooth4.addBox(-1.25F, -1F, -14.5F, 2, 3, 1);
        chestKTooth4.setRotationPoint(10F, 11F, 15F);

        chestJar = new ModelRenderer(this, 0, 43).setTextureSize(128, 64);
        chestJar.addBox(1F, -14F, -13F, 12, 4, 12);
        chestJar.setRotationPoint(1F, 11F, 15F);

        chestBrain = new ModelRenderer(this, 48, 46).setTextureSize(128, 64);
        chestBrain.addBox(2F, -13F, -12F, 10, 3, 10);
        chestBrain.setRotationPoint(1F, 11F, 15F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.5F, 0.5F, -0.5F);

        if (entity != null) {
            float f7 = 1.0F - ((EntityEvilTrunk) entity).skullrot;
            f7 = 1.0F - f7 * f7 * f7;
            f7 = (-(f7 * 3.141593F / 2.0F));

            this.chestSkull.rotateAngleX = f7;
            this.chestBrain.rotateAngleX = f7;
            this.chestJar.rotateAngleX = f7;
        }

        float f6 = 0.0625F;
        chestSkull.render(f6);
        chestJaw.render(f6);
        chestTooth1.render(f6);
        chestToothTop1.render(f6);
        chestTooth2.render(f6);
        chestToothTop2.render(f6);
        chestTooth3.render(f6);
        chestKTooth4.render(f6);
        chestBrain.render(f6);

        GL11.glEnable(GL11.GL_NORMALIZE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        chestJar.render(f6);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }
}
