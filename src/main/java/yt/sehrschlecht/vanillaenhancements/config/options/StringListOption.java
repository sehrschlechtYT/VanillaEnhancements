package yt.sehrschlecht.vanillaenhancements.config.options;

import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;

import java.util.List;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class StringListOption extends ConfigOption<List<String>> {
    /**
     * @param defaultValue The default value of the option.
     * @param description  A markdown formatted description of the option.
     */
    public StringListOption(List<String> defaultValue, @Nullable String description) {
        super(defaultValue, description);
    }

    @Override
    public String getPossibleValues() {
        return "Any list of strings";
    }

    @Override
    public List<String> getFromConfig() {
        return Config.getInstance().getDocument().getStringList(toPath());
    }

    /**
     * @param value The value to check
     * @return Null if the value is valid, otherwise the error message
     */
    @Override
    public @Nullable String validate(List<String> value) {
        return null;
    }
}
