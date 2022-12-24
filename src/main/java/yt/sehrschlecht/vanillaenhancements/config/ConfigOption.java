package yt.sehrschlecht.vanillaenhancements.config;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import yt.sehrschlecht.vanillaenhancements.utils.debugging.Debug;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class ConfigOption {
    private String key;
    private NamespacedKey moduleKey;
    public final Object defaultValue;

    public ConfigOption(Object defaultValue) {
        this.defaultValue = defaultValue;
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
        Debug.CONFIG.log("Resetting option " + key + " to default value " + defaultValue);
        set(getDefaultValue());
    }

    public void set(Object value) {
        Config.getInstance().set(this, value);
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public String getKey() {
        return key;
    }

    public NamespacedKey getModuleKey() {
        return moduleKey;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setModuleKey(NamespacedKey moduleKey) {
        this.moduleKey = moduleKey;
    }
}
