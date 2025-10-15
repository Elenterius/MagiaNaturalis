package com.github.elenterius.magianaturalis.util;

import com.github.elenterius.magianaturalis.MagiaNaturalis;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import static net.minecraftforge.common.util.Constants.NBT;

public final class BuilderFocusUtil {

    public static final String MOD_TAG_KEY = MagiaNaturalis.MOD_ID;
    public static final String META_TAG_KEY = "meta";
    public static final String SHAPE_TAG_KEY = "shape";
    public static final String SIZE_TAG_KEY = "size";

    public static Meta getMeta(ItemStack stack) {
        if (stack == null) return Meta.NONE;
        NBTTagList nbttaglist = NBTUtil.openNbtData(stack).getTagList(MOD_TAG_KEY, NBT.TAG_COMPOUND);
        if (nbttaglist == null) return Meta.NONE;

        int i = nbttaglist.getCompoundTagAt(0).getByte(META_TAG_KEY);
        return i == 1 ? Meta.UNIFORM : Meta.NONE;
    }

    public static boolean setMeta(ItemStack stack, Meta meta) {
        if (stack == null) return false;

        NBTTagCompound data = NBTUtil.openNbtData(stack);
        NBTTagList nbttaglist = data.getTagList(MOD_TAG_KEY, NBT.TAG_COMPOUND);
        NBTTagCompound tempData;

        if (nbttaglist == null) {
            nbttaglist = new NBTTagList();
            tempData = new NBTTagCompound();
        }
        else {
            tempData = nbttaglist.getCompoundTagAt(0);
        }

        tempData.setByte(META_TAG_KEY, (byte) meta.ordinal());
        nbttaglist.appendTag(tempData);

        if (nbttaglist.tagCount() > 0) {
            data.setTag(MOD_TAG_KEY, nbttaglist);
            return true;
        }

        return false;
    }

    public static Shape getShape(ItemStack stack) {
        if (stack == null) return Shape.NONE;

        NBTTagList nbttaglist = NBTUtil.openNbtData(stack).getTagList(MOD_TAG_KEY, NBT.TAG_COMPOUND);
        if (nbttaglist == null) return Shape.NONE;

        return getShapeByID(nbttaglist.getCompoundTagAt(0).getByte(SHAPE_TAG_KEY));
    }

    public static boolean setShape(ItemStack stack, Shape shape) {
        if (stack == null) return false;

        NBTTagCompound data = NBTUtil.openNbtData(stack);
        NBTTagList nbttaglist = data.getTagList(MOD_TAG_KEY, NBT.TAG_COMPOUND);
        NBTTagCompound tempData;

        if (nbttaglist == null) {
            nbttaglist = new NBTTagList();
            tempData = new NBTTagCompound();
        }
        else {
            tempData = nbttaglist.getCompoundTagAt(0);
        }

        tempData.setByte(SHAPE_TAG_KEY, (byte) shape.ordinal());
        nbttaglist.appendTag(tempData);

        if (nbttaglist.tagCount() > 0) {
            stack.setTagInfo(MOD_TAG_KEY, nbttaglist);
            return true;
        }

        return false;
    }

    public static int getSize(ItemStack stack) {
        if (stack == null) return 1;

        NBTTagList nbttaglist = NBTUtil.openNbtData(stack).getTagList(MOD_TAG_KEY, NBT.TAG_COMPOUND);
        if (nbttaglist == null) return 1;

        return nbttaglist.getCompoundTagAt(0).getByte(SIZE_TAG_KEY);
    }

    public static boolean setSize(ItemStack stack, int size) {
        if (stack == null) return false;

        NBTTagCompound data = NBTUtil.openNbtData(stack);
        NBTTagList list = data.getTagList(MOD_TAG_KEY, NBT.TAG_COMPOUND);
        NBTTagCompound tempData;

        if (list == null) {
            list = new NBTTagList();
            tempData = new NBTTagCompound();
        }
        else {
            tempData = list.getCompoundTagAt(0);
        }

        tempData.setByte(SIZE_TAG_KEY, (byte) size);
        list.appendTag(tempData);

        if (list.tagCount() > 0) {
            stack.setTagInfo(MOD_TAG_KEY, list);
            return true;
        }

        return false;
    }

    public static boolean setPickedBlock(ItemStack stack, Block block, int metadata) {
        if (stack == null) return false;

        int bid = Block.getIdFromBlock(block);
        if (metadata < 0 || metadata > 15) metadata = 0;

        NBTTagCompound data = NBTUtil.openNbtData(stack);
        NBTTagList nbttaglist = data.getTagList(MOD_TAG_KEY, NBT.TAG_COMPOUND);
        NBTTagCompound nbttagcompound;

        if (nbttaglist == null) {
            nbttaglist = new NBTTagList();
            nbttagcompound = new NBTTagCompound();
        }
        else {
            nbttagcompound = nbttaglist.getCompoundTagAt(0);
        }

        nbttagcompound.setInteger("bid", bid);
        nbttagcompound.setByte("bdata", (byte) metadata);

        nbttaglist.appendTag(nbttagcompound);

        if (nbttaglist.tagCount() > 0) {

            stack.setTagInfo(MOD_TAG_KEY, nbttaglist);
            return true;

        }
        return false;
    }

    public static int[] getPickedBlock(ItemStack stack) {
        int[] i = {0, 0};
        if (stack == null) return i;

        NBTTagList nbttaglist = NBTUtil.openNbtData(stack).getTagList(MOD_TAG_KEY, NBT.TAG_COMPOUND);
        if (nbttaglist == null) {
            return i;
        }
        else {
            i[0] = nbttaglist.getCompoundTagAt(0).getInteger("bid");
            i[1] = nbttaglist.getCompoundTagAt(0).getByte("bdata");
            return i;
        }
    }

    public enum Meta {
        NONE, UNIFORM;

        public String toString() {
            switch (this) {
                case NONE:
                    return Platform.translate("enum.magianaturalis.none");
                case UNIFORM:
                    return Platform.translate("enum.magianaturalis.meta.uniform");
                default:
                    break;
            }
            return Platform.translate("enum.magianaturalis.unknown");
        }
    }

    public enum Shape {
        NONE, PLANE, CUBE, PLANE_EXTEND, SPHERE;

        public String toString() {
            switch (this) {
                case CUBE:
                    return Platform.translate("enum.magianaturalis.shape.cube");
                case NONE:
                    return Platform.translate("enum.magianaturalis.none");
                case PLANE:
                    return Platform.translate("enum.magianaturalis.shape.plane");
                case PLANE_EXTEND:
                    return Platform.translate("enum.magianaturalis.shape.plane.extend");
                case SPHERE:
                    return Platform.translate("enum.magianaturalis.shape.sphere");
                default:
                    return Platform.translate("enum.magianaturalis.unknown");
            }
        }
    }

    public static Shape getShapeByID(int i) {
        switch (i) {
            case 1:
                return Shape.PLANE;
            case 2:
                return Shape.CUBE;
            case 3:
                return Shape.PLANE_EXTEND;
            case 4:
                return Shape.SPHERE;
            case 0:
            default:
                return Shape.NONE;
        }
    }

}
