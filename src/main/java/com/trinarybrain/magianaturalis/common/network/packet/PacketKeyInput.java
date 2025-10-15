package com.trinarybrain.magianaturalis.common.network.packet;

import com.trinarybrain.magianaturalis.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.item.focus.ItemFocusBuild;
import com.trinarybrain.magianaturalis.common.network.packet.PacketKeyInput.KeyInputMessage;
import com.trinarybrain.magianaturalis.common.util.FocusBuildHelper;
import com.trinarybrain.magianaturalis.common.util.FocusBuildHelper.Meta;
import com.trinarybrain.magianaturalis.common.util.FocusBuildHelper.Shape;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.ItemWandCasting;

public class PacketKeyInput implements IMessageHandler<KeyInputMessage, IMessage> {

    public PacketKeyInput() {
        super();
    }

    @Override
    public IMessage onMessage(KeyInputMessage message, MessageContext ctx) {
        if (ctx.side.isServer()) {
            ItemStack stack = ctx.getServerHandler().playerEntity.inventory.getCurrentItem();
            if (stack != null && stack.getItem() instanceof ItemWandCasting) {
                ItemWandCasting wand = (ItemWandCasting) stack.getItem();
                ItemFocusBasic focus = wand.getFocus(stack);
                if (focus instanceof ItemFocusBuild) {
                    ItemStack focusStack = wand.getFocusItem(stack);

                    switch (message.keyID) {
                        case 2:
                            int i = FocusBuildHelper.getSize(focusStack) - 1;
                            FocusBuildHelper.setSize(focusStack, i < 1 ? focus.getMaxAreaSize(focusStack) : i);
                            break;
                        case 3:
                            int j = FocusBuildHelper.getSize(focusStack) + 1;
                            FocusBuildHelper.setSize(focusStack, j > focus.getMaxAreaSize(focusStack) ? 1 : j);
                            break;
                        case 4:
                            int k = FocusBuildHelper.getMeta(focusStack).ordinal();
                            FocusBuildHelper.setMeta(focusStack, k == 0 ? Meta.UNIFORM : Meta.NONE);
                            break;
                        case 5:
                            int l = FocusBuildHelper.getShape(focusStack).ordinal() + 1;
                            FocusBuildHelper.setShape(focusStack, l > 3 ? Shape.PLANE : FocusBuildHelper.getShapeByID(l));
                            break;
                        case 51:
                            int q = FocusBuildHelper.getShape(focusStack).ordinal() - 1;
                            FocusBuildHelper.setShape(focusStack, q < 1 ? Shape.PLANE_EXTEND : FocusBuildHelper.getShapeByID(q));
                            break;
                        default:
                            MagiaNaturalis.LOGGER.error("Invalid KEY ID recieved.");
                    }
                }
            }
        }

        return null;
    }

    public static class KeyInputMessage implements IMessage {
        private byte keyID;

        @SuppressWarnings("unused")
        public KeyInputMessage() {
            super();
        }

        public KeyInputMessage(byte customKeyID) {
            keyID = customKeyID;
        }

        @Override
        public void fromBytes(ByteBuf buf) {
            keyID = buf.readByte();
        }

        @Override
        public void toBytes(ByteBuf buf) {
            buf.writeByte(keyID);
        }
    }

}
