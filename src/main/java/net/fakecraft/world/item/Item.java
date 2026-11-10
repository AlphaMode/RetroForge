package net.fakecraft.world.item;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.RegistryDelegate;
import net.fakecraft.util.MappedRegistry;
import net.fakecraft.world.level.block.Block;

public class Item {
    public static final MappedRegistry<Item> field_150901_e = GameData.getItemRegistry();
    public RegistryDelegate<Item> delegate = ((cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry)field_150901_e).getDelegate(this, Item.class);;

    // m_11873116
    public static Item func_150898_a(Block block) {
        return null;
    }

    // setStackedByData
    public Item func_77627_a(boolean isStackedByData) {
        return this;
    }

    // setMaxDamage
    public Item func_77656_e(int maxDamage) {
        return this;
    }

    public String getUnlocalizedName(ItemInstance objectToName) {
        return null;
    }

    public String getUnlocalizedName() {
        return null;
    }

    // stacksTo
    public Item func_77625_d(int maxSize) {
        return this;
    }

    // setDescriptionId
    public Item func_77655_b(String name) {
        return this;
    }

    // m_31354541
    public Item func_77637_a(CreativeModeTab tab) {
        return this;
    }
}
