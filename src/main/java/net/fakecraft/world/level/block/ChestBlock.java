package net.fakecraft.world.level.block;

import net.fakecraft.world.level.material.Material;

public class ChestBlock extends BaseEntityBlock {
    protected ChestBlock(int i) {
        super(Material.field_151575_d);
//        this.f_58500326 = i;
//        this.setCreativeModeTab(CreativeModeTab.TAB_DECORATIONS);
        this.func_149676_a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
    }
}
