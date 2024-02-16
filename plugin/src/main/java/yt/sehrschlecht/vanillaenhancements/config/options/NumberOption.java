package yt.sehrschlecht.vanillaenhancements.config.options;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator;
import yt.sehrschlecht.vanillaenhancements.utils.VESound;

import java.util.function.BiConsumer;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public abstract class NumberOption<T> extends ConfigOption<T> {

    private final T min;
    private final T max;
    private final T step;

    /**
     * @param defaultValue The default value of the option.
     * @param description  A markdown formatted description of the option.
     * @param min          The minimum value of the option.
     * @param max          The maximum value of the option.
     */
    public NumberOption(T defaultValue, @Nullable String description, T min, T max, @NotNull T step) {
        super(defaultValue, description);
        this.min = min;
        this.max = max;
        this.step = step;
        if (((Number) step).doubleValue() <= 0) {
            throw new IllegalArgumentException("Step must be greater than 0");
        }
    }

    /**
     * @param defaultValue  The default value of the option.
     * @param description   A markdown formatted description of the option.
     * @param min           The minimum value of the option.
     * @param max           The maximum value of the option.
     * @param step          The value the option should be changed by when clicking on the option item in the GUI
     * @param updateHandler A consumer that takes the old and the new value of the option after an update (e.g. through the UI)
     */
    public NumberOption(T defaultValue, @Nullable String description, T min, T max, @NotNull T step, @Nullable BiConsumer<T, T> updateHandler) {
        super(defaultValue, description, updateHandler);
        this.min = min;
        this.max = max;
        this.step = step;
        if (((Number) step).doubleValue() <= 0) {
            throw new IllegalArgumentException("Step must be greater than 0");
        }
    }

    @Override
    public String getPossibleValues() {
        if (min == null && max == null) {
            return "Any number";
        }
        if (min == null) {
            return "`<=" + max + "`";
        }
        if (max == null) {
            return "`>=" + min + "`";
        }
        return "`" + min + "-" + max + "`";
    }

    /**
     * @param value The value to check
     * @return Null if the value is valid, otherwise the error message
     */
    @Override
    public @Nullable String validate(T value) {
        if (!(value instanceof Number number)) return null;
        if (min != null && max != null) {
            Number minNumber = (Number) min;
            Number maxNumber = (Number) max;
            if (number.doubleValue() < minNumber.doubleValue() || number.doubleValue() > maxNumber.doubleValue()) {
                return "Value must be between " + min + " and " + max;
            }
        } else if (min != null) {
            Number minNumber = (Number) min;
            if (number.doubleValue() < minNumber.doubleValue()) {
                return "Value must be greater than " + min;
            }
        } else if (max != null) {
            Number maxNumber = (Number) max;
            if (number.doubleValue() > maxNumber.doubleValue()) {
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

    public T getStep() {
        return step;
    }

    @Override
    public String valueToDisplayString(T value) {
        return value.toString();
    }

    @Override
    public ClickableItem buildClickableItem(ItemCreator creator, SmartInventory origin) {
        creator.amount(
                Math.min(Math.max(((Number) get()).intValue(), 1), 64)
        );
        boolean enableGreaterStep;
        if (min != null && max != null) {
            T diff = subtract(max, min);
            enableGreaterStep = ((Number) diff).doubleValue() > ((Number) step).doubleValue() * 5;
        } else {
            enableGreaterStep = false;
        }
        creator.addLore("§9Right click: -" + step);
        creator.addLore("§9Left click: +" + step);
        if (enableGreaterStep) {
            creator.addLore("§9Shift right click: -" + ((Number) step).doubleValue() * 5);
            creator.addLore("§9Shift left click: +" + ((Number) step).doubleValue() * 5);
        }
        creator.addLore("§9Use the §odrop item button §r§9to reset the value");
        return ClickableItem.of(creator.build(), event -> {
            Player player = (Player) event.getWhoClicked();
            if (event.getClick().equals(ClickType.DROP)) { // todo not working
                reset();
                VESound.CONFIG_RESET.play(player);
                return;
            }
            T step;
            if (event.isShiftClick()) {
                step = convertFromDouble(((Number) this.step).doubleValue() * 5);
            } else {
                step = this.step;
            }
            if (event.isRightClick()) {
                T newValue = subtract(get(), step);
                if (((Number) newValue).doubleValue() < ((Number) min).doubleValue()) {
                    newValue = min;
                }
                set(newValue);
                VESound.CONFIG_DISABLE.play(player);
            } else if (event.isLeftClick()) {
                T newValue = add(get(), step);
                if (((Number) newValue).doubleValue() > ((Number) max).doubleValue()) {
                    newValue = max;
                }
                set(newValue);
                VESound.CONFIG_ENABLE.play(player);
            }
        });
    }

    protected abstract @NotNull T add(T first, T second); // ToDo find a better way for this
    protected abstract @NotNull T subtract(T first, T second);

    // temporary (at least I hope so) solution for making shift clicks work
    protected abstract @NotNull T convertFromDouble(Double doubleObject);

}
