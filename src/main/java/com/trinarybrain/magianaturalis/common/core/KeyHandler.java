package com.trinarybrain.magianaturalis.common.core;

import com.trinarybrain.magianaturalis.common.item.focus.ItemFocusBuild;
import com.trinarybrain.magianaturalis.common.network.PacketHandler;
import com.trinarybrain.magianaturalis.common.network.packet.PacketKeyInput;
import com.trinarybrain.magianaturalis.common.network.packet.PacketPickedBlock;
import com.trinarybrain.magianaturalis.common.util.WorldUtil;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Keyboard;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.ItemWandCasting;

public class KeyHandler {

    public static final KeyBinding MISC_KEY = new KeyBinding("key.magianaturalis.misc", Keyboard.KEY_V, "key.categories.magianaturalis");
    public static final KeyBinding INCREASE_SIZE_KEY = new KeyBinding("key.magianaturalis.size.add", Keyboard.KEY_ADD, "key.categories.magianaturalis");
    public static final KeyBinding DECREASE_SIZE_KEY = new KeyBinding("key.magianaturalis.size.sub", Keyboard.KEY_SUBTRACT, "key.categories.magianaturalis");
    public static final KeyBinding PICK_BLOCK_KEY = new KeyBinding("key.magianaturalis.pickBlock", -98, "key.categories.magianaturalis");

    public KeyHandler() {
        ClientRegistry.registerKeyBinding(INCREASE_SIZE_KEY);
        ClientRegistry.registerKeyBinding(DECREASE_SIZE_KEY);
        ClientRegistry.registerKeyBinding(MISC_KEY);
        ClientRegistry.registerKeyBinding(PICK_BLOCK_KEY);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == Side.SERVER) return;

        if (event.phase == TickEvent.Phase.START) {
            byte id = 0;
            if (DECREASE_SIZE_KEY.isPressed()) {
                id = 2;
            }
            else if (INCREASE_SIZE_KEY.isPressed()) {
                id = 3;
            }
            else if (GuiScreen.isCtrlKeyDown() && MISC_KEY.isPressed()) {
                id = 4; //Meta
            }
            else if (MISC_KEY.isPressed()) {
                id = 5; //Shape
            }
            else if (PICK_BLOCK_KEY.isPressed()) {
                EntityPlayer player = event.player;
                if (player != null && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemWandCasting) {
                    ItemWandCasting wand = (ItemWandCasting) player.getCurrentEquippedItem().getItem();
                    ItemFocusBasic focus = wand.getFocus(player.getCurrentEquippedItem());
                    if (focus instanceof ItemFocusBuild) {
                        MovingObjectPosition target = WorldUtil.getMovingObjectPositionFromPlayer(event.player.worldObj, event.player, ItemFocusBuild.reachDistance, true);
                        if (target != null) {
                            if (target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                                int x = target.blockX;
                                int y = target.blockY;
                                int z = target.blockZ;

                                Block block = event.player.worldObj.getBlock(x, y, z);
                                byte meta = (byte) event.player.worldObj.getBlockMetadata(x, y, z);
                                if (block == Blocks.double_plant) meta = (byte) block.getDamageValue(event.player.worldObj, x, y, z);
                                if (meta < 0 || meta > 15) meta = 0;

                                PacketHandler.network.sendToServer(new PacketPickedBlock.PickedBlockMessage(Block.getIdFromBlock(block), meta));
                            }
                        }
                    }
                }
            }

            if (FMLClientHandler.instance().getClient().inGameHasFocus && id > 0) {
                EntityPlayer player = event.player;
                if (player != null && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemWandCasting) {
                    ItemWandCasting wand = (ItemWandCasting) player.getCurrentEquippedItem().getItem();
                    ItemFocusBasic focus = wand.getFocus(player.getCurrentEquippedItem());
                    if (focus instanceof ItemFocusBuild) {
                        PacketHandler.network.sendToServer(new PacketKeyInput.KeyInputMessage(id));
                    }
                }
            }
        }
    }

}
