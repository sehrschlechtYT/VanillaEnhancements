package yt.sehrschlecht.vanillaenhancements.config.options;

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
    public FloatOption(Float defaultValue, @Nullable String description, Float min, Float max) {
        super(defaultValue, description, min, max);
    }

    @Override
    public Float getFromConfig() {
        return Config.getInstance().getDocument().getFloat(toPath());
    }

}