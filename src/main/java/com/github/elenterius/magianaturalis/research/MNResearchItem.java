package com.github.elenterius.magianaturalis.research;

import com.github.elenterius.magianaturalis.MagiaNaturalis;
import com.github.elenterius.magianaturalis.util.Platform;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchItem;

public class MNResearchItem extends ResearchItem {

    public MNResearchItem(String key, String category) {
        super(key, category);
    }

    public MNResearchItem(String key, int col, int row, int complex, ResourceLocation icon) {
        super(key, MagiaNaturalis.MOD_ID, new AspectList(), col, row, complex, icon);
    }

    public MNResearchItem(String key, int col, int row, int complex, ItemStack icon) {
        super(key, MagiaNaturalis.MOD_ID, new AspectList(), col, row, complex, icon);
    }

    public MNResearchItem(String key, AspectList tags, int col, int row, int complex, ResourceLocation icon) {
        super(key, MagiaNaturalis.MOD_ID, tags, col, row, complex, icon);
    }

    public MNResearchItem(String key, AspectList tags, int col, int row, int complex, ItemStack icon) {
        super(key, MagiaNaturalis.MOD_ID, tags, col, row, complex, icon);
    }

    @SideOnly(Side.CLIENT)
    public String getName() {
        return Platform.translate("mn.research_name." + key);
    }

    @SideOnly(Side.CLIENT)
    public String getText() {
        return Platform.translate("mn.research_text." + key);
    }

}
