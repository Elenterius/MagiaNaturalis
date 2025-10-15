package com.trinarybrain.magianaturalis.client.render.entity.man;

import com.trinarybrain.magianaturalis.MagiaNaturalis;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class TaintmanRenderer extends RenderLiving {

    private static final ResourceLocation TEXTURE = MagiaNaturalis.rl( "textures/models/taintman.png");

    public TaintmanRenderer() {
        super(new TaintmanModel(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return TEXTURE;
    }

}
