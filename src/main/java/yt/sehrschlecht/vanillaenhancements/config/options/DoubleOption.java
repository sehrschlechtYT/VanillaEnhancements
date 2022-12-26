package yt.sehrschlecht.vanillaenhancements.config.options;

import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class DoubleOption extends ConfigOption<Double> {
    private final double min;
    private final double max;

    /**
     * @param defaultValue The default value of the option.
     * @param description  A markdown formatted description of the option.
     * @param min          The minimum value of the option.
     * @param max          The maximum value of the option.
     */
    public DoubleOption(double defaultValue, String description, double min, double max) {
        super(defaultValue, description);
        this.min = min;
        this.max = max;
    }

    @Override
    public String getPossibleValues() {
        return "`" + min + "-" + max + "`";
    }

    @Override
    public Double getFromConfig() {
        return Config.getInstance().getDocument().getDouble(toPath());
    }

    /**
     * @param value The value to check
     * @return Null if the value is valid, otherwise the error message
     */
    @Override
    public @Nullable String validate(Double value) {
        if(value < min || value > max) {
            return "Value must be between " + min + " and " + max;
        }
        return null;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }
}

