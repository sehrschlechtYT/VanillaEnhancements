package yt.sehrschlecht.vanillaenhancements.modules;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.ticking.Tick;
import yt.sehrschlecht.vanillaenhancements.ticking.TickServiceExecutor;
import yt.sehrschlecht.vanillaenhancements.utils.debugging.Debug;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class ModuleRegistry {
    private List<VEModule> enabledModules = new ArrayList<>();
    private List<VEModule> registeredModules = new ArrayList<>();
    private Logger logger = VanillaEnhancements.getPlugin().getLogger();

    public boolean registerModule(VEModule module) {
        boolean inbuilt = (module.getModuleKey().getNamespace().equalsIgnoreCase(VanillaEnhancements.getPlugin().getName()));
        Debug.MODULES.log("Registering {} module {}...", inbuilt ? "inbuilt" : "external", inbuilt ? module.getModuleKey().getKey() : module.getModuleKey());
        registeredModules.add(module);
        module.initialize();
        if(!Config.getInstance().isModuleEnabled(module)) {
            Debug.MODULES.log("Module {} is disabled in config, skipping...", module.getModuleKey());
            return false;
        }
        try {
            if(!module.shouldEnable()) {
                Debug.MODULES.log("Module {} should not be enabled, skipping...", module.getModuleKey());
                return false;
            }
            module.onEnable();
            enabledModules.add(module);
            Bukkit.getPluginManager().registerEvents(module, VanillaEnhancements.getPlugin());
            registerTickServices(module);
            Debug.MODULES.log("Module {} enabled!", module.getModuleKey());
            return true;
        } catch (Throwable throwable) {
            logger.log(Level.SEVERE, "The module " + module.getName() + " couldn't be loaded due to an error:");
            logger.log(Level.SEVERE, throwable.getMessage());
            return false;
        }
    }

    private void registerTickServices(VEModule module) {
        Debug.TICK_SERVICES.log("Registering tick services for module {}...", module.getModuleKey());
        TickServiceExecutor executor = VanillaEnhancements.getPlugin().getTickServiceExecutor();
        Class<? extends VEModule> clazz = module.getClass();
        for (Method method : clazz.getMethods()) {
            Debug.TICK_SERVICES.log("Checking method {}...", method.getName());
            if(method.isAnnotationPresent(Tick.class)) {
                Debug.TICK_SERVICES.log("Method {} is annotated with @Tick, registering...", method.getName());
                executor.addTickService(module, method.getAnnotation(Tick.class), method);
            }
        }
    }

    public void unregisterAllModules() {
        Debug.MODULES.log("Unregistering all modules...");
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

    @Nullable
    public VEModule getInstance(Class<? extends VEModule> clazz) {
        Optional<VEModule> moduleOptional = enabledModules.stream().filter(m -> m.getClass().equals(clazz)).findFirst();
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
