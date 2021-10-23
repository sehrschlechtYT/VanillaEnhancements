package yt.sehrschlecht.vanillaenhancements.modules;

import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.ticking.TickService;

import java.util.logging.Logger;

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

    public VEModule getInstance() {
        return instance;
    }
    public VanillaEnhancements getPlugin() {
        return VanillaEnhancements.getPlugin();
    }
    public Logger getLogger() {
        return getPlugin().getLogger();
    }
    public boolean shouldEnable() {
        return true;
    }
}
