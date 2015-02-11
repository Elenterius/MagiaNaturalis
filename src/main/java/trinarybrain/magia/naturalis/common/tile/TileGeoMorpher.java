package trinarybrain.magia.naturalis.common.tile;

import java.util.List;

import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.wands.IWandable;
import thaumcraft.common.blocks.BlockCosmeticSolid;
import thaumcraft.common.lib.events.EssentiaHandler;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXBlockSparkle;
import thaumcraft.common.lib.world.biomes.BiomeHandler;
import trinarybrain.magia.naturalis.common.util.Platform;
import trinarybrain.magia.naturalis.common.util.WorldUtil;
import cpw.mods.fml.common.network.NetworkRegistry;

public class TileGeoMorpher extends TileThaumcraft implements IAspectContainer, IWandable
{
	public int ticks = 0;
	int morphX = 0;
	int morphZ = 0;
	public BiomeGenBase cachedBiome = null;
	BiomeGenBase lastBiome = null;
	AspectList morphCost = new AspectList();
	AspectList realCost = new AspectList();	
	public boolean idle = true;

	@Override
	public void updateEntity()
	{
		boolean update = false;
		if(Platform.isServer())
		{
			if(++this.ticks % 5 == 0)
				if(!idle)
				{
					if(this.validateStructure()) update = this.handleBiomeMorphing(8, cachedBiome);
					else
					{
						idle = true;
						this.realCost = new AspectList();
						update = true;

						if(this.morphX == 0)
						{
							this.morphZ--;
							this.morphX = 1024;
						}
						else this.morphX--;
					}
				}

			if(update)
			{
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
				this.markDirty();
			}
		}
	}

	public boolean validateStructure()
	{
		boolean valid = this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord) instanceof BlockAir ? true : false;
		for(int i = 1; i <= 3; i++)
		{
			if(!valid) return false;
			valid = this.worldObj.getBlock(this.xCoord, this.yCoord - 1 - i, this.zCoord) instanceof BlockCosmeticSolid && this.worldObj.getBlockMetadata(this.xCoord, this.yCoord - 1 - i, this.zCoord) == 0;
		}
		return valid;
	}

	public boolean handleBiomeMorphing(int radius, BiomeGenBase newBiome)
	{		
		boolean update = false;		
		boolean isComplete = false;
		if(this.realCost.visSize() > 0)
		{
			for(Aspect aspect : this.realCost.getAspects())
				if(this.realCost.getAmount(aspect) > 0)
					if(EssentiaHandler.drainEssentia(this, aspect, ForgeDirection.UNKNOWN, 12))
					{
						this.realCost.reduce(aspect, 1);
						this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
						this.markDirty();
						break;
					}

			if((this.realCost.visSize() <= 0))
			{
				isComplete = true;
				update = true;
				this.realCost = new AspectList();
				int posX = this.xCoord - radius + this.morphX;
				int posZ = this.zCoord - radius + this.morphZ;
				int posY = this.worldObj.getTopSolidOrLiquidBlock(posX, posZ);
				WorldUtil.setBiomeAt(this.worldObj, posX, posZ, newBiome);
				
				this.worldObj.getChunkFromBlockCoords(posX, posZ).setChunkModified();
				this.worldObj.markBlockForUpdate(posX, posY, posZ);
				
				PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(this.xCoord, this.yCoord, this.zCoord, newBiome.color), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 32.0D));
			}
		}
		else isComplete = true;

		if(isComplete && this.ticks % 20 == 0)
		{
			if(this.morphZ < radius * 2)
			{
				if(this.morphX < radius * 2)
				{
					this.morphX++;
				}
				else
				{
					this.morphX = 0;
					this.morphZ++;
				}

				int posX = this.xCoord - radius + this.morphX;
				int posZ = this.zCoord - radius + this.morphZ;
				BiomeGenBase oldBiome = this.worldObj.getBiomeGenForCoords(posX, posZ);

				if(newBiome != oldBiome && Math.sqrt((morphX-radius)*(morphX-radius) + (morphZ-radius)*(morphZ-radius)) <= radius)
				{
					if(this.lastBiome != newBiome) //prevent recalculation of Cost every Cycle
					{
						this.lastBiome = newBiome;
						this.morphCost = this.calculateMorphCost(newBiome);
					}
					this.realCost = this.morphCost.copy();
					return true;
				}
			}
			else
			{
				this.morphZ = 0;
				this.idle = true;
				update = true;
			}
		}

		return update;
	}

	public AspectList calculateMorphCost(BiomeGenBase newBiome)
	{  	    
		BiomeDictionary.Type[] newTypes = BiomeDictionary.getTypesForBiome(newBiome);
		AspectList aspectCost = new AspectList();
		for(int i = 0; i < newTypes.length; i++)
			if(newTypes[i] != null)
			{
				if(newTypes[i] != Type.MAGICAL) // Because Thaumcraft adds null aspect for magical biome type
				{
					Aspect aspect = (Aspect)((List)BiomeHandler.biomeInfo.get(newTypes[i])).get(1);
					if(aspect != null)
					{
						int aura = (int)((List)BiomeHandler.biomeInfo.get(newTypes[i])).get(0);
						aspectCost.add(aspect, Math.round(aura * 2F / 100F));
					}
				}
				else aspectCost.add(Aspect.MAGIC, 2);
			}
		return aspectCost;
	}

	@Override
	public void readFromNBT(NBTTagCompound data)
	{
		super.readFromNBT(data);
		this.morphX = data.getByte("morphX");
		this.morphZ = data.getByte("morphZ");
	}

	@Override
	public void writeToNBT(NBTTagCompound data)
	{
		super.writeToNBT(data);
		data.setByte("morphX", (byte) this.morphX);
		data.setByte("morphZ", (byte) this.morphZ);
	}

	@Override
	public void readCustomNBT(NBTTagCompound data) // Syncs to Client
	{
		this.realCost.readFromNBT(data);
		this.idle = data.getBoolean("idle");
		
		int id = data.getInteger("biomeID");
		this.cachedBiome = id >= 0 ? BiomeGenBase.getBiome(id) : null;
	}

	@Override
	public void writeCustomNBT(NBTTagCompound data) // Syncs to Client
	{
		this.realCost.writeToNBT(data);
		data.setBoolean("idle", this.idle);
		data.setInteger("biomeID", cachedBiome != null ? cachedBiome.biomeID : -1);
	}

	@Override
	public AspectList getAspects() { return this.realCost; }

	@Override
	public void setAspects(AspectList aspects) {}

	@Override
	public boolean doesContainerAccept(Aspect tag) { return false; }

	@Override
	public int addToContainer(Aspect tag, int amount) {	return 0; }

	@Override
	public boolean takeFromContainer(Aspect tag, int amount) { return false; }

	@Override
	public boolean takeFromContainer(AspectList ot) { return false; }

	@Override
	public boolean doesContainerContainAmount(Aspect tag, int amount) {	return false; }

	@Override
	public boolean doesContainerContain(AspectList ot) { return false; }

	@Override
	public int containerContains(Aspect tag) { return 0; }

	@Override
	public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side, int md)
	{
		if(player.isSneaking())
		{
			this.realCost = this.calculateMorphCost(this.cachedBiome);
		}
		else
		{
			this.idle = !this.idle;
		}
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		this.markDirty();
		return -1;
	}

	@Override
	public ItemStack onWandRightClick(World world, ItemStack wandstack, EntityPlayer player) { return null; }

	@Override
	public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count) {}

	@Override
	public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count) {}
}