package net.minecraft.item.crafting;

import net.minecraft.item.ItemStack;

import java.util.List;

public class CraftingManager {
    public static CraftingManager getInstance() {
        return null;
    }

    public IRecipe addRecipe(ItemStack output, Object[] params) {
        return null;
    }

    public void addShapelessRecipe(ItemStack output, Object[] params) {
    }

    public <I> List<I> getRecipeList() {
        return List.of();
    }
}
