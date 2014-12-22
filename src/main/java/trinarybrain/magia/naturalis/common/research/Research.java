package trinarybrain.magia.naturalis.common.research;

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
		ResearchItem research = null;

		research = new CustomResearchItem("INTRO", 0, 0, 0, new ItemStack(ConfigItems.itemResource, 1, 15));
		research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.INTRO.1"), new ResearchPage("mn.research_page.INTRO.2") });
		research.setRound().setAutoUnlock().registerResearchItem();

		research = new CustomResearchItem("SARKOLOGIA", 0, 1, 0, new ResourceLocation(ResourceUtil.DOMAIN, "textures/misc/titan.png"));
		research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.SARKOLOGIA.1") });
		research.setRound().setAutoUnlock().registerResearchItem();

		research = new CustomResearchItem("MINERALOGY", 1, 1, 0, new ItemStack(Items.quartz, 1, 0));
		research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.MINERALOGY.1") });
		research.setRound().setAutoUnlock().registerResearchItem();

		research = new CustomResearchItem("TRANSCRIBINGTABLE", 1, 2, 0, new ItemStack(BlocksMN.transcribingTable, 1, 0));
		research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.TRANSCRIBINGTABLE.1") });
		research.setRound().setAutoUnlock().registerResearchItem();

		research = new CustomResearchItem("GEOCCULTISM", 0, 3, 0, new ItemStack(Blocks.grass, 1, 0));
		research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.GEOCCULTISM.1") });
		research.setRound().setAutoUnlock().registerResearchItem();
		
		research = new CustomResearchItem("DUMMY", 0, 4, 0, new ItemStack(Blocks.lapis_ore, 1, 0));
		research.setPages(new ResearchPage("mn.research_page.DUMMY.1"));
		research.setRound().setAutoUnlock().registerResearchItem();
	}
}
