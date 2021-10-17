package yt.sehrschlecht.vanillaenhancements.ticking;

import org.bukkit.NamespacedKey;

import java.util.function.Consumer;

public class TickService {
    private final long period;
    private final Runnable runnable;
    private final NamespacedKey key;

    public TickService(long period, Runnable runnable, NamespacedKey key) {
        this.period = period;
        this.runnable = runnable;
        this.key = key;
    }

    public long getPeriod() {
        return period;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public NamespacedKey getKey() {
        return key;
    }
}
