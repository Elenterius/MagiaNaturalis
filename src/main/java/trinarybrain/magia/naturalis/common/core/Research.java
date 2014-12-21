package trinarybrain.magia.naturalis.common.core;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigItems;
import trinarybrain.magia.naturalis.common.ProjectInfo;
import trinarybrain.magia.naturalis.common.block.BlocksMN;
import trinarybrain.magia.naturalis.common.util.ResourceUtil;

public class Research
{
	public static ResearchItem INTRO;
	public static ResearchItem SARKOLOGIA;
	public static ResearchItem CAPACITOR;
	public static ResearchItem KNOWLEDGEWELL;
	public static ResearchItem GEOCCULTISM;
	public static ResearchItem HERBALISM;
	public static ResearchItem MIXOLOGY;
	public static ResearchItem MINERALOGY;
	public static ResearchItem GAMBLING;

	public static void init()
	{
		initCategory();
		initEntry();
	}

	private static void initCategory()
	{
		ResearchCategories.registerCategory(ProjectInfo.ID, new ResourceLocation(ResourceUtil.DOMAIN, "textures/misc/ouroboros.png"), new ResourceLocation(ResourceUtil.DOMAIN, "textures/misc/neon.png"));
	}

	private static void initEntry()
	{
		INTRO = new ResearchItem("INTRO", ProjectInfo.ID, new AspectList(), 0, 0, 0, new ItemStack(ConfigItems.itemResource, 1, 15)).setRound().setAutoUnlock().registerResearchItem();
		INTRO.setPages(new ResearchPage[] {new ResearchPage("tc.research_page.INTRO.1"), new ResearchPage("tc.research_page.INTRO.2")});

		SARKOLOGIA = new ResearchItem("SARKOLOGIA", ProjectInfo.ID, new AspectList(), 0, 1, 0, new ResourceLocation(ResourceUtil.DOMAIN, "textures/misc/titan.png")).setRound().setAutoUnlock().registerResearchItem();
		SARKOLOGIA.setPages(new ResearchPage[] {new ResearchPage("tc.research_page.SARKOLOGIA.1")});

		MINERALOGY = new ResearchItem("MINERALOGY", ProjectInfo.ID, new AspectList(), 1, 1, 0, new ItemStack(Items.quartz, 1, 0)).setRound().setAutoUnlock().registerResearchItem();
		MINERALOGY.setPages(new ResearchPage[] {new ResearchPage("tc.research_page.MINERALOGY.1")});

		KNOWLEDGEWELL = new ResearchItem("TRANSCRIBINGTABLE", ProjectInfo.ID, new AspectList(), 1, 2, 0, new ItemStack(BlocksMN.transcribingTable, 1, 0)).setRound().setAutoUnlock().registerResearchItem();
		KNOWLEDGEWELL.setPages(new ResearchPage[] {new ResearchPage("tc.research_page.TRANSCRIBINGTABLE.1")});

		GEOCCULTISM = new ResearchItem("GEOCCULTISM", ProjectInfo.ID, new AspectList(), 0, 3, 0, new ItemStack(Blocks.grass, 1, 0)).setRound().setAutoUnlock().registerResearchItem();
		GEOCCULTISM.setPages(new ResearchPage[] {new ResearchPage("tc.research_page.GEOCCULTISM.1")});
	}
}
