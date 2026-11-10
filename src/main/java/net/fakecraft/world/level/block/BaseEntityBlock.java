package net.fakecraft.world.level.block;

import net.fakecraft.world.level.material.Material;

public abstract class BaseEntityBlock extends Block {
    protected BaseEntityBlock(Material material) {
        super(material);
//        this.isEntityTile = true;
    }
}
