package net.fakecraft.network;

import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.fakecraft.util.ChatComponentText;

public class NetworkManager {
    public Object getNetHandler() {
        return null;
    }

    public boolean isLocalChannel() {
        return true;
    }

    public Channel channel() {
        return null;
    }

    public void setConnectionState(EnumConnectionState enumConnectionState) {
    }

    public void closeChannel(ChatComponentText chatcomponenttext) {
    }

    public void scheduleOutboundPacket(Packet packet) {
    }

    public void scheduleOutboundPacket(Packet packet, GenericFutureListener<Future<?>> genericFutureListener) {
    }
}
