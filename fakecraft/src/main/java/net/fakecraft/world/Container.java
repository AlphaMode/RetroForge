package net.fakecraft.world;

import net.fakecraft.world.item.ItemInstance;

public interface Container {
    int getContainerSize();

    ItemInstance getItem(int slot);
}
