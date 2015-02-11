package trinarybrain.magia.naturalis.common.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import trinarybrain.magia.naturalis.common.network.packet.PacketBase;
import trinarybrain.magia.naturalis.common.network.packet.PacketBiomeChange;
import trinarybrain.magia.naturalis.common.network.packet.PacketKey;
import trinarybrain.magia.naturalis.common.network.packet.PacketPickedBlock;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;

public class ChannelHandler extends FMLIndexedMessageToMessageCodec<PacketBase>
{
	public ChannelHandler()
	{
		this.addDiscriminator(0, PacketKey.class);
		this.addDiscriminator(1, PacketPickedBlock.class);
		this.addDiscriminator(2, PacketBiomeChange.class);
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, PacketBase packet, ByteBuf data) throws Exception
	{
		packet.writeData(data);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf data, PacketBase packet)
	{
		packet.readData(data);
	}
}
