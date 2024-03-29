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
    private static List<VEModule> enabledModules = new ArrayList<>();
    private static List<VEModule> registeredModules = new ArrayList<>();
    private static Logger logger = VanillaEnhancements.getPlugin().getLogger();

    public static boolean registerModule(VEModule module) {
        registeredModules.add(module);
        if(!Config.isModuleEnabled(module)) return false;
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
            logger.log(Level.SEVERE, "The module " + module.getName() + " couldn't be loaded due to an error.");
            return false;
        }
    }

    public static void unregisterAllModules() {
        enabledModules.clear();
        registeredModules.clear();
    }

    public static boolean isEnabled(NamespacedKey moduleKey) {
        if(getModule(moduleKey) == null) return false;
        return enabledModules.contains(getModule(moduleKey));
    }

    @Nullable
    public static VEModule getModule(NamespacedKey key) {
        Optional<VEModule> moduleOptional = enabledModules.stream().filter(m -> m.getModuleKey().equals(key)).findFirst();
        return moduleOptional.orElse(null);
    }

    @NotNull
    public static List<VEModule> getEnabledModules() {
        return enabledModules;
    }

    public static List<VEModule> getRegisteredModules() {
        return registeredModules;
    }
}
