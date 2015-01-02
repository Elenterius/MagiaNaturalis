package trinarybrain.magia.naturalis.common.core;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Keyboard;

import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.ItemWandCasting;
import trinarybrain.magia.naturalis.common.item.focus.ItemFocusBuild;
import trinarybrain.magia.naturalis.common.network.NetworkHandler;
import trinarybrain.magia.naturalis.common.network.packet.PacketID;
import trinarybrain.magia.naturalis.common.network.packet.PacketKey;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;

public class KeyHandler
{
	public KeyBinding keyMultSize = new KeyBinding("key.magianaturalis:size.add", Keyboard.KEY_ADD, "key.categories.magianaturalis");
	public KeyBinding keySubSize = new KeyBinding("key.magianaturalis:size.sub", Keyboard.KEY_SUBTRACT, "key.categories.magianaturalis");
	public KeyBinding keyMeta = new KeyBinding("key.magianaturalis:meta", Keyboard.KEY_DIVIDE, "key.categories.magianaturalis");
	public KeyBinding keyShape = new KeyBinding("key.magianaturalis:shape", Keyboard.KEY_MULTIPLY, "key.categories.magianaturalis");

	public KeyHandler()
	{
		ClientRegistry.registerKeyBinding(this.keyMultSize);
		ClientRegistry.registerKeyBinding(this.keySubSize);
		ClientRegistry.registerKeyBinding(this.keyMeta);
		ClientRegistry.registerKeyBinding(this.keyShape);
	}

	@SubscribeEvent
	public void playerTick(TickEvent.PlayerTickEvent event)
	{
		if(event.side == Side.SERVER) return;
		
		if(event.phase == TickEvent.Phase.START)
		{
			int id = 0;
			if(this.keySubSize.isPressed())
			{
				id = 2;
			}
			else if(this.keyMultSize.isPressed())
			{
				id = 3;
			}
			else if(this.keyMeta.isPressed())
			{
				id = 4;
			}
			else if(this.keyShape.isPressed())
			{
				id = 5;
				if(Minecraft.getMinecraft().currentScreen.isCtrlKeyDown()) id = 51;
			}

			if(FMLClientHandler.instance().getClient().inGameHasFocus && id != 0)
			{
				EntityPlayer player = event.player;
				if(player != null && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemWandCasting)
				{
					ItemWandCasting wand = (ItemWandCasting) player.getCurrentEquippedItem().getItem();
					ItemFocusBasic focus = wand.getFocus(player.getCurrentEquippedItem());
					if(focus != null && focus instanceof ItemFocusBuild)
						NetworkHandler.instance.sendToServer(new PacketKey(PacketID.PACKET_KEY, id));
				}
			}
		}
	}
}
