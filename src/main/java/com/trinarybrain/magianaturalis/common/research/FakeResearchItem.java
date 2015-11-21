package com.trinarybrain.magianaturalis.common.research;

import java.util.Arrays;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;

public class FakeResearchItem extends ResearchItem
{
	public ResearchItem origResearch;

	public FakeResearchItem(String name, String cat, String origin, String originCategory, int x, int y, ResourceLocation icon)
	{
		super(name, cat, new AspectList(), x, y, 1, icon);
		this.origResearch = ResearchCategories.getResearch(origin);
		this.addSiblingToOriginal();
		this.setStub();
		this.setHidden();

	}

	public FakeResearchItem(String name, String cat, String origin, String originCategory, int x, int y, ItemStack icon)
	{
		super(name, cat, new AspectList(), x, y, 1, icon);
		this.origResearch = ResearchCategories.getResearch(origin);
		this.addSiblingToOriginal();
		this.setStub();
		this.setHidden();
	}

	protected void addSiblingToOriginal()
	{
		if(this.origResearch.siblings == null) this.origResearch.setSiblings(new String[]{this.key});
		else
		{
			String[] newSiblings = Arrays.copyOf(this.origResearch.siblings, this.origResearch.siblings.length + 1);
			newSiblings[this.origResearch.siblings.length] = this.key;
			this.origResearch.setSiblings(newSiblings);
		}
		if(this.origResearch.isSecondary())	this.setSecondary();
	}

	public ResearchPage[] getPages()
	{
		return this.origResearch.getPages();
	}

	public String getName()
	{
		return this.origResearch.getName();
	}

	public String getText()
	{
		return this.origResearch.getText();
	}

	public boolean isStub()
	{
		return true;
	}

	public boolean isHidden()
	{
		return true;
	}

	public int getComplexity()
	{
		return 1;
	}
}

