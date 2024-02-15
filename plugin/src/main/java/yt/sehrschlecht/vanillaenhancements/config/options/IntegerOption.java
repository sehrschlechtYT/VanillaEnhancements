package yt.sehrschlecht.vanillaenhancements.config.options;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.config.Config;

import java.util.function.BiConsumer;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class IntegerOption extends NumberOption<Integer> {
    /**
     * @param defaultValue The default value of the option.
     * @param description  A markdown formatted description of the option.
     * @param min          The minimum value of the option.
     * @param max          The maximum value of the option.
     */
    public IntegerOption(int defaultValue, @Nullable String description, Integer min, Integer max, int step) {
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
    public IntegerOption(Integer defaultValue, @Nullable String description, Integer min, Integer max, @NotNull Integer step, @Nullable BiConsumer<Integer, Integer> updateHandler) {
        super(defaultValue, description, min, max, step, updateHandler);
    }

    @Override
    public Integer getFromConfig() {
        return Config.getInstance().getDocument().getInt(toPath());
    }

    @Override
    protected @NotNull Integer add(Integer first, Integer second) {
        return first + second;
    }

    @Override
    protected @NotNull Integer subtract(Integer first, Integer second) {
        return first - second;
    }

    @Override
    protected @NotNull Integer convertFromDouble(Double doubleObject) {
        return doubleObject.intValue();
    }
}
