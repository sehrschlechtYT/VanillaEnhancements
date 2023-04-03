package yt.sehrschlecht.vanillaenhancements.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import kotlin.Metadata;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.utils.debugging.Debug;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class Config {
    private final YamlDocument document;
    private static Config instance;

    /**
     * Default messages
     * If the value is null, the message will be removed when updating the config. This is useful for messages that have been removed.
     */
    public static Map<String, String> messages = new HashMap<>(){{
        put("prefix", "&7[&cVE&7] ");
        put("commandDisabled", "§cThis command is disabled!");
        put("knockback.usage", "§cUsage: /knockback <percentage>");
        put("knockback.invalidInput", "§cThe percentage must be between %min% and %max%!");
        put("knockback.success", "Set the knockback multiplier to %percentage%%");
    }};

    public Config(YamlDocument document) {
        instance = this;
        this.document = document;
        Debug.CONFIG.log("Loading config {}...", document.getNameAsString());
    }

    public void init() {
        Debug.CONFIG.log("Initializing config...");

        Debug.MESSAGES.log("Initializing messages...");
        Debug.MESSAGES.log("Found {} default messages.", messages.size());

        for (Map.Entry<String, String> entry : messages.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Debug.MESSAGES.log("Found message {} with default value \"{}\"", key, value);
            if(!document.contains("msg." + key)) {
                Debug.MESSAGES.log("Creating key msg.{} with default value {}", key, value);
                document.set("msg." + key, value);
            } else if(value == null) {
                Debug.MESSAGES.log("Removing message {} because it is null.", key);
                document.remove("msg." + key);
            }
        }

        for (VEModule module : VanillaEnhancements.getPlugin().getInbuiltModules()) {
            Debug.CONFIG_MODULES.log("Initializing config for module {}...", module.getModuleKey());
            String key = module.getModuleKey().getKey();
            Debug.CONFIG_MODULES.log("Module {} does{} have a config enabled key.", key, document.contains(key + ".enabled") ? "" : " not");
            if(!document.contains(key + ".enabled")) {
                Debug.CONFIG.log("Creating key {}.enabled", key);
                document.set(key + ".enabled", false);
            }
            boolean found = false;
            for (ConfigOption<?> option : getOptions(module)) {
                found = true;
                if(!document.contains(key + "." + option.getKey())) {
                    Debug.CONFIG.log("Creating key {}.{} with default value {}", key, option.getKey(), option.getDefaultValue());
                    option.reset();
                }
            }
            if(!found) {
                Debug.CONFIG.log("No options found for module {}.", module.getModuleKey());
            }
        }
        save();
    }

    public void reload() {
        VanillaEnhancements.getPlugin().getLogger().info("Reloading config (experimental)...");
        VanillaEnhancements.getPlugin().getLogger().info("Warning: This will not enable/disable modules! Only options will be reloaded!");
        try {
            document.reload();
        } catch (IOException e) {
            VanillaEnhancements.getPlugin().getLogger().severe("Could not reload config:");
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

    public void set(ConfigOption<?> option, Object value) {
        try {
            document.reload();
        } catch (IOException e) {
            Debug.CONFIG.log("An error occurred while reloading the config:");
            Debug.CONFIG.log(e.getMessage());
        }
        document.set(option.toPath(), value);
        save();
    }

    public String message(String key) {
        if(!document.contains("msg." + key)) {
            Debug.CONFIG.log("Tried accessing non-existent message {}!", key);
            return "Missing translation!";
        }
        return ChatColor.translateAlternateColorCodes('&', document.getString("msg." + key));
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
    public List<ConfigOption<?>> getOptions(VEModule module) {
        Debug.CONFIG_OPTIONS.log("Getting options for module {}...", module.getModuleKey());
        List<ConfigOption<?>> options = new ArrayList<>();
        Class<?> moduleClass = module.getClass();
        List<Field> fields;
        boolean isKotlinObject = moduleClass.isAnnotationPresent(Metadata.class);
        if (isKotlinObject) {
            Debug.CONFIG_OPTIONS.log("Module {} seems to be a Kotlin object, using Kotlin reflection...", module.getModuleKey());
            fields = KotlinConfigHelper.Companion.getFields(module);
        } else {
            fields = List.of(moduleClass.getDeclaredFields());
        }
        for (Field field : fields) {
            //check if field is public
            if(!isKotlinObject && !Modifier.isPublic(field.getModifiers())) {
                Debug.CONFIG_OPTIONS.log("Field {} is not public, skipping...", field.getName());
                continue;
            }
            Debug.CONFIG_OPTIONS.log("Checking field {}...", field.getName());
            Debug.CONFIG_OPTIONS.log("Type: {}, Assignable: {}", field.getType(), ConfigOption.class.isAssignableFrom(field.getType()));
            if(ConfigOption.class.isAssignableFrom(field.getType())) {
                try {
                    ConfigOption<?> option;
                    if (isKotlinObject) {
                        option = (ConfigOption<?>) KotlinConfigHelper.Companion.getFieldValue(module, field);
                        if (option == null) {
                            Debug.CONFIG_OPTIONS.log("Kotlin field {} is null, skipping...", field.getName());
                            continue;
                        }
                    } else {
                        option = (ConfigOption<?>) field.get(module);
                    }
                    option.setModuleKey(module.getModuleKey());
                    option.setKey(field.getName());
                    options.add(option);
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
