package net.fakecraft.world.inventory;

import net.fakecraft.world.Container;
import net.fakecraft.world.item.ItemInstance;

public class CraftingContainer implements Container {
    private ItemInstance[] items;

    @Override
    public int getContainerSize() {
        return -1;
    }

    @Override
    public ItemInstance getItem(int slot) {
        return slot >= this.getContainerSize() ? null : this.items[slot];
    }

    public ItemInstance getItem(int x, int y) {
        return null;
    }
}
