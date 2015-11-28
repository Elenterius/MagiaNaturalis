package com.trinarybrain.magianaturalis.common.network.packet;

import com.trinarybrain.magianaturalis.common.network.packet.PacketBiomeChange.BiomeChangeMessage;
import com.trinarybrain.magianaturalis.common.util.Platform;
import com.trinarybrain.magianaturalis.common.util.WorldUtil;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class PacketBiomeChange implements IMessageHandler<BiomeChangeMessage, IMessage>
{

	@Override
	public IMessage onMessage(BiomeChangeMessage message, MessageContext ctx)
	{
		if(ctx.side.isClient())
		{
			System.out.println(String.format("Received %s for [%s%s] %s from Server", message.biomeID, message.posX, message.posZ, Minecraft.getMinecraft().theWorld));

			//Update biome
			WorldUtil.setBiomeAt(Minecraft.getMinecraft().theWorld, message.posX, message.posZ, BiomeGenBase.getBiome(message.biomeID));

			//Update biome-color
			int y = Minecraft.getMinecraft().theWorld.getTopSolidOrLiquidBlock(message.posX, message.posZ);
			Minecraft.getMinecraft().theWorld.markBlockForUpdate(message.posX, y, message.posZ);
		}

		return null;
	}

	public static class BiomeChangeMessage implements IMessage
	{
		private int posX;
		private int posZ;
		private short biomeID;

		public BiomeChangeMessage() {}

		public BiomeChangeMessage(int x, int z, short biomeId)
		{
			posX = x;
			posZ = z;
			biomeID = biomeId;
		}

		@Override
		public void fromBytes(ByteBuf buf)
		{
			posX = buf.readInt();
			posZ = buf.readInt();
			biomeID = buf.readShort();
		}

		@Override
		public void toBytes(ByteBuf buf)
		{
			buf.writeInt(posX);
			buf.writeInt(posZ);
			buf.writeShort(biomeID);
		}
	}

}
