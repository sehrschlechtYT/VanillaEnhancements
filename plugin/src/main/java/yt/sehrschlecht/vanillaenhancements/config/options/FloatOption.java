package yt.sehrschlecht.vanillaenhancements.config.options;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.config.Config;

import java.util.function.BiConsumer;

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

    /**
     * @param defaultValue  The default value of the option.
     * @param description   A markdown formatted description of the option.
     * @param min           The minimum value of the option.
     * @param max           The maximum value of the option.
     * @param step          The value the option should be changed by when clicking on the option item in the GUI
     * @param updateHandler A consumer that takes the old and the new value of the option after an update (e.g. through the UI)
     */
    public FloatOption(Float defaultValue, @Nullable String description, Float min, Float max, @NotNull Float step, @Nullable BiConsumer<Float, Float> updateHandler) {
        super(defaultValue, description, min, max, step, updateHandler);
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

    @Override
    protected @NotNull Float convertFromDouble(Double doubleObject) {
        return doubleObject.floatValue();
    }
}