package trinarybrain.magia.naturalis.common.recipe;

import java.util.HashMap;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import trinarybrain.magia.naturalis.common.block.BlockArcaneChest;
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
	
	static IRecipe addShapelessOreDictRecipe(ItemStack output, Object... input)
	{
	    IRecipe recipe = new ShapelessOreRecipe(output, input);
	    CraftingManager.getInstance().getRecipeList().add(recipe);
	    return recipe;
	  }
	
	static void initDefault()
	{
		recipes.put("SickleThaumium", GameRegistry.addShapedRecipe(new ItemStack(ItemsMN.sickleThaumium, 1), " I ", "  I", "SI ", 'I', new ItemStack(ConfigItems.itemResource, 1, 2), 'S', Items.stick));
		
		recipes.put("GreatwoodOrn", GameRegistry.addShapedRecipe(new ItemStack(BlocksMN.arcaneWood, 1, 1), "W", "W", 'W', new ItemStack(ConfigBlocks.blockSlabWood, 6, 0)));
		recipes.put("GreatwoodSlab", new IRecipe[] {GameRegistry.addShapedRecipe(new ItemStack(ConfigBlocks.blockSlabWood, 6, 0), "WWW", 'W', new ItemStack(BlocksMN.arcaneWood, 1, 0)), GameRegistry.addShapedRecipe(new ItemStack(ConfigBlocks.blockSlabWood, 6, 0), "WWW", 'W', new ItemStack(BlocksMN.arcaneWood, 1, 1))});
		recipes.put("SilverwoodSlab", new IRecipe[] {GameRegistry.addShapedRecipe(new ItemStack(ConfigBlocks.blockSlabWood, 6, 1), "WWW", 'W', new ItemStack(BlocksMN.arcaneWood, 1, 2)), GameRegistry.addShapedRecipe(new ItemStack(ConfigBlocks.blockSlabWood, 6, 1), "WWW", 'W', new ItemStack(BlocksMN.arcaneWood, 1, 3))});
		recipes.put("PlankSilverwood", GameRegistry.addShapedRecipe(new ItemStack(BlocksMN.arcaneWood, 1, 2), "W", "W", Character.valueOf('W'), new ItemStack(ConfigBlocks.blockSlabWood, 6, 1)));
	
		recipes.put("WoodConversion", new IRecipe[] {
				addShapelessOreDictRecipe(new ItemStack(BlocksMN.arcaneWood, 1, 0), new ItemStack(ItemsMN.alchemicalStone, 1, 0), new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6)),
				addShapelessOreDictRecipe(new ItemStack(BlocksMN.arcaneWood, 1, 1), new ItemStack(ItemsMN.alchemicalStone, 1, 0), new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6)),
				addShapelessOreDictRecipe(new ItemStack(BlocksMN.arcaneWood, 1, 2), new ItemStack(ItemsMN.alchemicalStone, 1, 0), new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 7)),
				addShapelessOreDictRecipe(new ItemStack(BlocksMN.arcaneWood, 1, 3), new ItemStack(ItemsMN.alchemicalStone, 1, 0), new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 7)),
				addShapelessOreDictRecipe(new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6), new ItemStack(ItemsMN.alchemicalStone, 1, 0), new ItemStack(BlocksMN.arcaneWood, 1, 0)),
				addShapelessOreDictRecipe(new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6), new ItemStack(ItemsMN.alchemicalStone, 1, 0), new ItemStack(BlocksMN.arcaneWood, 1, 1)),
				addShapelessOreDictRecipe(new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 7), new ItemStack(ItemsMN.alchemicalStone, 1, 0), new ItemStack(BlocksMN.arcaneWood, 1, 2)),
				addShapelessOreDictRecipe(new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 7), new ItemStack(ItemsMN.alchemicalStone, 1, 0), new ItemStack(BlocksMN.arcaneWood, 1, 3))
		});

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
		
		aspects = new AspectList().add(Aspect.WATER, 20).add(Aspect.ORDER, 15).add(Aspect.EARTH, 15).add(Aspect.FIRE, 10);
		recipe = new Object[] {"IBI", "WCW", "IWI",
				'I', new ItemStack(ConfigItems.itemResource, 1, 2),
				'B', ConfigItems.itemZombieBrain,
				'W', new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6),
				'C', Blocks.chest};
		ItemStack arcaneChest = new ItemStack(BlocksMN.arcaneChest, 1, 1);
		BlockArcaneChest.setChestType(arcaneChest, (byte) 1);
		recipes.put("ArcaneChest1", ThaumcraftApi.addArcaneCraftingRecipe("ARCANE_CHEST", arcaneChest, aspects, recipe));
		recipe = new Object[] {"IBI", "WCW", "IWI",
				'I', new ItemStack(ConfigItems.itemResource, 1, 2),
				'B', ConfigItems.itemZombieBrain,
				'W', new ItemStack(BlocksMN.arcaneWood, 1, 2),
				'C', Blocks.chest};
		arcaneChest = new ItemStack(BlocksMN.arcaneChest, 1, 2);
		BlockArcaneChest.setChestType(arcaneChest, (byte) 2);
		recipes.put("ArcaneChest2", ThaumcraftApi.addArcaneCraftingRecipe("ARCANE_CHEST", arcaneChest, aspects, recipe));
	}

	static void initInfusion()
	{
		AspectList aspects = null;
		ItemStack[] recipe = null;

		aspects = new AspectList().add(Aspect.SENSES, 32).add(Aspect.ARMOR, 16).add(Aspect.DARKNESS, 32);
		recipe = new ItemStack[] {
				new ItemStack(ConfigItems.itemShard, 1, 5),
				new ItemStack(ConfigItems.itemShard, 1, 5),
				new ItemStack(Items.spider_eye),
				new ItemStack(Items.spider_eye),
				new ItemStack(Items.iron_ingot),
				new ItemStack(Items.iron_ingot),
				new ItemStack(Items.iron_ingot),
				new ItemStack(ConfigItems.itemZombieBrain)};
		recipes.put("GogglesDark", ThaumcraftApi.addInfusionCraftingRecipe("GOGGLES_DARK", new ItemStack(ItemsMN.gogglesDark), 3, aspects, new ItemStack(ConfigItems.itemGoggles), recipe));

		aspects = new AspectList().add(Aspect.GREED, 32).add(Aspect.CROP, 16).add(Aspect.HARVEST, 24).add(Aspect.TOOL, 8);
		ItemStack stackBook = new ItemStack(Items.enchanted_book);
		Items.enchanted_book.addEnchantment(stackBook, new EnchantmentData(Enchantment.fortune.effectId, 2));
		recipe = new ItemStack[] {
				new ItemStack(ConfigItems.itemShard, 1, 4),
				new ItemStack(ConfigItems.itemShard, 1, 5),
				new ItemStack(Items.wheat_seeds),
				new ItemStack(ConfigItems.itemResource, 1, 2),
				new ItemStack(ConfigItems.itemResource, 1, 2),
				stackBook};
		recipes.put("SickleElemental", ThaumcraftApi.addInfusionCraftingRecipe("SICKLE_ABUNDANCE", new ItemStack(ItemsMN.sickleElemental), 1, aspects, new ItemStack(ItemsMN.sickleThaumium), recipe));
		
		aspects = new AspectList().add(Aspect.CRAFT, 32).add(Aspect.TOOL, 16).add(Aspect.EXCHANGE, 8).add(Aspect.MECHANISM, 3);
		recipe = new ItemStack[] {
				new ItemStack(ConfigItems.itemShard, 1, 4),
				new ItemStack(ConfigItems.itemShard, 1, 6),
				new ItemStack(ConfigItems.itemShard, 1, 5),
				new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 7),
				new ItemStack(ConfigItems.itemShovelElemental),
				new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 7),
				new ItemStack(ConfigItems.itemResource, 1, 3),
				new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 7)
		};
		recipes.put("FocusBuild", ThaumcraftApi.addInfusionCraftingRecipe("FOCUS_BUILD", new ItemStack(ItemsMN.focusBuild), 5, aspects, new ItemStack(ConfigItems.itemFocusTrade), recipe));
		
		aspects = new AspectList().add(Aspect.ELDRITCH, 8).add(Aspect.VOID, 8).add(Aspect.TRAVEL, 8).add(Aspect.EXCHANGE, 3);
		recipe = new ItemStack[] {
				new ItemStack(Items.ender_pearl),
				new ItemStack(Blocks.ender_chest),
				new ItemStack(Items.ender_pearl)
		};
		recipes.put("EnderPouch", ThaumcraftApi.addInfusionCraftingRecipe("ENDER_POUCH", new ItemStack(ItemsMN.focusPouchEnder), 1, aspects, new ItemStack(ConfigItems.itemFocusPouch), recipe));
		
		aspects = new AspectList().add(Aspect.ELDRITCH, 8).add(Aspect.VOID, 8).add(Aspect.ARMOR, 8).add(Aspect.EXCHANGE, 8);
		recipe = new ItemStack[] {
				new ItemStack(Items.gold_ingot),
				new ItemStack(Items.ender_pearl),
				new ItemStack(Items.lead),
				new ItemStack(Items.ender_pearl),
		};
		recipes.put("JarPrison", ThaumcraftApi.addInfusionCraftingRecipe("JAR_PRISON", new ItemStack(BlocksMN.jarPrison), 2, aspects, new ItemStack(ConfigBlocks.blockJar), recipe));
		
		aspects = new AspectList().add(Aspect.EXCHANGE, 32);
		recipe = new ItemStack[] {
				new ItemStack(Items.emerald),
				new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 7),
				new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 7)
		};
		recipes.put("StonePheno", ThaumcraftApi.addInfusionCraftingRecipe("STONE_PHENO", new ItemStack(ItemsMN.alchemicalStone, 1, 0), 2, aspects, new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6), recipe));
		
		aspects = new AspectList().add(Aspect.EXCHANGE, 16).add(Aspect.WATER, 16).add(Aspect.MAGIC, 8).add(Aspect.FLESH, 6);
		recipe = new ItemStack[] {
				new ItemStack(Items.glowstone_dust),
				new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6),
				new ItemStack(Items.glowstone_dust),
				new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6)
		};
		recipes.put("StoneQuick", ThaumcraftApi.addInfusionCraftingRecipe("STONE_QUICKSILVER", new ItemStack(ItemsMN.alchemicalStone, 1, 1), 2, aspects, new ItemStack(ConfigItems.itemResource, 1, 3), recipe));
	}
}
