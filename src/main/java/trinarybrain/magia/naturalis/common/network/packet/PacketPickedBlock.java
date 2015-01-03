package trinarybrain.magia.naturalis.common.network.packet;

import io.netty.buffer.ByteBuf;

public class PacketPickedBlock extends PacketBase
{
	private int packetID;
	public int blockID;
	public int meta;
	
	public PacketPickedBlock() {}
	
	public PacketPickedBlock(int packetID, int blockID, int meta)
	{
		this.packetID = packetID;
		this.blockID = blockID;
		this.meta = meta;
	}
	
	@Override
	public void writeData(ByteBuf data)
	{
		data.writeByte(this.packetID);
		data.writeInt(this.blockID);
		data.writeInt(this.meta);
	}

	@Override
	public void readData(ByteBuf data)
	{
		this.packetID = data.readByte();
		this.blockID = data.readInt();
		this.meta = data.readInt();
	}

	@Override
	public int getPacketID()
	{
		return this.packetID;
	}
}
