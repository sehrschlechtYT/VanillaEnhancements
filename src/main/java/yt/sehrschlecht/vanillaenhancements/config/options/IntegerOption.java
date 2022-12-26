package yt.sehrschlecht.vanillaenhancements.config.options;

import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class IntegerOption extends ConfigOption<Integer> {
    private final int min;
    private final int max;

    /**
     * @param defaultValue The default value of the option.
     * @param description  A markdown formatted description of the option.
     * @param min          The minimum value of the option.
     * @param max          The maximum value of the option.
     */
    public IntegerOption(int defaultValue, String description, int min, int max) {
        super(defaultValue, description);
        this.min = min;
        this.max = max;
    }

    @Override
    public String getPossibleValues() {
        return "`" + min + "-" + max + "`";
    }

    @Override
    public Integer getFromConfig() {
        return Config.getInstance().getDocument().getInt(toPath());
    }

    /**
     * @param value The value to check
     * @return Null if the value is valid, otherwise the error message
     */
    @Override
    public @Nullable String validate(Integer value) {
        if(value < min || value > max) {
            return "Value must be between " + min + " and " + max;
        }
        return null;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
