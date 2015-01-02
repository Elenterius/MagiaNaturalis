package trinarybrain.magia.naturalis.common.network;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.ItemWandCasting;
import trinarybrain.magia.naturalis.common.MagiaNaturalis;
import trinarybrain.magia.naturalis.common.core.Log;
import trinarybrain.magia.naturalis.common.item.focus.ItemFocusBuild;
import trinarybrain.magia.naturalis.common.network.packet.PacketBase;
import trinarybrain.magia.naturalis.common.network.packet.PacketID;
import trinarybrain.magia.naturalis.common.network.packet.PacketKey;
import trinarybrain.magia.naturalis.common.network.packet.PacketPickedBlock;
import trinarybrain.magia.naturalis.common.util.FocusBuildHelper;
import trinarybrain.magia.naturalis.common.util.FocusBuildHelper.Meta;
import trinarybrain.magia.naturalis.common.util.FocusBuildHelper.Shape;
import cpw.mods.fml.common.network.NetworkRegistry;

@Sharable
public class PacketHandler extends SimpleChannelInboundHandler<PacketBase>
{
	@Override
	protected void channelRead0(ChannelHandlerContext context, PacketBase packet) throws Exception
	{
		try
		{
			INetHandler netHandler = context.channel().attr(NetworkRegistry.NET_HANDLER).get();
			EntityPlayer player = MagiaNaturalis.proxy.getPlayerFromNetHandler(netHandler);

			int packetID = packet.getPacketID();

			switch(packetID)
			{
			case PacketID.PACKET_KEY:
			{
				this.handleCustomKeyID(player, (PacketKey) packet);
				break;
			}

			case PacketID.PACKET_PICKED_BLOCK:
			{
				this.handlePickedBlock(player, (PacketPickedBlock) packet);
				break;
			}

			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private void handlePickedBlock(EntityPlayer player, PacketPickedBlock packet)
	{
		ItemStack stack = player.inventory.getCurrentItem();
		if(stack != null && stack.getItem() instanceof ItemWandCasting)
		{
			ItemWandCasting wand = (ItemWandCasting) stack.getItem();
			ItemFocusBasic focus = wand.getFocus(stack);
			if(focus != null && focus instanceof ItemFocusBuild)
			{
				Block block = Block.getBlockById(packet.blockID);
				int meta = packet.meta;

				if(block != null && meta >= 0 && meta <= 15)
					FocusBuildHelper.setpickedBlock(wand.getFocusItem(stack), block, meta);
			}
		}
	}

	private void handleCustomKeyID(EntityPlayer player, PacketKey packet) throws IOException
	{
		ItemStack stack = player.inventory.getCurrentItem();
		if(stack != null && stack.getItem() instanceof ItemWandCasting)
		{
			ItemWandCasting wand = (ItemWandCasting) stack.getItem();
			ItemFocusBasic focus = wand.getFocus(stack);
			if(focus != null && focus instanceof ItemFocusBuild)
			{
				ItemStack focusStack = wand.getFocusItem(stack);

				switch(packet.key)
				{
				case 2:
					int i = FocusBuildHelper.getSize(focusStack) - 1;
					FocusBuildHelper.setSize(focusStack, i < 1 ? ItemFocusBuild.maxSize : i);
					break;
				case 3:
					int j = FocusBuildHelper.getSize(focusStack) + 1;
					FocusBuildHelper.setSize(focusStack, j > ItemFocusBuild.maxSize ? 1 : j);
					break;
				case 4:
					int k = FocusBuildHelper.getMeta(focusStack).ordinal();
					FocusBuildHelper.setMeta(focusStack, k == 0 ? Meta.UNIFORM : Meta.NONE);
					break;
				case 5:
					int l = FocusBuildHelper.getShape(focusStack).ordinal() + 1;
					FocusBuildHelper.setShape(focusStack, l > 4 ? Shape.PLANE : FocusBuildHelper.getShapeByID(l));
					break;
				case 51:
					int q = FocusBuildHelper.getShape(focusStack).ordinal() - 1;
					FocusBuildHelper.setShape(focusStack, q < 1 ? Shape.SPHERE : FocusBuildHelper.getShapeByID(q));
					break;
				default:
					Log.logger.error("Invalid KEY ID revieved.");
				}
			}
		}
	}
}
