package com.trinarybrain.magianaturalis.common.research;

import java.util.Arrays;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

public class FakeResearchItem extends ResearchItem {
    public ResearchItem origResearch;

    public FakeResearchItem(String name, String cat, String origin, String originCategory, int x, int y, ResourceLocation icon) {
        super(name, cat, new AspectList(), x, y, 1, icon);
        origResearch = ResearchCategories.getResearch(origin);
        addSiblingToOriginal();
        setStub();
        setHidden();
    }

    public FakeResearchItem(String name, String cat, String origin, String originCategory, int x, int y, ItemStack icon) {
        super(name, cat, new AspectList(), x, y, 1, icon);
        origResearch = ResearchCategories.getResearch(origin);
        addSiblingToOriginal();
        setStub();
        setHidden();
    }

    protected void addSiblingToOriginal() {
        if (origResearch.siblings == null) origResearch.setSiblings(key);
        else {
            String[] newSiblings = Arrays.copyOf(origResearch.siblings, origResearch.siblings.length + 1);
            newSiblings[origResearch.siblings.length] = key;
            origResearch.setSiblings(newSiblings);
        }
        if (origResearch.isSecondary()) setSecondary();
    }

    public ResearchPage[] getPages() {
        return origResearch.getPages();
    }

    public String getName() {
        return origResearch.getName();
    }

    public String getText() {
        return origResearch.getText();
    }

    public boolean isStub() {
        return true;
    }

    public boolean isHidden() {
        return true;
    }

    public int getComplexity() {
        return 1;
    }

}

