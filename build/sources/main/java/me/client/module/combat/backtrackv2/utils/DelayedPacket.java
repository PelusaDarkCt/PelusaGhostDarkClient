package me.client.module.combat.backtrackv2.utils;

import net.minecraft.network.Packet;

public class DelayedPacket {
    private final long time;
    private final Packet<?> pck;
    public DelayedPacket(final Packet<?> packet, final long time) {
        this.pck = packet;
        this.time = time;
    }
    public long getTime() {
        return time;
    }
    public Packet<?> getPacket() {
        return pck;
    }
}
