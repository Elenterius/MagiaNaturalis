package trinarybrain.magia.naturalis.common.network.packet;

import io.netty.buffer.ByteBuf;

public class PacketBiomeChange extends PacketBase
{
	private int packetID;
	public int x;
	public int z;
	public short biomeID;
	
	public PacketBiomeChange() {}

	public PacketBiomeChange(int packetID, int x, int z, short biomeID)
	{
		this.packetID = packetID;
		this.x = x;
		this.z = z;
		this.biomeID = biomeID;
	}

	@Override
	public void writeData(ByteBuf data)
	{
		data.writeByte(this.packetID);
		data.writeInt(this.x);
		data.writeInt(this.z);
		data.writeShort(this.biomeID);
	}

	@Override
	public void readData(ByteBuf data)
	{
		this.packetID = data.readByte();
		this.x = data.readInt();
		this.z = data.readInt();
		this.biomeID = data.readShort();
	}

	@Override
	public int getPacketID()
	{
		return this.packetID;
	}
}