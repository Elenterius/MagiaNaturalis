package com.github.elenterius.magianaturalis.network.packet;

import com.github.elenterius.magianaturalis.MagiaNaturalis;
import com.github.elenterius.magianaturalis.item.focus.BuilderFocusItem;
import com.github.elenterius.magianaturalis.network.packet.PacketKeyInput.KeyInputMessage;
import com.github.elenterius.magianaturalis.util.BuilderFocusUtil;
import com.github.elenterius.magianaturalis.util.BuilderFocusUtil.Meta;
import com.github.elenterius.magianaturalis.util.BuilderFocusUtil.Shape;
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
                if (focus instanceof BuilderFocusItem) {
                    ItemStack focusStack = wand.getFocusItem(stack);

                    switch (message.keyId) {
                        case 2:
                            int i = BuilderFocusUtil.getSize(focusStack) - 1;
                            BuilderFocusUtil.setSize(focusStack, i < 1 ? focus.getMaxAreaSize(focusStack) : i);
                            break;
                        case 3:
                            int j = BuilderFocusUtil.getSize(focusStack) + 1;
                            BuilderFocusUtil.setSize(focusStack, j > focus.getMaxAreaSize(focusStack) ? 1 : j);
                            break;
                        case 4:
                            int k = BuilderFocusUtil.getMeta(focusStack).ordinal();
                            BuilderFocusUtil.setMeta(focusStack, k == 0 ? Meta.UNIFORM : Meta.NONE);
                            break;
                        case 5:
                            int l = BuilderFocusUtil.getShape(focusStack).ordinal() + 1;
                            BuilderFocusUtil.setShape(focusStack, l > 3 ? Shape.PLANE : BuilderFocusUtil.getShapeByID(l));
                            break;
                        case 51:
                            int q = BuilderFocusUtil.getShape(focusStack).ordinal() - 1;
                            BuilderFocusUtil.setShape(focusStack, q < 1 ? Shape.PLANE_EXTEND : BuilderFocusUtil.getShapeByID(q));
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
        private byte keyId;

        @SuppressWarnings("unused")
        public KeyInputMessage() {
            super();
        }

        public KeyInputMessage(byte customKeyID) {
            keyId = customKeyID;
        }

        @Override
        public void fromBytes(ByteBuf buf) {
            keyId = buf.readByte();
        }

        @Override
        public void toBytes(ByteBuf buf) {
            buf.writeByte(keyId);
        }
    }

}
