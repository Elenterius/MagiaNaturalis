package trinarybrain.magia.naturalis.common.recipe;

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
	public static ShapedArcaneRecipe recipeResearchLog;
	
	public static void init()
	{
		initDefault();
		initThaumic();
	}
	
	static void initDefault()
	{
		GameRegistry.addShapelessRecipe(new ItemStack(BlocksMN.arcaneWood, 1, 0), new ItemStack(ConfigBlocks.blockSlabWood, 2, 0));
	}
	
	static void initThaumic() //TODO: FIX THIS BROKEN SHIZZ
	{
		recipeResearchLog = ThaumcraftApi.addArcaneCraftingRecipe("RESEARCH_LOG", new ItemStack(ItemsMN.researchLog, 1, 0), new AspectList().add(Aspect.ORDER, 80).add(Aspect.ENTROPY, 80).add(Aspect.AIR, 80).add(Aspect.EARTH, 80).add(Aspect.FIRE, 80).add(Aspect.WATER, 80), "ALA", "AAA", "ALA", 'A', new ItemStack(ConfigItems.itemInkwell, 1, 2), 'L', new ItemStack(Items.leather, 1));
	}
}
