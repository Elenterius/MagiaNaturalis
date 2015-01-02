package trinarybrain.magia.naturalis.common.network.packet;

import io.netty.buffer.ByteBuf;

public abstract class PacketBase
{	
	public abstract int getPacketID();

	public abstract void readData(ByteBuf data);

	public abstract void writeData(ByteBuf data);
}
