package yt.sehrschlecht.vanillaenhancements.config.options;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.config.Config;

import java.util.function.BiConsumer;

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
     * @param step         The value the option should be changed by when clicking on the option item in the GUI
     */
    public DoubleOption(double defaultValue, @Nullable String description, Double min, Double max, double step) {
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
    public DoubleOption(Double defaultValue, @Nullable String description, Double min, Double max, @NotNull Double step, @Nullable BiConsumer<Double, Double> updateHandler) {
        super(defaultValue, description, min, max, step, updateHandler);
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
    protected @NotNull Double convertFromDouble(Double doubleObject) {
        return doubleObject;
    }

    @Override
    public Double getFromConfig() {
        return Config.getInstance().getDocument().getDouble(toPath());
    }

}

