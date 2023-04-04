package yt.sehrschlecht.vanillaenhancements.config.options;

import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public abstract class EnumConfigOption<T extends Enum<T>> extends ConfigOption<T> {
    protected final Class<T> typeClass;

    /**
     * @param defaultValue The default value of the option.
     * @param description  A markdown formatted description of the option.
     * @param typeClass
     */
    public EnumConfigOption(T defaultValue, String description, Class<T> typeClass) {
        super(defaultValue, description);
        this.typeClass = typeClass;
    }

    @Override
    public void set(T value) {
        Config.getInstance().set(this, value.name());
    }

    @Override
    public T getFromConfig() {
        String value = Config.getInstance().getDocument().getString(toPath());
        try {
            return T.valueOf(typeClass, value.toUpperCase());
        } catch (IllegalArgumentException e) {
            VanillaEnhancements.getPlugin().getLogger().severe("Invalid " + typeClass.getSimpleName() + " for option " + toPath() + ": " + value);
            return defaultValue;
        }
    }

    /**
     * @param value The value to check
     * @return Null if the value is valid, otherwise the error message
     */
    @Override
    public @Nullable String validate(T value) {
        return null;
    }

}
