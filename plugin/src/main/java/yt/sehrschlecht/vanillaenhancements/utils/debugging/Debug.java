package yt.sehrschlecht.vanillaenhancements.utils.debugging;

import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class Debug {
    public final static Component CONFIG = new Component(ComponentType.CONFIG);
    public final static Component CONFIG_COMMENTS = new Component(ComponentType.CONFIG_COMMENTS);
    public final static Component CONFIG_OPTIONS = new Component(ComponentType.CONFIG_OPTIONS);
    public final static Component CONFIG_MODULES = new Component(ComponentType.CONFIG_MODULES);
    public final static Component MESSAGES = new Component(ComponentType.MESSAGES);
    public final static Component MODULES = new Component(ComponentType.MODULES);
    public final static Component TICK_SERVICES = new Component(ComponentType.TICK_SERVICES);
    public final static Component RECIPES = new Component(ComponentType.RECIPES);
    public final static Component RECIPE_DISCOVERING = new Component(ComponentType.RECIPE_DISCOVERING);
    public final static Component RESOURCE_PACKS = new Component(ComponentType.RESOURCE_PACKS);
    public final static Component MISC = new Component(ComponentType.MISC);

    private final List<ComponentType> enabledComponents;
    private boolean enabled = false;


    private void logMessage(String message) {
        VanillaEnhancements.getPlugin().getLogger().log(Level.INFO, message);
    }

    private void warn(String message) {
        VanillaEnhancements.getPlugin().getLogger().log(Level.WARNING, message);
    }

    public void log(String message, ComponentType componentType) {
        if (notInitialized()) return;
        if (!componentEnabled(componentType)) return;
        logMessage(componentType.getPrefix() + message);
    }

    public void warn(String message, ComponentType componentType) {
        if (notInitialized()) return;
        if (!componentEnabled(componentType)) return;
        warn(componentType.getPrefix() + message);
    }

    private boolean notInitialized() {
        if (VanillaEnhancements.getPlugin() == null || VanillaEnhancements.getPlugin().getDebug() == null) {
            System.err.println("Warning: Debug.warn() was called before VanillaEnhancements was initialized! Debug messages are not being logged yet!");
            return true;
        }
        return false;
    }

    public boolean componentEnabled(ComponentType componentType) {
        return enabledComponents.contains(componentType);
    }

    public void warn(String message, ComponentType componentType, Object... args) {
        if (args != null) {
            for (Object arg : args) {
                if (arg == null) continue;
                message = message.replaceFirst("\\{}", arg.toString());
            }
        }
        warn(message, componentType);
    }

    public void log(String message, ComponentType componentType, Object... args) {
        if (args != null) {
            for (Object arg : args) {
                if (arg == null) continue;
                message = message.replaceFirst("\\{}", arg.toString());
            }
        }
        log(message, componentType);
    }

    public Debug() {
        enabledComponents = new ArrayList<>();
        loadDebugSettings();
    }

    private void loadDebugSettings() {
        File file = new File(VanillaEnhancements.getPlugin().getDataFolder(), ".debug");
        if (file.exists()) {
            enabledComponents.clear();
            enabled = true;
            warn("--------------------------------------------------------------------------------");
            warn("");
            warn("Warning: Debugging is enabled!");
            warn("This may spam your console and log file!");
            warn("If you did not enable debugging, please open a new issue on GitHub:");
            warn("https://github.com/sehrschlechtYT/VanillaEnhancements/issues");
            warn("");
            warn("--------------------------------------------------------------------------------");
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("#") || line.trim().isEmpty()) continue;
                    try {
                        enabledComponents.add(ComponentType.valueOf(line.trim().toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        VanillaEnhancements.getPlugin().getLogger().log(Level.WARNING, "Unknown debug component: " + line);
                    }
                }
            } catch (Exception e) {
                VanillaEnhancements.getPlugin().getLogger().log(Level.SEVERE, "Failed to read debug file!", e);
            }
            logMessage("Enabled debug components: " + (enabledComponents.isEmpty() ? "None" : String.join(", ", enabledComponents.stream().map(Enum::name).toList())));
        } else {
            enabled = false;
        }
    }

    public void reload() {
        Config.getInstance().reload();
        loadDebugSettings();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public enum ComponentType {
        CONFIG("[Config]"),
        CONFIG_COMMENTS("[Config Comments]"),
        CONFIG_OPTIONS("[Config Options]"),
        CONFIG_MODULES("[Config Modules]"),
        /**
         * This component is not used to log anything, but to delete the config on startup.
         */
        DELETE_CONFIG_ON_STARTUP(null),
        MESSAGES("[Messages]"),
        MODULES("[Modules]"),
        TICK_SERVICES("[TickServices]"),
        RECIPES("[Recipes]"),
        RECIPE_DISCOVERING("[Recipe Discovering]"),
        RESOURCE_PACKS("[Resource Packs]"),
        MISC("[Misc]");

        private final String prefix;

        ComponentType(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix + " ";
        }
    }

    public static class Component {
        private final ComponentType type;

        public Component(ComponentType type) {
            this.type = type;
        }

        public void log(String message, Object... args) {
            VanillaEnhancements.getPlugin().getDebug().log(message, type, args);
        }

        public void warn(String message, Object... args) {
            VanillaEnhancements.getPlugin().getDebug().warn(message, type, args);
        }
    }

}
