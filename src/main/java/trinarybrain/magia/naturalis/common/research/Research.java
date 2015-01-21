package trinarybrain.magia.naturalis.common.research;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.IInfusionStabiliser;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigItems;
import trinarybrain.magia.naturalis.common.ProjectInfo;
import trinarybrain.magia.naturalis.common.block.BlockArcaneChest;
import trinarybrain.magia.naturalis.common.block.BlocksMN;
import trinarybrain.magia.naturalis.common.item.ItemsMN;
import trinarybrain.magia.naturalis.common.recipe.Recipes;
import trinarybrain.magia.naturalis.common.util.ResourceUtil;

public class Research
{
	public static void init()
	{
		initCategory();
		initEntry();
		initObjectTag();
	}
	
	private static void initObjectTag()
	{
		ThaumcraftApi.registerObjectTag(new ItemStack(ItemsMN.sickleElemental), new AspectList().add(Aspect.HARVEST, 6).add(Aspect.TOOL, 2).add(Aspect.GREED, 6).add(Aspect.CRYSTAL, 6).add(Aspect.MAGIC, 3).add(Aspect.TREE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ItemsMN.sickleThaumium), new AspectList().add(Aspect.HARVEST, 4).add(Aspect.TOOL, 2).add(Aspect.METAL, 9).add(Aspect.MAGIC, 3).add(Aspect.TREE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(ItemsMN.gogglesDark), new AspectList().add(Aspect.ARMOR, 4).add(Aspect.SENSES, 7).add(Aspect.DARKNESS, 5).add(Aspect.ENTROPY, 4).add(Aspect.BEAST, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(ItemsMN.researchLog), new AspectList().add(Aspect.MIND, 8).add(Aspect.VOID, 6).add(Aspect.MAGIC, 3).add(Aspect.CLOTH, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(ItemsMN.spectacles), new AspectList().add(Aspect.ARMOR, 4).add(Aspect.SENSES, 5).add(Aspect.CLOTH, 3).add(Aspect.GREED, 3));
		//TODO: ADD the Rest
	}

	private static void initCategory()
	{
		ResearchCategories.registerCategory(ProjectInfo.ID, new ResourceLocation(ResourceUtil.DOMAIN, "textures/items/book_magia_natura.png"), new ResourceLocation(ResourceUtil.DOMAIN, "textures/gui/background.png"));
	}

	private static void initEntry() //TODO: Include Crafting Pages
	{
		ResearchItem research = null;

		research = new CustomResearchItem("INTRO", 0, 0, 0, new ItemStack(ConfigItems.itemResource, 1, 15));
		research.setPages(new ResearchPage("mn.research_page.INTRO.1"), new ResearchPage("mn.research_page.INTRO.2")).setSpecial().setRound().setAutoUnlock().registerResearchItem();
		
		research = new CustomResearchItem("RESEARCH_LOG", new AspectList().add(Aspect.MIND, 3).add(Aspect.VOID, 3).add(Aspect.ORDER, 3), -1, -2, 0, new ItemStack(ItemsMN.researchLog, 1, 0));
		research.setPages(new ResearchPage("mn.research_page.RESEARCH_LOG.1"), new ResearchPage((IArcaneRecipe)Recipes.recipes.get("ResearchLog"))).setRound().setParentsHidden("DECONSTRUCTOR").registerResearchItem();

		research = new CustomResearchItem("TRANSCRIBINGTABLE", new AspectList().add(Aspect.MIND, 3).add(Aspect.VOID, 3).add(Aspect.ORDER, 3), -2, -4, 0, new ItemStack(BlocksMN.transcribingTable, 1, 0));
		research.setPages(new ResearchPage("mn.research_page.TRANSCRIBINGTABLE.1"), new ResearchPage((IArcaneRecipe)Recipes.recipes.get("TranscribingTable"))).setParents("RESEARCH_LOG").registerResearchItem();
		
		research = new CustomResearchItem("GOGGLES_DARK", new AspectList().add(Aspect.SENSES, 6).add(Aspect.AURA, 3).add(Aspect.MAGIC, 3).add(Aspect.DARKNESS, 4), -8, 1, 2, new ItemStack(ItemsMN.gogglesDark, 1, 0));
		research.setPages(new ResearchPage("mn.research_page.GOGGLES_DARK.1"), new ResearchPage((InfusionRecipe)Recipes.recipes.get("GogglesDark"))).setParents("MN_GOGGLES").setParentsHidden("SPECTACLES").registerResearchItem();
		
		research = new CustomResearchItem("SPECTACLES", new AspectList().add(Aspect.SENSES, 3).add(Aspect.AURA, 3).add(Aspect.MAGIC, 3), -6, 0, 1, new ItemStack(ItemsMN.spectacles, 1, 0));
		research.setPages(new ResearchPage("mn.research_page.SPECTACLES.1"), new ResearchPage((IArcaneRecipe)Recipes.recipes.get("Spectacles"))).setSecondary().setParents("MN_GOGGLES").registerResearchItem();
		
		research = new FakeResearchItem("MN_GOGGLES", ProjectInfo.ID, "GOGGLES", "ARTIFICE", -4, 1, ResearchCategories.getResearch("GOGGLES").icon_item).registerResearchItem();
				
		research = new CustomResearchItem("KEY_SPECIAL", new AspectList().add(Aspect.TOOL, 4).add(Aspect.MIND, 3).add(Aspect.MECHANISM, 3), -4, -3, 3, new ItemStack(ItemsMN.key, 1, 0));
		research.setPages(new ResearchPage("mn.research_page.KEY_SPECIAL.1"), new ResearchPage((IArcaneRecipe)Recipes.recipes.get("ThaumiumKey1")), new ResearchPage("mn.research_page.KEY_SPECIAL.2"), new ResearchPage((IArcaneRecipe)Recipes.recipes.get("ThaumiumKey2")));
		research.setParents("MN_WARDEDARCANA").registerResearchItem();
		
		ItemStack chest = new ItemStack(BlocksMN.arcaneChest, 1, 1);
		BlockArcaneChest.setChestType(chest, (byte) 1);
		research = new CustomResearchItem("ARCANE_CHEST", new AspectList().add(Aspect.VOID, 4).add(Aspect.MIND, 3).add(Aspect.MECHANISM, 3).add(Aspect.ARMOR, 3), -7, -3, 3, chest);
		research.setPages(new ResearchPage("mn.research_page.ARCANE_CHEST.1"), new ResearchPage((IArcaneRecipe)Recipes.recipes.get("ArcaneChest1")), new ResearchPage((IArcaneRecipe)Recipes.recipes.get("ArcaneChest2"))).setParents("MN_WARDEDARCANA").registerResearchItem();
		
		research = new FakeResearchItem("MN_WARDEDARCANA", ProjectInfo.ID, "WARDEDARCANA", "ARTIFICE", -5, -2, ResearchCategories.getResearch("WARDEDARCANA").icon_item).registerResearchItem();
		
		research = new FakeResearchItem("MN_FOCUSTRADE", ProjectInfo.ID, "FOCUSTRADE", "THAUMATURGY", 2, -4, ResearchCategories.getResearch("FOCUSTRADE").icon_item).registerResearchItem();
		research = new CustomResearchItem("FOCUS_BUILD", new AspectList().add(Aspect.MAGIC, 3).add(Aspect.CRAFT, 6).add(Aspect.ORDER, 2).add(Aspect.EARTH, 2), 4, -5, 2, new ItemStack(ItemsMN.focusBuild, 1, 0));
		research.setPages(new ResearchPage("mn.research_page.FOCUS_BUILD.1"), new ResearchPage((InfusionRecipe)Recipes.recipes.get("FocusBuild"))).setParents("MN_FOCUSTRADE").registerResearchItem();
		
		research = new CustomResearchItem("STONE_PHENO", new AspectList().add(Aspect.MAGIC, 3).add(Aspect.EXCHANGE, 4).add(Aspect.EARTH, 2), 4, -3, 2, new ItemStack(ItemsMN.alchemicalStone, 1, 0));
		research.setPages(new ResearchPage("mn.research_page.STONE_PHENO.1"), new ResearchPage((InfusionRecipe)Recipes.recipes.get("StonePheno")), new ResearchPage((IRecipe[])Recipes.recipes.get("WoodConversion")));
		research.setParents("MN_FOCUSTRADE" , "MN_CRUCIBLE").setSecondary().registerResearchItem();
		
		research = new FakeResearchItem("MN_CRUCIBLE", ProjectInfo.ID, "CRUCIBLE", "ALCHEMY", 3, -1, ResearchCategories.getResearch("CRUCIBLE").icon_item).registerResearchItem();
		
		research = new CustomResearchItem("STONE_QUICKSILVER", new AspectList().add(Aspect.SENSES, 3).add(Aspect.EXCHANGE, 4).add(Aspect.AURA, 2), 6, -1, 2, new ItemStack(ItemsMN.alchemicalStone, 1, 1));
		research.setPages(new ResearchPage("mn.research_page.STONE_QUICKSILVER.1"), new ResearchPage((InfusionRecipe)Recipes.recipes.get("StoneQuick"))).setRound().setParents("MN_CRUCIBLE").registerResearchItem();
		
		research = new FakeResearchItem("MN_FOCUSPOUCH", ProjectInfo.ID, "FOCUSPOUCH", "THAUMATURGY", 4, 2, ResearchCategories.getResearch("FOCUSPOUCH").icon_item).registerResearchItem();
		research = new CustomResearchItem("ENDER_POUCH", new AspectList().add(Aspect.ELDRITCH, 3).add(Aspect.VOID, 3), 6, 3, 2, new ItemStack(ItemsMN.focusPouchEnder, 1, 0));
		research.setPages(new ResearchPage("mn.research_page.ENDER_POUCH.1"), new ResearchPage((InfusionRecipe)Recipes.recipes.get("EnderPouch"))).setRound().setParents("MN_FOCUSPOUCH").registerResearchItem();
		
		research = new FakeResearchItem("MN_TRAVELTRUNK", ProjectInfo.ID, "TRAVELTRUNK", "GOLEMANCY", 1, 3, ResearchCategories.getResearch("TRAVELTRUNK").icon_item).registerResearchItem();
		research = new CustomResearchItem("EVIL_TRUNK", new AspectList().add(Aspect.SOUL, 3).add(Aspect.BEAST, 3).add(Aspect.TAINT, 3), 2, 5, 2, new ItemStack(ItemsMN.evilTrunkSpawner, 1, 0));
		research.setPages(new ResearchPage("mn.research_page.EVIL_TRUNK.1")).setParents("MN_TRAVELTRUNK").registerResearchItem();
		
		research = new CustomResearchItem("JAR_PRISON", new AspectList().add(Aspect.TRAP, 6).add(Aspect.GREED, 3).add(Aspect.EXCHANGE, 3).add(Aspect.MOTION, 3), -1, 4, 3, new ItemStack(BlocksMN.jarPrison, 1, 0));
		research.setPages(new ResearchPage("mn.research_page.JAR_PRISON.1"), new ResearchPage((InfusionRecipe)Recipes.recipes.get("JarPrison"))).setParentsHidden("JARLABEL").registerResearchItem();
		
		research = new CustomResearchItem("SICKLE_THAUM", new AspectList().add(Aspect.TOOL, 3).add(Aspect.CROP, 3).add(Aspect.HARVEST, 3), -4, 3, 1, new ItemStack(ItemsMN.sickleThaumium, 1, 0));
		research.setPages(new ResearchPage("mn.research_page.SICKLE_THAUM.1"), new ResearchPage((IRecipe)Recipes.recipes.get("SickleThaumium"))).setParentsHidden("THAUMIUM").setSecondary().registerResearchItem();
		
		research = new CustomResearchItem("SICKLE_ABUNDANCE", new AspectList().add(Aspect.TOOL, 3).add(Aspect.CROP, 3).add(Aspect.HARVEST, 3).add(Aspect.GREED, 6), -5, 5, 2, new ItemStack(ItemsMN.sickleElemental, 1, 0));
		research.setPages(new ResearchPage("mn.research_page.SICKLE_ABUNDANCE.1"), new ResearchPage((InfusionRecipe)Recipes.recipes.get("SickleElemental"))).setParents("SICKLE_THAUM").setParentsHidden("INFUSION").registerResearchItem();
		
		research = null;
		
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
	}
}
