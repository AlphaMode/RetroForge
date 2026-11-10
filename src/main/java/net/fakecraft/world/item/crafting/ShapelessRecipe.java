package net.fakecraft.world.item.crafting;

import net.fakecraft.world.inventory.CraftingContainer;
import net.fakecraft.world.item.ItemInstance;
import net.fakecraft.world.level.Level;

import java.util.List;

public class ShapelessRecipe implements Recipe {
    private final ItemInstance result;
    public final List ingredients;

    public ShapelessRecipe(ItemInstance result, List ingredients) {
        this.result = result;
        this.ingredients = ingredients;
    }

    @Override
    public boolean matches(CraftingContainer craftSlots, Level level) {
        return false;
    }

    @Override
    public ItemInstance assemble(CraftingContainer craftSlots) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public ItemInstance getResultItem() {
        return null;
    }
}
