package yt.sehrschlecht.vanillaenhancements.utils.debugging;

import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.utils.docs.VEDocsGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
        if(args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if(arg == null) continue;
                message = message.replaceFirst("\\{}", arg.toString());
            }
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
        }
    }

    public void reload() {
        Config.getInstance().reload();
        // ToDo reload debug settings too
    }

    public void generateDocs() throws IOException {
        List<VEModule> modules = VanillaEnhancements.getPlugin().getModuleRegistry().getRegisteredModules();
        logMessage("Generating documentation for " + modules.size() + " modules...");
        new VEDocsGenerator(modules).generate();
        logMessage("Successfully generated documentation! Output: " + getDocsOutput());
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getDocsOutput() {
        return "/plugins/VanillaEnhancements/docs/";
    }

    public enum ComponentType {
        CONFIG("[Config]"),
        CONFIG_COMMENTS("[Config Comments]"),
        CONFIG_OPTIONS("[Config Options]"),
        CONFIG_MODULES("[Config Modules]"),
        MESSAGES("[Messages]"),
        MODULES("[Modules]"),
        TICK_SERVICES("[TickServices]"),
        RECIPES("[Recipes]"),
        RECIPE_DISCOVERING("[Recipe Discovering]"),
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
