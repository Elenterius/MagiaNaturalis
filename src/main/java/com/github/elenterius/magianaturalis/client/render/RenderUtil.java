package com.github.elenterius.magianaturalis.client.render;

import com.github.elenterius.magianaturalis.MagiaNaturalis;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public final class RenderUtil {

    public static int RenderID = RenderingRegistry.getNextAvailableRenderId();
    public static int RenderID2 = RenderingRegistry.getNextAvailableRenderId();

    public static void bindTexture(ResourceLocation resource) {
        Minecraft.getMinecraft().renderEngine.bindTexture(resource);
    }

    public static float getTicks() {
        return Minecraft.getMinecraft().renderViewEntity.ticksExisted;
    }

    public static EntityPlayer getRenderViewPlayer() {
        return (EntityPlayer) Minecraft.getMinecraft().renderViewEntity;
    }

    public static void drawTextureQuad(ResourceLocation rl, float w, float h) {
        Tessellator tessellator = Tessellator.instance;
        Minecraft.getMinecraft().renderEngine.bindTexture(rl);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0, 0, 0, 0, 0);
        tessellator.addVertexWithUV(0, h, 0, 0, 1);
        tessellator.addVertexWithUV(w, h, 0, 1, 1);
        tessellator.addVertexWithUV(w, 0, 0, 1, 0);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public static void drawItemStack(RenderItem itemRender, FontRenderer fontRenderer, ItemStack stack, int x, int y) {
        itemRender.zLevel = 200.0F;
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glEnable(GL11.GL_LIGHTING);
        try {
            itemRender.renderItemAndEffectIntoGUI(fontRenderer, Minecraft.getMinecraft().getTextureManager(), stack, x, y);
        }
        catch (Exception e) {
            MagiaNaturalis.LOGGER.catching(e);
        }
        GL11.glDisable(GL11.GL_LIGHTING);
        //      GL11.glDisable(GL12.GL_RESCALE_NORMAL);
    }

}
