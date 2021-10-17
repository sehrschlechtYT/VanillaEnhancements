package yt.sehrschlecht.vanillaenhancements.config;

import org.bukkit.NamespacedKey;

public class ConfigOption {
    private final String key;
    private final NamespacedKey moduleKey;
    private final Object defaultValue;

    public ConfigOption(String key, NamespacedKey moduleKey, Object defaultValue) {
        this.key = key;
        this.moduleKey = moduleKey;
        this.defaultValue = defaultValue;
    }

    public NamespacedKey getModuleKey() {
        return moduleKey;
    }

    public String getKey() {
        return key;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public boolean asBoolean() {
        return Config.optionAsBoolean(this);
    }

    public double asDouble() {
        return Config.optionAsDouble(this);
    }

    public int asInt() {
        return Config.optionAsInt(this);
    }
}
