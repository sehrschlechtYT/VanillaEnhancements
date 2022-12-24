package yt.sehrschlecht.vanillaenhancements.utils.debugging;

import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;

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
    public final static Component CONFIG_OPTIONS = new Component(ComponentType.CONFIG_OPTIONS);
    public final static Component MODULES = new Component(ComponentType.MODULES);
    public final static Component TICK_SERVICES = new Component(ComponentType.TICK_SERVICES);
    public final static Component RECIPES = new Component(ComponentType.RECIPES);
    public final static Component OTHER = new Component(ComponentType.OTHER);

    private final List<ComponentType> enabledComponents;
    private boolean enabled = false;


    private void logMessage(String message) {
        VanillaEnhancements.getPlugin().getLogger().log(Level.INFO, message);
    }

    private void warn(String message) {
        VanillaEnhancements.getPlugin().getLogger().log(Level.WARNING, message);
    }

    public void log(String message, ComponentType componentType) {
        if(VanillaEnhancements.getPlugin() == null || VanillaEnhancements.getPlugin().getDebug() == null) {
            System.err.println("Warning: Debug.log() was called before VanillaEnhancements was initialized!");
            return;
        }
        if(!enabledComponents.contains(componentType)) return;
        VanillaEnhancements.getPlugin().getDebug().logMessage(componentType.getPrefix() + message);
    }

    public void log(String message, ComponentType componentType, Object... args) {
        for(Object arg : args) {
            if(arg == null) continue;
            message = message.replaceFirst("\\{}", arg.toString());
        }
        log(message, componentType);
    }

    public Debug() {
        enabledComponents = new ArrayList<>();
        File file = new File(VanillaEnhancements.getPlugin().getDataFolder(), ".debug");
        if (file.exists()) {
            enabled = true;
            warn("----------------------------------------");
            warn("");
            warn("Warning: Debugging is enabled!");
            warn("This may spam your console and log file!");
            warn("If you have not enabled debugging, please open a new issue on GitHub:");
            warn("https://github.com/sehrschlechtYT/VanillaEnhancements/issues");
            warn("");
            warn("----------------------------------------");
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    try {
                        enabledComponents.add(ComponentType.valueOf(line.trim().toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        VanillaEnhancements.getPlugin().getLogger().log(Level.WARNING, "Unknown debug component: " + line);
                    }
                }
            } catch (Exception e) {
                VanillaEnhancements.getPlugin().getLogger().log(Level.SEVERE, "Failed to read debug file!", e);
            }
            logMessage("Enabled debug components: " + String.join(", ", enabledComponents.stream().map(Enum::name).toList()));
        }
    }

    public void reload() {
        Config.getInstance().reload();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public enum ComponentType {
        CONFIG("[Config]"),
        CONFIG_OPTIONS("[Config Options]"),
        MODULES("[Modules]"),
        TICK_SERVICES("[TickServices]"),
        RECIPES("[Recipes]"),
        OTHER("[Other]");

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
    }
}
