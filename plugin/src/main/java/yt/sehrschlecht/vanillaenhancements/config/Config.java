package yt.sehrschlecht.vanillaenhancements.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.Block;
import kotlin.reflect.KProperty;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.utils.debugging.Debug;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class Config {
    private final YamlDocument document;
    private static Config instance;

    private final Map<NamespacedKey, List<ConfigOption<?>>> moduleOptions;

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
        this.moduleOptions = new HashMap<>();
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
            if (!document.contains("msg." + key)) {
                Debug.MESSAGES.log("Creating key msg.{} with default value {}", key, value);
                document.set("msg." + key, value);
            } else if (value == null) {
                Debug.MESSAGES.log("Removing message {} because it is null.", key);
                document.remove("msg." + key);
            }
        }
    }

    public void createModuleOptions(VEModule module) {
        Debug.CONFIG_MODULES.log("Initializing config for module {}...", module.getModuleKey());
        moduleOptions.put(module.getModuleKey(), getOptions(module));
        String key = module.getModuleKey().getKey();
        boolean found = false;
        for (ConfigOption<?> option : moduleOptions.get(module.getModuleKey())) {
            found = true;
            if (!document.contains(key + "." + option.getKey())) {
                Debug.CONFIG.log("Creating key {}.{} with default value \"{}\".", key, option.getKey(), option.getDefaultValue());
                option.reset();
            }
        }
        if (!found) {
            Debug.CONFIG.log("No options found for module {}.", module.getModuleKey());
        }
        if (!document.contains(key + ".enabled")) {
            Debug.CONFIG.warn("Module {} does not have a config enabled key!", key);
        }
        setupComments(module);
        save();
    }

    @ApiStatus.Internal
    protected void setupComments(VEModule module) {
        Debug.CONFIG.log("Setting up comments...");
        Debug.CONFIG_COMMENTS.log("Starting comment setup for module {}...", module.getModuleKey().toString());

        String key = module.getModuleKey().getKey();
        Debug.CONFIG_COMMENTS.log("Checking comments for module {}...", key);
        if (module.getDescription() != null && !module.getDescription().isBlank()) {
            Debug.CONFIG_COMMENTS.log("Module {} has a description.", key);
            Block<?> block = document.getBlock(key);
            if (block.getComments() != null && block.getComments().stream().anyMatch(c -> c.trim().equalsIgnoreCase(module.getDescription().trim()))) {
                Debug.CONFIG_COMMENTS.log("Skipping comment for module {} because it already exists.", key);
            } else {
                block.setComments(List.of(" " + module.getDescription()));
                Debug.CONFIG_COMMENTS.log("Added comment for module {}.", key);
            }
        }

        Debug.CONFIG_COMMENTS.log("Starting comment setup for options of {}...", module.getModuleKey().toString());
        for (ConfigOption<?> option : moduleOptions.get(module.getModuleKey())) {
            Debug.CONFIG_COMMENTS.log("Checking comments for option {}...", option.toPath());
            Block<?> block = document.getBlock(option.toPath());
            if (option.getDescription() != null && !option.getDescription().isBlank()) {
                block.setComments(List.of(
                        " Possible values: " + option.getPossibleValues(),
                        " " + option.getDescription()
                ));
                Debug.CONFIG_COMMENTS.log("Added comment for option {}.", option.toPath());
            }
        }
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
        save(); // ToDo do not immediately save the config but only on certain events
    }

    public String message(String key) {
        if (!document.contains("msg." + key)) {
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

    /**
     * Warning: This method should only be called once in #init() because it is performance heavy! Options are cached and can be accessed via {@link #getModuleOptions(VEModule)}
     * @param module The module to get the options for
     * @return A list of all options for the given module
     */
    @NotNull
    @ApiStatus.Internal
    private List<ConfigOption<?>> getOptions(VEModule module) {
        Debug.CONFIG_OPTIONS.log("Getting options for module {}...", module.getModuleKey());
        List<ConfigOption<?>> options = new ArrayList<>();
        Class<?> moduleClass = module.getClass();
        List<Field> fields;
        Map<Field, KProperty<?>> kotlinFields = null;
        boolean isKotlinObject = Arrays.stream(moduleClass.getDeclaredAnnotations()).anyMatch(annotation ->
            annotation.annotationType().getName().equalsIgnoreCase("kotlin.Metadata")
        );
        if (isKotlinObject) {
            Debug.CONFIG_OPTIONS.log("Module {} seems to be a Kotlin object, using Kotlin reflection...", module.getModuleKey());
            kotlinFields = KotlinConfigHelper.Companion.getFields(module.getClass());
            fields = new ArrayList<>(kotlinFields.keySet());
        } else {
            List<List<Field>> classFields = new ArrayList<>();
            fields = new ArrayList<>();
            Class<?> currentClass = moduleClass;
            while (currentClass != null && currentClass != Object.class) { // search recursively for fields
                classFields.add(Arrays.asList(currentClass.getDeclaredFields()));
                currentClass = currentClass.getSuperclass();
            }
            Collections.reverse(classFields); // superclass fields first (e.g. "enable" field)
            for (List<Field> classField : classFields) {
                fields.addAll(classField);
            }
        }
        for (Field field : fields) {
            //check if field is public
            if (!isKotlinObject && !Modifier.isPublic(field.getModifiers())) {
                Debug.CONFIG_OPTIONS.log("Field {} is not public, skipping...", field.getName());
                continue;
            }
            Debug.CONFIG_OPTIONS.log("Checking field {}...", field.getName());
            Debug.CONFIG_OPTIONS.log("Type: {}, Assignable: {}", field.getType().getSimpleName(), ConfigOption.class.isAssignableFrom(field.getType()));
            if (ConfigOption.class.isAssignableFrom(field.getType())) {
                try {
                    ConfigOption<?> option;
                    if (isKotlinObject) {
                        option = (ConfigOption<?>) KotlinConfigHelper.Companion.getFieldValue(kotlinFields.get(field), module);
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
                } catch (Exception exception) {
                    VanillaEnhancements.getPlugin().getLogger().severe(
                            "An error occurred while getting option %s from module %s:".formatted(field.getName(), module.getModuleKey().getKey())
                    );
                    exception.printStackTrace();
                }
            }
        }
        return options;
    }

    public YamlDocument getDocument() {
        return document;
    }

    /**
     * Retrieves all options for the given module, saved in {@link #moduleOptions} in {@link #init()}
     * @param module The module to get the options for
     * @return A list of all options for the given module
     */
    public List<ConfigOption<?>> getModuleOptions(VEModule module) {
        return moduleOptions.computeIfAbsent(module.getModuleKey(), key -> getOptions(module));
    }

}
