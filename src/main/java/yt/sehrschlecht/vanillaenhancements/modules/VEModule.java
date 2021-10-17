package yt.sehrschlecht.vanillaenhancements.modules;

import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.ticking.TickService;

public abstract class VEModule implements Listener {
    private static VEModule instance;

    public VEModule() {
        instance = this;
    }

    @NotNull
    public abstract String getName();
    @NotNull
    public abstract NamespacedKey getKey();
    public abstract void onEnable();
    public abstract void onDisable();
    @Nullable
    public abstract TickService getTickService();

    public static VEModule getInstance() {
        return instance;
    }
}
