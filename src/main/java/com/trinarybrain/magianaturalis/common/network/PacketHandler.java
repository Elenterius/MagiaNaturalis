package com.trinarybrain.magianaturalis.common.network;

import com.trinarybrain.magianaturalis.common.Reference;
import com.trinarybrain.magianaturalis.common.network.packet.PacketBiomeChange;
import com.trinarybrain.magianaturalis.common.network.packet.PacketKeyInput;
import com.trinarybrain.magianaturalis.common.network.packet.PacketPickedBlock;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler
{
	public static SimpleNetworkWrapper network;
	private static int nextPacketID = 0;

	public static void initPackets()
	{
		network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.ID.toUpperCase());
		registerMessage(PacketKeyInput.class, PacketKeyInput.KeyInputMessage.class);
		registerMessage(PacketPickedBlock.class, PacketPickedBlock.PickedBlockMessage.class);
		registerMessage(PacketBiomeChange.class, PacketBiomeChange.BiomeChangeMessage.class);
	}

	private static void registerMessage(Class packet, Class message)
	{
		network.registerMessage(packet, message, nextPacketID, Side.CLIENT);
		network.registerMessage(packet, message, nextPacketID, Side.SERVER);
		nextPacketID++;
	}

}
