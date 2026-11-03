package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;

public class ItemStack {
    public int stackSize;
    
    public ItemStack(Item item, int count, int auxValue) {}

    public ItemStack(Block block, int count, int auxValue) {}

    public ItemStack copy() {
        return null;
    }

    public int getMaxStackSize() {
        return 64;
    }

    public void setTagCompound(NBTTagCompound nbttag) {

    }

    public Item getItem() {
        return null;
    }
}
