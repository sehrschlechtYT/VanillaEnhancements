package yt.sehrschlecht.vanillaenhancements.config;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator;
import yt.sehrschlecht.vanillaenhancements.utils.debugging.Debug;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public abstract class ConfigOption<T> {
    protected String key;
    protected NamespacedKey moduleKey;
    protected final T defaultValue;
    protected final String description;

    /**
     * @param defaultValue The default value of the option.
     * @param description A markdown formatted description of the option.
     */
    public ConfigOption(T defaultValue, @Nullable String description) {
        this.defaultValue = defaultValue;
        this.description = description;
    }

    public void reset() {
        Debug.CONFIG.log("Resetting option " + key + " to default value " + defaultValue);
        set(getDefaultValue());
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public String getKey() {
        return key;
    }

    public T get() {
        T object = getFromConfig();
        if (object == null) {
            Debug.CONFIG.log("Option " + toPath() + " is null, resetting to default value " + defaultValue);
            reset();
            return defaultValue;
        }
        String validationError = validate(object);
        if (validationError != null){
            Debug.CONFIG.log("Option " + toPath() + " is invalid, resetting to default value " + defaultValue + " (" + validationError + ")");
            reset();
            return defaultValue;
        }
        return object;
    }

    public abstract T getFromConfig();

    public abstract String getPossibleValues();

    public String valueToDisplayString() {
        return valueToDisplayString(get());
    }
    public abstract String valueToDisplayString(T value);

    public void set(T value) {
        Config.getInstance().set(this, value);
    }

    /**
     * @param value The value to check
     * @return Null if the value is valid, otherwise the error message
     */
    public abstract @Nullable String validate(T value);

    public NamespacedKey getModuleKey() {
        return moduleKey;
    }

    public String toPath() {
        return getModuleKey().getKey() + "." + getKey();
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setModuleKey(NamespacedKey moduleKey) {
        this.moduleKey = moduleKey;
    }

    public @Nullable String getDescription() {
        return description;
    }

    public abstract ClickableItem buildClickableItem(ItemCreator creator, SmartInventory origin);

}
