package yt.sehrschlecht.vanillaenhancements.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.utils.debugging.Debug;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class Config {
    private YamlDocument document;
    private static Config instance;

    public Config(YamlDocument document) {
        instance = this;
        this.document = document;
        Debug.CONFIG.log("Loading config {}...", document.getNameAsString());
    }

    public void init() {
        Debug.CONFIG.log("Initializing config...");
        for (VEModule module : VanillaEnhancements.getPlugin().getInbuiltModules()) {
            String key = module.getModuleKey().getKey();
            if(!document.contains(key + ".enabled")) {
                Debug.CONFIG.log("Creating key {}.enabled", key);
                document.set(key + ".enabled", false);
            }
            boolean found = false;
            for (ConfigOption option : getOptions(module)) {
                found = true;
                if(!document.contains(key + "." + option.getKey())) {
                    Debug.CONFIG.log("Creating key {}.{} with default value {}", key, option.getKey(), option.getDefaultValue());
                    document.set(key + "." + option.getKey(), option.getDefaultValue());
                }
            }
            if(!found) {
                Debug.CONFIG.log("No options found for module {}.", module.getModuleKey());
            }
        }
        save();
    }

    public void reload() {
        VanillaEnhancements.getPlugin().getLogger().info("Reloading config...");
        try {
            document.reload();
        } catch (IOException e) {
            VanillaEnhancements.getPlugin().getLogger().severe("Could not reload config!");
            e.printStackTrace();
        }
        Debug.CONFIG_OPTIONS.log("Reloading config options...");
        init();
        Debug.MODULES.log("Sending reload signals to modules...");
        VanillaEnhancements.getPlugin().getModuleRegistry().getEnabledModules().forEach(VEModule::reload);
    }

    public static Config getInstance() {
        return instance;
    }

    public void set(ConfigOption option, Object value) {
        document.set(option.toPath(), value);
        save();
    }

    public String message(String key) {
        if(!document.contains("msg." + key)) {
            Debug.CONFIG.log("Tried accessing non-existent message{}!", key);
            return "Missing translation!";
        }
        return ChatColor.translateAlternateColorCodes('&', document.getString("msg." + key));
    }

    public String optionAsString(ConfigOption option) {
        return document.getString(option.toPath());
    }

    public int optionAsInt(ConfigOption option) {
        return document.getInt(option.toPath());
    }

    public double optionAsDouble(ConfigOption option) {
        return document.getDouble(option.toPath());
    }

    public boolean optionAsBoolean(ConfigOption option) {
        return document.getBoolean(option.toPath());
    }

    public List<String> optionAsStringList(ConfigOption option) {
        return document.getStringList(option.toPath());
    }

    public void save() {
        try {
            document.save(new File(VanillaEnhancements.getPlugin().getDataFolder(), "config.yml"));
        } catch (IOException e) {
            VanillaEnhancements.getPlugin().getLogger().severe("An error occurred while saving the config:");
            e.printStackTrace();
        }
    }

    @NotNull
    public List<ConfigOption> getOptions(VEModule module) {
        Debug.CONFIG_OPTIONS.log("Getting options for module {}...", module.getModuleKey());
        List<ConfigOption> options = new ArrayList<>();
        Class<?> moduleClass = module.getClass();
        for (Field field : moduleClass.getDeclaredFields()) {
            Debug.CONFIG_OPTIONS.log("Checking field {}...", field.getName());
            if(field.getType().isAssignableFrom(ConfigOption.class)) {
                try {
                    ConfigOption option = (ConfigOption) field.get(module);
                    option.setModuleKey(module.getModuleKey());
                    option.setKey(field.getName());
                    options.add((ConfigOption) field.get(module));
                    Debug.CONFIG_OPTIONS.log("Found option {} with default value {}.", field.getName(), option.getDefaultValue());
                } catch (Exception e) {
                    VanillaEnhancements.getPlugin().getLogger().severe(
                            "An error occurred while getting option %s from module %s:".formatted(field.getName(), module.getModuleKey().getKey())
                    );
                    e.printStackTrace();
                }
            }
        }
        return options;
    }

    public boolean isModuleEnabled(VEModule module) {
        return document.getBoolean(module.getModuleKey().getKey() + ".enabled");
    }

    public YamlDocument getDocument() {
        return document;
    }
}
