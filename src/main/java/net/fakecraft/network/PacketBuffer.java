package net.fakecraft.network;

import io.netty.buffer.ByteBuf;
import net.fakecraft.world.item.ItemInstance;
import com.mojang.nbt.NBTTagCompound;

import java.io.IOException;

public class PacketBuffer {
    public PacketBuffer(ByteBuf buf) {}

    public void writeItemStackToBuffer(ItemInstance stack) throws IOException {
    }

    public ItemInstance readItemStackFromBuffer() throws IOException {
        return null;
    }

    public void writeNBTTagCompoundToBuffer(NBTTagCompound tag) throws IOException {
    }

    public NBTTagCompound readNBTTagCompoundFromBuffer() throws IOException {
        return null;
    }
}
