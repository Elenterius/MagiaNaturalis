package trinarybrain.magia.naturalis.common.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.world.biomes.BiomeHandler;
import trinarybrain.magia.naturalis.common.core.Log;
import trinarybrain.magia.naturalis.common.util.Platform;
import trinarybrain.magia.naturalis.common.util.ResourceUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiomeDevTool extends BaseItem
{
	public BiomeDevTool()
	{
		super();
		this.maxStackSize = 1;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		super.addInformation(stack, player, list, par4);
		list.add(EnumChatFormatting.DARK_PURPLE + "Last Biome: " + stack.getItemDamage());
	}

	@Override @SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister icon)
	{
		this.itemIcon = icon.registerIcon(ResourceUtil.PREFIX + "book_ouroboros");
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if(Platform.isClient()) return stack;
		return stack;
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
	{
		if(Platform.isClient()) return false;

		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
	    int aura = BiomeHandler.getBiomeAura(biome);
	    
	    BiomeDictionary.Type[] typeArray = BiomeDictionary.getTypesForBiome(BiomeGenBase.getBiome(biome.biomeID));
	    Aspect aspect;
	    AspectList aspectList = new AspectList();
	    
	    String aspects = "";
	    String types = "";
	    
	    for(BiomeDictionary.Type type : typeArray)
	    {
	    	types += "[" + type.toString() + "]";
	    	aspect = (Aspect)((List)BiomeHandler.biomeInfo.get(type)).get(1);
	    	if(aspect != null)
		    {
		      aspectList.add(aspect, 1);
		      aspects += "[" + aspect.getName() + "]";
		    }
	    }
	    
	    
	    String divider = "------------------------------------------------------------------------------------";
	    Log.logger.info(String.format("%n" + divider + "%n  - Biome: %s, ID: %d%n  - Aura: %d%n  - Types: %s%n  - Aspects: %s%n" + divider, biome.biomeName, biome.biomeID, aura, types, aspects));
	    
		return false;
	}
}