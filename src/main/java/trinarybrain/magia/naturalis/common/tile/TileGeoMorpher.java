package trinarybrain.magia.naturalis.common.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.BiomeGenBase;
import thaumcraft.api.TileThaumcraft;
import trinarybrain.magia.naturalis.common.core.Log;
import trinarybrain.magia.naturalis.common.util.Platform;
import trinarybrain.magia.naturalis.common.util.WorldUtil;

public class TileGeoMorpher extends TileThaumcraft
{
	public int ticks = 0;
	private int morphX = 0;
	private int morphZ = 0;

	@Override
	public void updateEntity()
	{
		boolean update = false;
		if(Platform.isServer())
		{
			if(++this.ticks >= 30) {update = this.handleBiomeMorphing(32);}
			if(update) this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	}

	public boolean handleBiomeMorphing(int radius)
	{
		ticks = 0;
		BiomeGenBase biome = BiomeGenBase.jungle;

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
			if(this.worldObj.getBiomeGenForCoords(posX, posZ) != biome && (Math.abs(morphX-radius) + Math.abs(morphZ-radius)) <= radius)
			{
				WorldUtil.setBiomeAt(this.worldObj, posX, posZ, biome);
				this.worldObj.getChunkFromBlockCoords(this.xCoord, this.zCoord).setChunkModified();
				Minecraft.getMinecraft().theWorld.markBlockForUpdate(posX, this.worldObj.getTopSolidOrLiquidBlock(posX, posZ), posZ); //Will only work in SSP
				return true;
			}
			ticks = 25;
		}
		else
		{
			this.morphZ = 0;
			ticks = 20;
		}
		return false;
	}

	@Override
	public void readCustomNBT(NBTTagCompound data)
	{
		this.morphX = data.getByte("morphX");
		this.morphZ = data.getByte("morphZ");
	}

	@Override
	public void writeCustomNBT(NBTTagCompound data)
	{
		data.setByte("morphX", (byte) this.morphX);
		data.setByte("morphZ", (byte) this.morphZ);
	}
}
