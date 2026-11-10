package net.fakecraft.world.level.block;

import cpw.mods.fml.common.registry.GameData;
import net.fakecraft.util.MappedRegistry;
import net.fakecraft.world.item.CreativeModeTab;
import net.fakecraft.world.level.material.Material;

public class Block {
    public static final MappedRegistry<Block> field_149771_c = GameData.getBlockRegistry();
    public final cpw.mods.fml.common.registry.RegistryDelegate<Block> delegate =
            ((cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry)field_149771_c).getDelegate(this, Block.class);

    protected Block(Material material) {
//        this.material = material;
        this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
//        this.f_88284298 = this.isSolidRender();
//        this.f_38518051 = this.isSolidRender() ? 255 : 0;
//        this.f_74640912 = !material.blocksLight();
    }

    // setShape
    public final void func_149676_a(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {

    }

    public Block func_149647_a(CreativeModeTab tab) {
        return this;
    }

    // setDestroyTime
    public Block func_149711_c(float strength) {
        return this;
    }

    // setDescriptionId
    public Block func_149663_c(String name) {
        return this;
    }

    public String getUnlocalizedName() {
        return null;
    }
}
