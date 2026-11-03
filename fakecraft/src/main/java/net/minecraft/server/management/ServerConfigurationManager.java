package net.minecraft.server.management;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;

public class ServerConfigurationManager {
    public Object getServerInstance() {
        return null;
    }

    public NBTTagCompound getPlayerNBT(EntityPlayerMP player) {
        return null;
    }

    public void initializeConnectionToPlayer(NetworkManager manager, EntityPlayerMP player, NetHandlerPlayServer serverHandler) {
    }
}
