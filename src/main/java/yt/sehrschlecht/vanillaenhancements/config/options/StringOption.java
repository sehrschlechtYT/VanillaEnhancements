package yt.sehrschlecht.vanillaenhancements.config.options;

import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class StringOption extends ConfigOption<String> {
    private final boolean allowEmpty;

    /**
     * @param defaultValue The default value of the option.
     * @param description  A markdown formatted description of the option.
     * @param allowEmpty
     */
    public StringOption(String defaultValue, @Nullable String description, boolean allowEmpty) {
        super(defaultValue, description);
        this.allowEmpty = allowEmpty;
    }

    public StringOption(String defaultValue, String description) {
        this(defaultValue, description, false);
    }

    @Override
    public String getFromConfig() {
        return Config.getInstance().getDocument().getString(toPath());
    }

    @Override
    public String getPossibleValues() {
        return "Any string";
    }

    /**
     * @param value The value to check
     * @return Null if the value is valid, otherwise the error message
     */
    @Override
    public @Nullable String validate(String value) {
        if(!allowEmpty && value.isEmpty()) return "The value can't be empty";
        return null;
    }
}
