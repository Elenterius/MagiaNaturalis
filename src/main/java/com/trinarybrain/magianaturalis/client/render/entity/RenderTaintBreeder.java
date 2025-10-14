package com.trinarybrain.magianaturalis.client.render.entity;

import com.trinarybrain.magianaturalis.client.model.entity.ModelTaintBreeder;
import com.trinarybrain.magianaturalis.common.MagiaNaturalis;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderTaintBreeder extends RenderLiving {

    private static final ResourceLocation TEXTURE = MagiaNaturalis.rl("textures/models/taint_breeder.png");

    public RenderTaintBreeder() {
        super(new ModelTaintBreeder(), 1.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return TEXTURE;
    }

}
