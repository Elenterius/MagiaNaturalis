package trinarybrain.magia.naturalis.common.item;

import net.minecraft.item.Item;
import trinarybrain.magia.naturalis.common.item.alchemy.ItemMutationStone;
import trinarybrain.magia.naturalis.common.item.alchemy.ItemQuicksilverStone;
import trinarybrain.magia.naturalis.common.item.artifact.DevTool;
import trinarybrain.magia.naturalis.common.item.artifact.ItemGogglesDark;
import trinarybrain.magia.naturalis.common.item.artifact.ItemKey;
import trinarybrain.magia.naturalis.common.item.artifact.ItemResearchLog;
import trinarybrain.magia.naturalis.common.item.artifact.ItemSpectacles;
import trinarybrain.magia.naturalis.common.util.NameUtil;
import trinarybrain.magia.naturalis.common.util.ResourceUtil;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemsMN
{
	public static Item devTool;
	public static Item researchLog;
	public static Item quicksilverStone;
	public static Item mutationStone;
	public static Item key;
	public static Item gogglesDark;
	public static Item spectacles;

	public static void initItems()
	{
		devTool = new DevTool(); registerItem(devTool, NameUtil.DEV_TOOL);
		researchLog = new ItemResearchLog(); registerItem(researchLog, NameUtil.RESEARCH_LOG);
		quicksilverStone = new ItemQuicksilverStone(); registerItem(quicksilverStone, NameUtil.QUICKSILVER_STONE);
		mutationStone = new ItemMutationStone(); registerItem(mutationStone, NameUtil.MUTATION_STONE);
		key = new ItemKey(); registerItem(key, NameUtil.KEY);
		gogglesDark = new ItemGogglesDark(); registerItem(gogglesDark, NameUtil.GOGGLES_DARK_CRYSTAL);
		spectacles = new ItemSpectacles(); registerItem(spectacles, NameUtil.SPECTACLES);
	}

	private static void registerItem(Item item, String str)
	{
		item.setUnlocalizedName(ResourceUtil.PREFIX + str);
		GameRegistry.registerItem(item, ResourceUtil.TAG_ITEM + str);
	}
}
