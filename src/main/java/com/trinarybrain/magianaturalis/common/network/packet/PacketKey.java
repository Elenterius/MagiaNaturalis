package com.trinarybrain.magianaturalis.common.network.packet;

import io.netty.buffer.ByteBuf;

public class PacketKey extends PacketBase
{
	private int packetID;
	public int key;

	public PacketKey() {}

	public PacketKey(int packetID, int key)
	{
		this.packetID = packetID;
		this.key = key;
	}

	@Override
	public void writeData(ByteBuf data)
	{
		data.writeByte(packetID);
		data.writeByte(this.key);
	}

	@Override
	public void readData(ByteBuf data)
	{
		this.packetID = data.readByte();
		this.key = data.readByte();
	}

	@Override
	public int getPacketID()
	{
		return this.packetID;
	}
}