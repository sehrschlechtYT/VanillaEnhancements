package yt.sehrschlecht.vanillaenhancements.config.options;

import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class BooleanOption extends ConfigOption<Boolean> {

    /**
     * @param defaultValue The default value of the option.
     * @param description  A markdown formatted description of the option.
     */
    public BooleanOption(Boolean defaultValue, @Nullable String description) {
        super(defaultValue, description);
    }

    @Override
    public Boolean getFromConfig() {
        return Config.getInstance().getDocument().getBoolean(toPath());
    }

    @Override
    public String getPossibleValues() {
        return "`true/false`";
    }

    @Override
    public String valueToDisplayString(Boolean value) {
        return value ? "§aEnabled" : "§cDisabled";
    }

    /**
     * @param value The value to check
     * @return Null if the value is valid, otherwise the error message
     */
    @Override
    public @Nullable String validate(Boolean value) {
        return null;
    }
}
