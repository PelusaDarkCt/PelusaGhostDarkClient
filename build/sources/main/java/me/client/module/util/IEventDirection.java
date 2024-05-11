package me.client.module.util;

import me.client.module.util.*;

public interface IEventDirection {

    EventDirection getDirection();

    default boolean isIncoming() {
        return getDirection() == EventDirection.INCOMING;
    }

    default boolean isOutgoing() {
        return getDirection() == EventDirection.OUTGOING;
    }

}
