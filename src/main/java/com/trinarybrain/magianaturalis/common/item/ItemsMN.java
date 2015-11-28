package com.trinarybrain.magianaturalis.common.item;

import com.trinarybrain.magianaturalis.common.Reference;
import com.trinarybrain.magianaturalis.common.item.alchemy.ItemAlchemicalStone;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemBiomeSampler;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemElementalSickle;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemEvilTrunkSpawner;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemGogglesDark;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemKey;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemResearchLog;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemSpectacles;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemThaumiumSickle;
import com.trinarybrain.magianaturalis.common.item.baubles.ItemFocusPouchEnder;
import com.trinarybrain.magianaturalis.common.item.focus.ItemFocusBuild;
import com.trinarybrain.magianaturalis.common.item.focus.ItemFocusRevenant;
import com.trinarybrain.magianaturalis.common.util.NameUtil;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ItemsMN
{
	public static Item researchLog;
	public static Item alchemicalStone;
	public static Item key;
	public static Item gogglesDark;
	public static Item spectacles;
	public static Item focusBuild;
	public static Item evilTrunkSpawner;
	public static Item focusPouchEnder;
	public static Item sickleThaumium;
	public static Item sickleElemental;
	public static Item biomeReport;
	public static Item focusRevenant;

	//public static Item devTool;
	//public static Item biomeDevTool;

	public static void initItems()
	{
		researchLog = new ItemResearchLog(); registerItem(researchLog, NameUtil.RESEARCH_LOG);
		alchemicalStone = new ItemAlchemicalStone(); registerItem(alchemicalStone, NameUtil.ALCHEMICAL_STONE);
		key = new ItemKey(); registerItem(key, NameUtil.KEY);
		gogglesDark = new ItemGogglesDark(); registerItem(gogglesDark, NameUtil.GOGGLES_DARK_CRYSTAL);
		spectacles = new ItemSpectacles(); registerItem(spectacles, NameUtil.SPECTACLES);
		focusBuild = new ItemFocusBuild(); registerItem(focusBuild, "focusBuild");
		evilTrunkSpawner = new ItemEvilTrunkSpawner(); registerItem(evilTrunkSpawner, "evilTrunk");
		focusPouchEnder = new ItemFocusPouchEnder(); registerItem(focusPouchEnder, NameUtil.ENDER_FOCUS_POUCH);
		sickleThaumium = new ItemThaumiumSickle(); registerItem(sickleThaumium, NameUtil.SICKLE_THAUMIUM);
		sickleElemental = new ItemElementalSickle(); registerItem(sickleElemental, NameUtil.SICKLE_ELEMENTAL);
		biomeReport = new ItemBiomeSampler(); registerItem(biomeReport, NameUtil.BIOME_SAMPLER);
		focusRevenant = new ItemFocusRevenant(); registerItem(focusRevenant, "focusRevenant");

		//devTool = new DevTool(); registerItem(devTool, NameUtil.DEV_TOOL);
		//biomeDevTool = new BiomeDevTool(); registerItem(biomeDevTool, "devTool");
	}

	private static void registerItem(Item item, String str)
	{
		item.setUnlocalizedName(Reference.ID + ":" + str);
		GameRegistry.registerItem(item, "item." + str);
	}
}
