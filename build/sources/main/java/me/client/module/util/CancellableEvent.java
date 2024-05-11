package me.client.module.util;

public class CancellableEvent {
    private boolean cancelled;

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void cancel() {
        setCancelled(false);
    }
}


/*TODO: Sabes porque la gallina cruso la calle¿? Pues ni yo se jaja *convulsiona*
* Gracias a Raben b++, Me estare guiando para programar el blink :)
* */