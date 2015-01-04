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
import trinarybrain.magia.naturalis.common.block.BlockArcaneChest;
import trinarybrain.magia.naturalis.common.block.BlocksMN;
import trinarybrain.magia.naturalis.common.item.ItemsMN;
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
		
		research = new CustomResearchItem("RESEARCH_LOG", 1, 1, 0, new ItemStack(ItemsMN.researchLog, 1, 0));
		research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.RESEARCH_LOG.1") });
		research.setRound().setAutoUnlock().registerResearchItem();

		research = new CustomResearchItem("TRANSCRIBINGTABLE", 0, 1, 0, new ItemStack(BlocksMN.transcribingTable, 1, 0));
		research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.TRANSCRIBINGTABLE.1") });
		research.setRound().setAutoUnlock().registerResearchItem();

		research = new CustomResearchItem("FOCUS_BUILD", 0, 2, 0, new ItemStack(ItemsMN.focusBuild, 1, 0));
		research.setPages(new ResearchPage("mn.research_page.FOCUS_BUILD.1"));
		research.setRound().setAutoUnlock().registerResearchItem();
		
		research = new CustomResearchItem("GOGGLES_DARK", 0, 3, 0, new ItemStack(ItemsMN.gogglesDark, 1, 0));
		research.setPages(new ResearchPage("mn.research_page.GOGGLES_DARK.1"));
		research.setRound().setAutoUnlock().registerResearchItem();
		
		research = new CustomResearchItem("SPECTACLES", 0, 4, 0, new ItemStack(ItemsMN.spectacles, 1, 0));
		research.setPages(new ResearchPage("mn.research_page.SPECTACLES.1"));
		research.setRound().setAutoUnlock().registerResearchItem();
				
		research = new CustomResearchItem("KEY_SPECIAL", 1, 5, 0, new ItemStack(ItemsMN.key, 1, 0));
		research.setPages(new ResearchPage("mn.research_page.KEY_SPECIAL.1"), new ResearchPage("mn.research_page.KEY_SPECIAL.2"));
		research.setRound().setAutoUnlock().registerResearchItem();
		
		ItemStack chest = new ItemStack(BlocksMN.arcaneChest, 1, 1);
		BlockArcaneChest.setChestType(chest, (byte) 1);
		research = new CustomResearchItem("ARCANE_CHEST", 0, 5, 0, chest);
		research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.ARCANE_CHEST.1") });
		research.setRound().setAutoUnlock().registerResearchItem();
		
		research = new CustomResearchItem("JAR_PRISON", 1, 0, 0, new ItemStack(BlocksMN.jarPrison, 1, 0));
		research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.JAR_PRISON.1") });
		research.setRound().setAutoUnlock().registerResearchItem();
		
		research = new CustomResearchItem("STONE_PHENO", 1, 2, 0, new ItemStack(ItemsMN.mutationStone, 1, 0));
		research.setPages(new ResearchPage("mn.research_page.STONE_PHENO.1"));
		research.setRound().setAutoUnlock().registerResearchItem();
		
		research = new CustomResearchItem("STONE_QUICKSILVER", 1, 3, 0, new ItemStack(ItemsMN.quicksilverStone, 1, 0));
		research.setPages(new ResearchPage("mn.research_page.STONE_QUICKSILVER.1"));
		research.setRound().setAutoUnlock().registerResearchItem();
		
//==============================================================================================================================
		
//		research = new CustomResearchItem("SARKOLOGIA", 2, 1, 0, new ResourceLocation(ResourceUtil.DOMAIN, "textures/misc/titan.png"));
//		research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.SARKOLOGIA.1") });
//		research.setRound().setAutoUnlock().registerResearchItem();
//
//		research = new CustomResearchItem("MINERALOGY", 2, 2, 0, new ItemStack(Items.quartz, 1, 0));
//		research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.MINERALOGY.1") });
//		research.setRound().setAutoUnlock().registerResearchItem();
//		
//		research = new CustomResearchItem("GEOCCULTISM", 2, 3, 0, new ItemStack(Blocks.grass, 1, 0));
//		research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.GEOCCULTISM.1") });
//		research.setRound().setAutoUnlock().registerResearchItem();
//		
//		research = new CustomResearchItem("DUMMY", 2, 4, 0, new ItemStack(Blocks.lapis_ore, 1, 0));
//		research.setPages(new ResearchPage("mn.research_page.DUMMY.1"));
//		research.setRound().setAutoUnlock().registerResearchItem();
	}
}
