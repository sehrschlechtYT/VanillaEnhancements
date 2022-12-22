package yt.sehrschlecht.vanillaenhancements.modules;

import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.utils.ModuleUtils;

import java.util.logging.Logger;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
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

    public abstract @NotNull String getKey(); //ToDo maybe use SimpleClassName as key

    /**
     * Called before onEnable() and onDisable()
     * Used to execute code before the module is enabled/disabled
     */
    public void initialize() {

    }

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
