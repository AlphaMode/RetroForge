package net.fakecraft.world.item;

import net.fakecraft.world.level.block.Block;
import com.mojang.nbt.NBTTagCompound;

public class ItemInstance {
    public int stackSize;
    
    public ItemInstance(Item item, int count, int auxValue) {}

    public ItemInstance(Block block, int count, int auxValue) {}

    public ItemInstance(Block block) {}

    public ItemInstance(Item item) {}

    public ItemInstance copy() {
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

    public int getAuxValue() {
        return -1;
    }
}
