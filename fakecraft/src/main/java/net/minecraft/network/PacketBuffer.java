package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.io.IOException;

public class PacketBuffer {
    public PacketBuffer(ByteBuf buf) {}

    public void writeItemStackToBuffer(ItemStack stack) throws IOException {
    }

    public ItemStack readItemStackFromBuffer() throws IOException {
        return null;
    }

    public void writeNBTTagCompoundToBuffer(NBTTagCompound tag) throws IOException {
    }

    public NBTTagCompound readNBTTagCompoundFromBuffer() throws IOException {
        return null;
    }
}
