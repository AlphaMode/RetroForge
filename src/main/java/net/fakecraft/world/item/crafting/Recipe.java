package net.fakecraft.world.item.crafting;

import net.fakecraft.world.inventory.CraftingContainer;
import net.fakecraft.world.item.ItemInstance;
import net.fakecraft.world.level.Level;

public interface Recipe {
    boolean matches(CraftingContainer craftSlots, Level level);

    ItemInstance assemble(CraftingContainer craftSlots);

    int size();

    ItemInstance getResultItem();
}
