package yt.sehrschlecht.vanillaenhancements.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Config {
    private static FileConfiguration config;


    public static void init() {
        config = VanillaEnhancements.getPlugin().getConfig();
        for (VEModule module : VanillaEnhancements.modules) {
            String key = module.getKey().getKey();
            if(!config.contains(key + ".enabled")) {
                config.set(key + ".enabled", false);
            }
            for (ConfigOption option : getOptions(module)) {
                if(!config.contains(key + "." + option.getKey())) {
                    config.set(key + "." + option.getKey(), option.getDefaultValue());
                }
            }
        }
        save();
    }

    public static String message(String key) {
        if(!config.contains("msg." + key)) return "Missing translation!";
        return ChatColor.translateAlternateColorCodes('&', config.getString("msg." + key));
    }

    public static String optionAsString(ConfigOption option) {
        return config.getString(option.getModuleKey().getKey() + "." + option.getKey());
    }

    public static int optionAsInt(ConfigOption option) {
        return config.getInt(option.getModuleKey().getKey() + "." + option.getKey());
    }

    public static double optionAsDouble(ConfigOption option) {
        return config.getDouble(option.getModuleKey().getKey() + "." + option.getKey());
    }

    public static boolean optionAsBoolean(ConfigOption option) {
        return config.getBoolean(option.getModuleKey().getKey() + "." + option.getKey());
    }

    public static List<String> optionAsStringList(ConfigOption option) {
        return config.getStringList(option.getModuleKey().getKey() + "." + option.getKey());
    }

    public static void save() {
        VanillaEnhancements.getPlugin().saveConfig();
    }

    @NotNull
    public static List<ConfigOption> getOptions(VEModule module) {
        List<ConfigOption> options = new ArrayList<>();
        Class<? extends Annotation> annotation = RegisterOption.class;
        Class<?> moduleClass = module.getClass();
        for (Field field : moduleClass.getDeclaredFields()) {
            if(field.isAnnotationPresent(annotation)) {
                try {
                    options.add((ConfigOption) field.get(module));
                } catch (Exception e) {

                }
            }
        }
        return options;
    }

    public static boolean isModuleEnabled(VEModule module) {
        return config.getBoolean(module.getKey().getKey() + ".enabled");
    }
}
