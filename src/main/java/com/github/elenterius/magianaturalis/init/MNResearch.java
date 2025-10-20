package com.github.elenterius.magianaturalis.init;

import com.github.elenterius.magianaturalis.MagiaNaturalis;
import com.github.elenterius.magianaturalis.block.chest.ArcaneChestType;
import com.github.elenterius.magianaturalis.research.CustomResearchItem;
import com.github.elenterius.magianaturalis.research.FakeResearchItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigItems;

public class MNResearch {

    static void init() {
        initCategory();
        initEntry();
        initObjectTag();
    }

    private static void initObjectTag() {
        ThaumcraftApi.registerObjectTag(new ItemStack(MNItems.sickleElemental), new AspectList().add(Aspect.HARVEST, 6).add(Aspect.TOOL, 2).add(Aspect.GREED, 6).add(Aspect.CRYSTAL, 6).add(Aspect.MAGIC, 3).add(Aspect.TREE, 1));
        ThaumcraftApi.registerObjectTag(new ItemStack(MNItems.sickleThaumium), new AspectList().add(Aspect.HARVEST, 4).add(Aspect.TOOL, 2).add(Aspect.METAL, 9).add(Aspect.MAGIC, 3).add(Aspect.TREE, 1));
        ThaumcraftApi.registerObjectTag(new ItemStack(MNItems.gogglesDark), new AspectList().add(Aspect.ARMOR, 4).add(Aspect.SENSES, 7).add(Aspect.DARKNESS, 5).add(Aspect.ENTROPY, 4).add(Aspect.BEAST, 3));
        ThaumcraftApi.registerObjectTag(new ItemStack(MNItems.researchLog), new AspectList().add(Aspect.MIND, 8).add(Aspect.VOID, 6).add(Aspect.MAGIC, 3).add(Aspect.CLOTH, 3));
        ThaumcraftApi.registerObjectTag(new ItemStack(MNItems.spectacles), new AspectList().add(Aspect.ARMOR, 4).add(Aspect.SENSES, 5).add(Aspect.CLOTH, 3).add(Aspect.GREED, 3));
        //TODO: ADD missing Aspect Tags
    }

    private static void initCategory() {
        ResearchCategories.registerCategory(MagiaNaturalis.MOD_ID, MagiaNaturalis.rl("textures/items/research_log.png"), MagiaNaturalis.rl("textures/gui/background.png"));
    }

    private static void initEntry() {
        ResearchItem research = null;

        research = new CustomResearchItem("INTRO", 0, 0, 0, new ItemStack(ConfigItems.itemResource, 1, 15));
        research.setPages(new ResearchPage("mn.research_page.INTRO.1")).setSpecial().setRound().setAutoUnlock().registerResearchItem();

        research = new CustomResearchItem("CARPENTRY", -2, 2, 0, new ItemStack(MNBlocks.arcaneWood, 1, 4));
        research.setPages(new ResearchPage("mn.research_page.CARPENTRY.1"), new ResearchPage((IRecipe) MNRecipes.recipes.get("GreatwoodOrn")), new ResearchPage((IRecipe) MNRecipes.recipes.get("PlankSilverwood")), new ResearchPage((IRecipe) MNRecipes.recipes.get("GreatwoodGoldOrn1")), new ResearchPage((IRecipe) MNRecipes.recipes.get("GreatwoodGoldOrn2")), new ResearchPage((IRecipe) MNRecipes.recipes.get("GreatwoodGoldTrim"))).setRound().setAutoUnlock().registerResearchItem();

        research = new CustomResearchItem("RESEARCH_LOG", new AspectList().add(Aspect.MIND, 3).add(Aspect.VOID, 3).add(Aspect.ORDER, 3), -1, -2, 0, new ItemStack(MNItems.researchLog, 1, 0));
        research.setPages(new ResearchPage("mn.research_page.RESEARCH_LOG.1"), new ResearchPage((IArcaneRecipe) MNRecipes.recipes.get("ResearchLog"))).setRound().setParentsHidden("DECONSTRUCTOR").registerResearchItem();

        research = new CustomResearchItem("TRANSCRIBINGTABLE", new AspectList().add(Aspect.MIND, 3).add(Aspect.VOID, 3).add(Aspect.ORDER, 3), -2, -4, 0, new ItemStack(MNBlocks.transcribingTable, 1, 0));
        research.setPages(new ResearchPage("mn.research_page.TRANSCRIBINGTABLE.1"), new ResearchPage((IArcaneRecipe) MNRecipes.recipes.get("TranscribingTable"))).setParents("RESEARCH_LOG").registerResearchItem();

        research = new CustomResearchItem("GOGGLES_DARK", new AspectList().add(Aspect.SENSES, 6).add(Aspect.AURA, 3).add(Aspect.MAGIC, 3).add(Aspect.DARKNESS, 4), -7, 2, 2, new ItemStack(MNItems.gogglesDark, 1, 0));
        research.setPages(new ResearchPage("mn.research_page.GOGGLES_DARK.1"), new ResearchPage((InfusionRecipe) MNRecipes.recipes.get("GogglesDark"))).setParents("MN_GOGGLES").setParentsHidden("SPECTACLES").registerResearchItem();
        ThaumcraftApi.addWarpToResearch("GOGGLES_DARK", 1);

        research = new CustomResearchItem("SPECTACLES", new AspectList().add(Aspect.SENSES, 3).add(Aspect.AURA, 3).add(Aspect.MAGIC, 3), -6, 0, 1, new ItemStack(MNItems.spectacles, 1, 0));
        research.setPages(new ResearchPage("mn.research_page.SPECTACLES.1"), new ResearchPage((IArcaneRecipe) MNRecipes.recipes.get("Spectacles"))).setSecondary().setParents("MN_GOGGLES").registerResearchItem();

        research = new FakeResearchItem("MN_GOGGLES", MagiaNaturalis.MOD_ID, "GOGGLES", "ARTIFICE", -4, 1, ResearchCategories.getResearch("GOGGLES").icon_item).registerResearchItem();

        research = new CustomResearchItem("KEY_SPECIAL", new AspectList().add(Aspect.TOOL, 4).add(Aspect.MIND, 3).add(Aspect.MECHANISM, 3), -4, -3, 3, new ItemStack(MNItems.arcaneKey, 1, 0));
        research.setPages(new ResearchPage("mn.research_page.KEY_SPECIAL.1"), new ResearchPage((IArcaneRecipe) MNRecipes.recipes.get("ThaumiumKey1")), new ResearchPage("mn.research_page.KEY_SPECIAL.2"), new ResearchPage((IArcaneRecipe) MNRecipes.recipes.get("ThaumiumKey2")));
        research.setParents("MN_WARDEDARCANA").registerResearchItem();

        ItemStack chest = new ItemStack(MNBlocks.arcaneChest, 1, ArcaneChestType.GREAT_WOOD.id());
        research = new CustomResearchItem("ARCANE_CHEST", new AspectList().add(Aspect.VOID, 4).add(Aspect.MIND, 3).add(Aspect.MECHANISM, 3).add(Aspect.ARMOR, 3), -7, -3, 3, chest);
        research.setPages(new ResearchPage("mn.research_page.ARCANE_CHEST.1"), new ResearchPage((IArcaneRecipe) MNRecipes.recipes.get("ArcaneChest1")), new ResearchPage((IArcaneRecipe) MNRecipes.recipes.get("ArcaneChest2"))).setParents("MN_WARDEDARCANA").registerResearchItem();

        research = new FakeResearchItem("MN_WARDEDARCANA", MagiaNaturalis.MOD_ID, "WARDEDARCANA", "ARTIFICE", -5, -2, ResearchCategories.getResearch("WARDEDARCANA").icon_item).registerResearchItem();

        research = new FakeResearchItem("MN_FOCUSTRADE", MagiaNaturalis.MOD_ID, "FOCUSTRADE", "THAUMATURGY", 2, -4, ResearchCategories.getResearch("FOCUSTRADE").icon_item).registerResearchItem();
        research = new CustomResearchItem("FOCUS_BUILD", new AspectList().add(Aspect.MAGIC, 3).add(Aspect.CRAFT, 6).add(Aspect.ORDER, 2).add(Aspect.EARTH, 2), 4, -5, 2, new ItemStack(MNItems.focusBuild, 1, 0));
        research.setPages(new ResearchPage("mn.research_page.FOCUS_BUILD.1"), new ResearchPage((InfusionRecipe) MNRecipes.recipes.get("FocusBuild"))).setParents("MN_FOCUSTRADE").registerResearchItem();

        research = new CustomResearchItem("STONE_PHENO", new AspectList().add(Aspect.MAGIC, 3).add(Aspect.EXCHANGE, 4).add(Aspect.EARTH, 2), 4, -3, 2, new ItemStack(MNItems.alchemicalStone, 1, 0));
        research.setPages(new ResearchPage("mn.research_page.STONE_PHENO.1"), new ResearchPage((InfusionRecipe) MNRecipes.recipes.get("StonePheno")), new ResearchPage((IRecipe[]) MNRecipes.recipes.get("WoodConversion")), new ResearchPage((IRecipe[]) MNRecipes.recipes.get("ColorConversion")));
        research.setParents("MN_FOCUSTRADE", "MN_CRUCIBLE").setSecondary().registerResearchItem();

        research = new FakeResearchItem("MN_CRUCIBLE", MagiaNaturalis.MOD_ID, "CRUCIBLE", "ALCHEMY", 3, -1, ResearchCategories.getResearch("CRUCIBLE").icon_item).registerResearchItem();

        research = new CustomResearchItem("STONE_QUICKSILVER", new AspectList().add(Aspect.SENSES, 3).add(Aspect.EXCHANGE, 4).add(Aspect.AURA, 2), 6, -1, 2, new ItemStack(MNItems.alchemicalStone, 1, 1));
        research.setPages(new ResearchPage("mn.research_page.STONE_QUICKSILVER.1"), new ResearchPage((InfusionRecipe) MNRecipes.recipes.get("StoneQuick"))).setParents("MN_CRUCIBLE").registerResearchItem();

        research = new FakeResearchItem("MN_FOCUSPOUCH", MagiaNaturalis.MOD_ID, "FOCUSPOUCH", "THAUMATURGY", 4, 2, ResearchCategories.getResearch("FOCUSPOUCH").icon_item).registerResearchItem();
        research = new CustomResearchItem("ENDER_POUCH", new AspectList().add(Aspect.ELDRITCH, 3).add(Aspect.VOID, 3), 6, 3, 2, new ItemStack(MNItems.focusPouchEnder, 1, 0));
        research.setPages(new ResearchPage("mn.research_page.ENDER_POUCH.1"), new ResearchPage((InfusionRecipe) MNRecipes.recipes.get("EnderPouch"))).setRound().setParents("MN_FOCUSPOUCH").registerResearchItem();

        research = new FakeResearchItem("MN_TRAVELTRUNK", MagiaNaturalis.MOD_ID, "TRAVELTRUNK", "GOLEMANCY", 1, 3, ResearchCategories.getResearch("TRAVELTRUNK").icon_item).registerResearchItem();
        research = new CustomResearchItem("EVIL_TRUNK", new AspectList().add(Aspect.SOUL, 3).add(Aspect.BEAST, 3).add(Aspect.TAINT, 3), 2, 5, 2, new ItemStack(MNItems.evilTrunkSpawner, 1, 0));
        research.setPages(new ResearchPage("mn.research_page.EVIL_TRUNK.1"), new ResearchPage((InfusionRecipe) MNRecipes.recipes.get("CorruptedTrunk")), new ResearchPage((InfusionRecipe) MNRecipes.recipes.get("SinisterTrunk")), new ResearchPage((InfusionRecipe) MNRecipes.recipes.get("DemonicTrunk")), new ResearchPage((InfusionRecipe) MNRecipes.recipes.get("TaintedTrunk"))).setParents("MN_TRAVELTRUNK").registerResearchItem();
        ThaumcraftApi.addWarpToResearch("EVIL_TRUNK", 1);

        research = new CustomResearchItem("JAR_PRISON", new AspectList().add(Aspect.TRAP, 6).add(Aspect.GREED, 3).add(Aspect.EXCHANGE, 3).add(Aspect.MOTION, 3), -1, 4, 3, new ItemStack(MNBlocks.jarPrison, 1, 0));
        research.setPages(new ResearchPage("mn.research_page.JAR_PRISON.1"), new ResearchPage((InfusionRecipe) MNRecipes.recipes.get("JarPrison"))).setParentsHidden("JARLABEL").registerResearchItem();

        research = new CustomResearchItem("SICKLE_THAUM", new AspectList().add(Aspect.TOOL, 3).add(Aspect.CROP, 3).add(Aspect.HARVEST, 3), -4, 3, 1, new ItemStack(MNItems.sickleThaumium, 1, 0));
        research.setPages(new ResearchPage("mn.research_page.SICKLE_THAUM.1"), new ResearchPage((IRecipe) MNRecipes.recipes.get("SickleThaumium"))).setParentsHidden("THAUMIUM").setSecondary().registerResearchItem();

        research = new CustomResearchItem("SICKLE_ABUNDANCE", new AspectList().add(Aspect.TOOL, 3).add(Aspect.CROP, 3).add(Aspect.HARVEST, 3).add(Aspect.GREED, 6), -5, 5, 2, new ItemStack(MNItems.sickleElemental, 1, 0));
        research.setPages(new ResearchPage("mn.research_page.SICKLE_ABUNDANCE.1"), new ResearchPage((InfusionRecipe) MNRecipes.recipes.get("SickleElemental"))).setParents("SICKLE_THAUM").setParentsHidden("INFUSION").registerResearchItem();

        research = new CustomResearchItem("GEO_OCCULTISM", new AspectList().add(Aspect.AURA, 4).add(Aspect.EXCHANGE, 3).add(Aspect.WEATHER, 3).add(Aspect.MAGIC, 6).add(Aspect.EARTH, 2).add(Aspect.AIR, 2), 0, -5, 0, new ItemStack(MNBlocks.geoPylon));
        research.setPages(new ResearchPage("mn.research_page.GEO_OCCULTISM.1"), new ResearchPage((InfusionRecipe) MNRecipes.recipes.get("GeoPylon")), new ResearchPage("mn.research_page.GEO_OCCULTISM.2"), new ResearchPage((IArcaneRecipe) MNRecipes.recipes.get("BiomeReport"))).setParentsHidden("INFUSION", "STONE_PHENO").registerResearchItem();

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
    }
}
