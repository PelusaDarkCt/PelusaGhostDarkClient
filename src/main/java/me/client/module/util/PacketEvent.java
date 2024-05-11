package me.client.module.util;

import me.client.module.util.*;
import net.minecraft.network.Packet;

public class PacketEvent extends CancellableEvent implements IEventDirection {
    private final Packet<?> packet;

    private final EventDirection direction;

    public PacketEvent(Packet<?> packet, EventDirection direction) {
        this.packet = packet;
        this.direction = direction;
    }

    public <T extends Packet<?>> T getPacket() {
        return (T)this.packet;
    }

    public EventDirection getDirection() {
        return this.direction;
    }
}
