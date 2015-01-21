package trinarybrain.magia.naturalis.common.recipe;

import java.util.HashMap;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import trinarybrain.magia.naturalis.common.block.BlocksMN;
import trinarybrain.magia.naturalis.common.item.ItemsMN;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes
{
	public static HashMap<String, Object> recipes = new HashMap();
	
	public static void init()
	{
		initDefault();
		initArcane();
		initInfusion();
	}
	
	static void initDefault()
	{
		//TODO: ADD WOOD RECIPES
		recipes.put("SickleThaumium", GameRegistry.addShapedRecipe(new ItemStack(ItemsMN.sickleThaumium, 1), " I ", "  I", "SI ", 'I', new ItemStack(ConfigItems.itemResource, 1, 2), 'S', Items.stick));
	}
	
	static void initArcane()
	{
		AspectList aspects = null;
		Object[] recipe = null;
		
		aspects = new AspectList().add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 20).add(Aspect.AIR, 20).add(Aspect.EARTH, 20).add(Aspect.FIRE, 20).add(Aspect.WATER, 20);
		recipe = new Object[] {"ALI", "TBW", "OLE",
				'A', new ItemStack(ConfigItems.itemShard, 1, 0),
				'I', new ItemStack(ConfigItems.itemShard, 1, 1),
				'T', new ItemStack(ConfigItems.itemShard, 1, 2),
				'W', new ItemStack(ConfigItems.itemShard, 1, 3),
				'O', new ItemStack(ConfigItems.itemShard, 1, 4),
				'E', new ItemStack(ConfigItems.itemShard, 1, 5),
				'L', Items.leather,
				'B', Items.book};
		recipes.put("ResearchLog", ThaumcraftApi.addArcaneCraftingRecipe("RESEARCH_LOG", new ItemStack(ItemsMN.researchLog, 1), aspects, recipe));
		
		aspects = new AspectList().add(Aspect.ORDER, 2).add(Aspect.ENTROPY, 2).add(Aspect.AIR, 2).add(Aspect.EARTH, 2).add(Aspect.FIRE, 2).add(Aspect.WATER, 2);
		recipe = new Object[] {"NXI", "N  ",
				'I', new ItemStack(ConfigItems.itemResource, 1, 2),
				'N', new ItemStack(ConfigItems.itemNugget, 1, 6),
				'X', new ItemStack(ConfigItems.itemNugget, 1, 0)};
		recipes.put("ThaumiumKey1", ThaumcraftApi.addArcaneCraftingRecipe("KEY_SPECIAL", new ItemStack(ItemsMN.key, 2, 0), aspects, recipe));
		recipe = new Object[] {"NXI", "N  ",
				'I', new ItemStack(ConfigItems.itemResource, 1, 2),
				'N', new ItemStack(ConfigItems.itemNugget, 1, 6),
				'X', Items.gold_nugget};
		recipes.put("ThaumiumKey2", ThaumcraftApi.addArcaneCraftingRecipe("KEY_SPECIAL", new ItemStack(ItemsMN.key, 2, 1), aspects, recipe));
		
		aspects = new AspectList().add(Aspect.ORDER, 5).add(Aspect.ENTROPY, 5).add(Aspect.AIR, 5).add(Aspect.EARTH, 5).add(Aspect.FIRE, 5).add(Aspect.WATER, 5);
		recipe = new Object[] {"ILI", "TGT",
				'I', Items.gold_ingot,
				'L', Items.leather,
				'T', ConfigItems.itemThaumometer,
				'G', ConfigItems.itemGoggles};
		recipes.put("Spectacles", ThaumcraftApi.addArcaneCraftingRecipe("SPECTACLES", new ItemStack(ItemsMN.spectacles), aspects, recipe));
		
		aspects = new AspectList().add(Aspect.ORDER, 30).add(Aspect.ENTROPY, 30).add(Aspect.AIR, 30).add(Aspect.EARTH, 30).add(Aspect.FIRE, 30).add(Aspect.WATER, 30);
		recipe = new Object[] {"ASI", "TDW", "OBE",
				'A', new ItemStack(ConfigItems.itemShard, 1, 0),
				'I', new ItemStack(ConfigItems.itemShard, 1, 1),
				'T', new ItemStack(ConfigItems.itemShard, 1, 2),
				'W', new ItemStack(ConfigItems.itemShard, 1, 3),
				'O', new ItemStack(ConfigItems.itemShard, 1, 4),
				'E', new ItemStack(ConfigItems.itemShard, 1, 5),
				'S', ConfigItems.itemInkwell,
				'D',  new ItemStack(ConfigBlocks.blockTable, 1, 14),
				'B', new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6)};
		recipes.put("TranscribingTable", ThaumcraftApi.addArcaneCraftingRecipe("TRANSCRIBINGTABLE", new ItemStack(BlocksMN.transcribingTable), aspects, recipe));
	}
	
	static void initInfusion()
	{
		ThaumcraftApi.addInfusionCraftingRecipe(research, result, instability, aspects, input, recipe)
	}
}
