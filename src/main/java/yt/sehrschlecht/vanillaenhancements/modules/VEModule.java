package yt.sehrschlecht.vanillaenhancements.modules;

import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.utils.ModuleUtils;

import java.util.logging.Logger;

public abstract class VEModule implements Listener {
    private static VEModule instance;

    public VEModule() {
        instance = this;
    }

    @NotNull
    public String getName() {
        return ModuleUtils.getNameFromKey(getModuleKey());
    }

    @NotNull
    public NamespacedKey getModuleKey() {
        return new NamespacedKey(VanillaEnhancements.getPlugin(), getKey());
    }

    public abstract @NotNull String getKey();

    public void onEnable() {

    }

    public void onDisable() {

    }

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
