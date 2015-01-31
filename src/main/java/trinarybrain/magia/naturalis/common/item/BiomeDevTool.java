package trinarybrain.magia.naturalis.common.item;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager.BiomeType;
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
		
	    String divider = "------------------------------------------------------------------------------------";
	    
	    try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/GithubEnv/MagiaNaturalis/eclipse/Biome_Types-Aspects.txt"))))
	    {
	    	String aspects = "";
	    	String types = "";
	    	int auraLevel = 0;

	    	Aspect aspect;
	    	for(BiomeDictionary.Type type : BiomeDictionary.Type.values())
	    	{
	    		types = "[" + type.toString() + "]";
	    		aspect = (Aspect)((List)BiomeHandler.biomeInfo.get(type)).get(1);
	    		auraLevel = (int)((List)BiomeHandler.biomeInfo.get(type)).get(0);
	    		if(aspect != null)
	    		{
	    			aspects = "[" + aspect.getName() + "]";
	    		}
	    		else
		    	{
		    		aspects = "[NULL]";
		    	}
	    		
	    		String info = String.format("%-16s :\t[Aura: %d]\t\t-\t%s%n", types, auraLevel, aspects);
		    	writer.write(info);
	    	}
	    }
	    catch(IOException e)
	    {
	    	e.printStackTrace();
	    }
	    
	    try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/GithubEnv/MagiaNaturalis/eclipse/Biome_Composition.txt"))))
	    {
	    	BiomeGenBase[] biomes = BiomeGenBase.getBiomeGenArray();
			for(BiomeGenBase biome : biomes)
			{
				if(biome != null)
				{
					int aura = BiomeHandler.getBiomeAura(biome);
				   
				    String aspects = "";
				    String types = "";
				    
				    Aspect aspect;
				    for(BiomeDictionary.Type type : BiomeDictionary.getTypesForBiome(biome))
				    {
				    	types += "[" + type.toString() + "]";
				    	aspect = (Aspect)((List)BiomeHandler.biomeInfo.get(type)).get(1);
				    	
				    	if(aspect != null)
				    	{
				    		aspects += "[" + aspect.getName() + "]";
				    	}
				    	else
				    	{
				    		aspects += "[NULL]";
				    	}
				    }
				    
				    String info = String.format(divider + "%nBiome: %s%n  - Aura Average.: %d%n  - Types: %s%n  - Aspects: %s%n", biome.biomeName, aura, types, aspects);
				    writer.write(info);
				}
			}
	    }
		catch(IOException e)
		{
			e.printStackTrace();
		}
	    
		return false;
	}
}