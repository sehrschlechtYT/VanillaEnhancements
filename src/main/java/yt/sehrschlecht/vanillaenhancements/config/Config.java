package yt.sehrschlecht.vanillaenhancements.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;

import java.lang.annotation.Annotation;
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
    }

    public void init() {
        for (VEModule module : VanillaEnhancements.getPlugin().getInbuiltModules()) {
            String key = module.getModuleKey().getKey();
            if(!document.contains(key + ".enabled")) {
                document.set(key + ".enabled", false);
            }
            for (ConfigOption option : getOptions(module)) {
                if(!document.contains(key + "." + option.getKey())) {
                    document.set(key + "." + option.getKey(), option.getDefaultValue());
                }
            }
        }
        save();
    }

    public static Config getInstance() {
        return instance;
    }

    public void set(ConfigOption option, Object value) {
        document.set(option.getModuleKey().getKey() + "." + option.getKey(), value);
        save();
    }

    public String message(String key) {
        if(!document.contains("msg." + key)) return "Missing translation!";
        return ChatColor.translateAlternateColorCodes('&', document.getString("msg." + key));
    }

    public String optionAsString(ConfigOption option) {
        return document.getString(option.getModuleKey().getKey() + "." + option.getKey());
    }

    public int optionAsInt(ConfigOption option) {
        return document.getInt(option.getModuleKey().getKey() + "." + option.getKey());
    }

    public double optionAsDouble(ConfigOption option) {
        return document.getDouble(option.getModuleKey().getKey() + "." + option.getKey());
    }

    public boolean optionAsBoolean(ConfigOption option) {
        return document.getBoolean(option.getModuleKey().getKey() + "." + option.getKey());
    }

    public List<String> optionAsStringList(ConfigOption option) {
        return document.getStringList(option.getModuleKey().getKey() + "." + option.getKey());
    }

    public void save() {
        VanillaEnhancements.getPlugin().saveConfig();
    }

    @NotNull
    public List<ConfigOption> getOptions(VEModule module) {
        List<ConfigOption> options = new ArrayList<>();
        Class<? extends Annotation> annotation = Option.class;
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

    public boolean isModuleEnabled(VEModule module) {
        return document.getBoolean(module.getModuleKey().getKey() + ".enabled");
    }
}
