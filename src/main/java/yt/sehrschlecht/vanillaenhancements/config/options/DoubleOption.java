package yt.sehrschlecht.vanillaenhancements.config.options;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.config.Config;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class DoubleOption extends NumberOption<Double> {

    /**
     * @param defaultValue The default value of the option.
     * @param description  A markdown formatted description of the option.
     * @param min          The minimum value of the option.
     * @param max          The maximum value of the option.
     */
    public DoubleOption(double defaultValue, @Nullable String description, Double min, Double max, double step) {
        super(defaultValue, description, min, max, step);
    }

    @Override
    protected @NotNull Double add(Double first, Double second) {
        return first + second;
    }

    @Override
    protected @NotNull Double subtract(Double first, Double second) {
        return first - second;
    }

    @Override
    public Double getFromConfig() {
        return Config.getInstance().getDocument().getDouble(toPath());
    }

}

