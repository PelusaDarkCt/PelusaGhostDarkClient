package me.client.module.values;

public class UpdateEvent {
    private final float partialTicks;

    public UpdateEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
