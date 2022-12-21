package yt.sehrschlecht.vanillaenhancements.modules;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.ticking.TickServiceExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModuleRegistry {
    private List<VEModule> enabledModules = new ArrayList<>();
    private List<VEModule> registeredModules = new ArrayList<>();
    private Logger logger = VanillaEnhancements.getPlugin().getLogger();

    public boolean registerModule(VEModule module) {
        registeredModules.add(module);
        if(!Config.getInstance().isModuleEnabled(module)) return false;
        try {
            if(!module.shouldEnable()) return false;
            module.onEnable();
            enabledModules.add(module);
            Bukkit.getPluginManager().registerEvents(module, VanillaEnhancements.getPlugin());
            if(module.getTickService() != null) {
                TickServiceExecutor.addTickService(module.getTickService());
            }
            return true;
        } catch (Throwable throwable) {
            logger.log(Level.SEVERE, "The module " + module.getName() + " couldn't be loaded due to an error:");
            logger.log(Level.SEVERE, throwable.getMessage());
            return false;
        }
    }

    public void unregisterAllModules() {
        enabledModules.clear();
        registeredModules.clear();
    }

    public boolean isEnabled(NamespacedKey moduleKey) {
        if(getModule(moduleKey) == null) return false;
        return enabledModules.contains(getModule(moduleKey));
    }

    @Nullable
    public VEModule getModule(NamespacedKey key) {
        Optional<VEModule> moduleOptional = enabledModules.stream().filter(m -> m.getModuleKey().equals(key)).findFirst();
        return moduleOptional.orElse(null);
    }

    @NotNull
    public List<VEModule> getEnabledModules() {
        return enabledModules;
    }

    public List<VEModule> getRegisteredModules() {
        return registeredModules;
    }
}
