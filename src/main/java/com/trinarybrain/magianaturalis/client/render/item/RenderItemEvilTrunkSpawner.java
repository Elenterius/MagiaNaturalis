package com.trinarybrain.magianaturalis.client.render.item;

import com.trinarybrain.magianaturalis.client.render.RenderUtil;
import com.trinarybrain.magianaturalis.client.render.entity.trunk.*;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemEvilTrunkSpawner;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class RenderItemEvilTrunkSpawner implements IItemRenderer {

    public CorruptedTrunkModel modelTC = new CorruptedTrunkModel();
    public SinisterTrunkModel modelTS = new SinisterTrunkModel();
    public DemonicTrunkModel modelTD = new DemonicTrunkModel();
    public TaintedTrunkModel modelTT = new TaintedTrunkModel();

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        GL11.glScalef(1.0F, -1.0F, -1.0F);

        if ((type == IItemRenderer.ItemRenderType.EQUIPPED) || (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON)) {
            GL11.glTranslatef(-0.25F, -0.5F, -0.25F);
            if ((type == IItemRenderer.ItemRenderType.EQUIPPED) && (type != IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON)) {
                GL11.glTranslatef(1.0F, 0.0F, 0.0F);
            }
            else {
                float angle = 90.0F;
                GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
                GL11.glTranslatef(0.25F, -0.25F, 0.25F);
            }
        }

        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        GL11.glTranslatef(0.5F, -0.5F, 0.5F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        if (item.getItem() instanceof ItemEvilTrunkSpawner) {
            switch (item.getItemDamage()) {
                case 0:
                    RenderUtil.bindTexture(EvilTrunkRenderer.CORRUPTED_TEXTURE);
                    this.modelTC.render(null, 0, 0, 0, 0, 0, 0);
                    break;

                case 1:
                    RenderUtil.bindTexture(EvilTrunkRenderer.SINISTER_TEXTURE);
                    this.modelTS.render(null, 0, 0, 3, 0, 0, 0);
                    break;

                case 2:
                    RenderUtil.bindTexture(EvilTrunkRenderer.DEMONIC_TEXTURE);
                    this.modelTD.render(null, 0, 0, 0, 0, 0, 0);
                    break;

                case 3:
                    RenderUtil.bindTexture(EvilTrunkRenderer.TAINTED_TEXTURE);
                    this.modelTT.render(null, 0, 0, 0, 0, 0, 0);
                    break;
            }
        }
        GL11.glPopMatrix();
    }

}
