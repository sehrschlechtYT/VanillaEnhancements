package yt.sehrschlecht.vanillaenhancements.config;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
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
        return Config.getInstance().optionAsBoolean(this);
    }

    public double asDouble() {
        return Config.getInstance().optionAsDouble(this);
    }

    public int asInt() {
        return Config.getInstance().optionAsInt(this);
    }

    public String asString() {
        return Config.getInstance().optionAsString(this);
    }

    public ChatColor asChatColor() {
        return ChatColor.valueOf(Config.getInstance().optionAsString(this));
    }

    public void reset() {
        set(getDefaultValue());
    }

    public void set(Object value) {
        Config.getInstance().set(this, value);
    }
}
