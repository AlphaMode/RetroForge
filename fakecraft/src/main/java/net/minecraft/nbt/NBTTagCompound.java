package net.minecraft.nbt;

public class NBTTagCompound extends NBTBase {
    public int getInteger(String dimension) {
        return -1;
    }

    public void setString(String modId, String modId1) {
    }

    public void setTag(String modList, NBTBase list) {
    }

    public void setInteger(String v, Integer value) {
    }

    public void setIntArray(String blockedItemIds, int[] blockedIds) {
    }

    public boolean hasKey(String modList) {
        return false;
    }

    public boolean hasKey(String modList, int b) {
        return false;
    }

    public NBTTagList getTagList(String modList, int b) {
        return null;
    }

    public String getString(String modId) {
        return null;
    }

    public int[] getIntArray(String blockedItemIds) {
        return null;
    }

    public NBTTagCompound getCompoundTag(String modId) {
        return null;
    }
}
