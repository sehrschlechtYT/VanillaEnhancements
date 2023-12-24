package yt.sehrschlecht.vanillaenhancements.config.options;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator;

import java.util.function.BiConsumer;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class BooleanOption extends ConfigOption<Boolean> {


    /**
     * @param defaultValue The default value of the option.
     * @param description  A markdown formatted description of the option.
     */
    public BooleanOption(Boolean defaultValue, @Nullable String description) {
        super(defaultValue, description);
    }

    /**
     * @param defaultValue  The default value of the option.
     * @param description   A markdown formatted description of the option.
     * @param updateHandler A consumer that takes the old and the new value of the option after an update (e.g. through the UI)
     */
    public BooleanOption(Boolean defaultValue, @Nullable String description, @Nullable BiConsumer<Boolean, Boolean> updateHandler) {
        super(defaultValue, description, updateHandler);
    }

    @Override
    public Boolean getFromConfig() {
        return Config.getInstance().getDocument().getBoolean(toPath());
    }

    @Override
    public String getPossibleValues() {
        return "`true/false`";
    }

    @Override
    public String valueToDisplayString(Boolean value) {
        return value ? "§aEnabled" : "§cDisabled";
    }

    /**
     * @param value The value to check
     * @return Null if the value is valid, otherwise the error message
     */
    @Override
    public @Nullable String validate(Boolean value) {
        return null;
    }

    @Override
    public ClickableItem buildClickableItem(ItemCreator creator, SmartInventory origin) {
        creator.addLore("§9§oLeft click §r§9to toggle");
        creator.addLore("§9Use §oshift + right click §r§9to reset the value");
        return ClickableItem.of(creator.build(), event -> {
            Player player = (Player) event.getWhoClicked();
            if (event.getClick().equals(ClickType.SHIFT_RIGHT)) {
                reset();
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1f, 1f);
            } else if (event.getClick().equals(ClickType.LEFT)) {
                set(!get());
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, get() ? 2f : 0f);
            }
        });
    }

}
