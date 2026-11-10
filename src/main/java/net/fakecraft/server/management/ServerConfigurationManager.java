package net.fakecraft.server.management;

import net.fakecraft.entity.player.EntityPlayerMP;
import com.mojang.nbt.NBTTagCompound;
import net.fakecraft.network.NetHandlerPlayServer;
import net.fakecraft.network.NetworkManager;

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
