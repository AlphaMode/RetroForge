package net.minecraft.network;

import java.io.IOException;

public abstract class Packet {
    public void readPacketData(PacketBuffer packetbuffer) throws IOException {};

    public void writePacketData(PacketBuffer packetbuffer) throws IOException {};

    public void processPacket(INetHandler inethandler) {};
}
