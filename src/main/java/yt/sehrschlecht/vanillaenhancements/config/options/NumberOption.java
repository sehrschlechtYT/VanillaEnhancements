package yt.sehrschlecht.vanillaenhancements.config.options;

import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public abstract class NumberOption<T> extends ConfigOption<T> {
    private final T min;
    private final T max;

    /**
     * @param defaultValue The default value of the option.
     * @param description  A markdown formatted description of the option.
     * @param min          The minimum value of the option.
     * @param max          The maximum value of the option.
     */
    public NumberOption(T defaultValue, String description, T min, T max) {
        super(defaultValue, description);
        this.min = min;
        this.max = max;
    }

    @Override
    public String getPossibleValues() {
        if(min == null && max == null) {
            return "Any number";
        }
        if(min == null) {
            return "`<" + max + "`";
        }
        if(max == null) {
            return "`>" + min + "`";
        }
        return "`" + min + "-" + max + "`";
    }

    /**
     * @param value The value to check
     * @return Null if the value is valid, otherwise the error message
     */
    @Override
    public @Nullable String validate(T value) {
        if(!(value instanceof Number number)) return null;
        if(min != null && max != null) {
            Number minNumber = (Number) min;
            Number maxNumber = (Number) max;
            if(number.doubleValue() < minNumber.doubleValue() || number.doubleValue() > maxNumber.doubleValue()) {
                return "Value must be between " + min + " and " + max;
            }
        } else if(min != null) {
            Number minNumber = (Number) min;
            if(number.doubleValue() < minNumber.doubleValue()) {
                return "Value must be greater than " + min;
            }
        } else if(max != null) {
            Number maxNumber = (Number) max;
            if(number.doubleValue() > maxNumber.doubleValue()) {
                return "Value must be less than " + max;
            }
        }
        return null;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }
}
