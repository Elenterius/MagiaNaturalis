package trinarybrain.magia.naturalis.common.network;

import java.util.EnumMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

import org.apache.logging.log4j.Level;

import trinarybrain.magia.naturalis.common.core.Log;
import trinarybrain.magia.naturalis.common.network.packet.PacketBase;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.FMLOutboundHandler.OutboundTarget;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

public class NetworkHandler
{
	public EnumMap<Side, FMLEmbeddedChannel> channels;
	public static NetworkHandler instance;

	public NetworkHandler(String channelName)
	{
		channels = NetworkRegistry.INSTANCE.newChannel(channelName, new ChannelHandler(), new PacketHandler());
	}

	public void sendToPlayers(Packet packet, World world, int x, int y, int z, int maxDistance)
	{
		try
		{
			channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
			channels.get(Side.SERVER).writeOutbound(packet);
		}
		catch(Throwable t)
		{
			Log.logger.log(Level.WARN, "sentToPlayers crash", t);
		}
	}

	public void sendToPlayers(PacketBase packet, World world, int x, int y, int z, int maxDistance)
	{
		try
		{
			channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
			channels.get(Side.SERVER).writeOutbound(packet);
		}
		catch(Throwable t)
		{
			Log.logger.log(Level.WARN, "sentToPlayers crash", t);
		}
	}

	public void sendToPlayer(EntityPlayer entityplayer, PacketBase packet)
	{
		try
		{
			channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
			channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(entityplayer);
			channels.get(Side.SERVER).writeOutbound(packet);
		}
		catch(Throwable t)
		{
			String name = entityplayer.getDisplayName();

			if(name == null)
			{
				name = "<no name>";
			}

			Log.logger.log(Level.WARN, "sentToPlayer \"" + name + "\" crash", t);
		}
	}

	public void sendToServer(PacketBase packet)
	{
		try
		{
			channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(OutboundTarget.TOSERVER);
			channels.get(Side.CLIENT).writeOutbound(packet);
		}
		catch(Throwable t)
		{
			Log.logger.log(Level.WARN, "sentToServer crash", t);
		}
	}
}
