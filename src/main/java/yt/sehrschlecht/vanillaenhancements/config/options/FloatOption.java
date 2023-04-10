package yt.sehrschlecht.vanillaenhancements.config.options;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.config.Config;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class FloatOption extends NumberOption<Float> {

    /**
     * @param defaultValue The default value of the option.
     * @param description  A markdown formatted description of the option.
     * @param min          The minimum value of the option.
     * @param max          The maximum value of the option.
     */
    public FloatOption(float defaultValue, @Nullable String description, Float min, Float max, float step) {
        super(defaultValue, description, min, max, step);
    }

    @Override
    public Float getFromConfig() {
        return Config.getInstance().getDocument().getFloat(toPath());
    }

    @Override
    protected @NotNull Float add(Float first, Float second) {
        return first + second;
    }

    @Override
    protected @NotNull Float subtract(Float first, Float second) {
        return first - second;
    }

}