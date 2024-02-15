package yt.sehrschlecht.vanillaenhancements.config.options;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.gui.ModifyStringListMenu;
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class StringListOption extends ConfigOption<List<String>> {
    /**
     * @param defaultValue The default value of the option.
     * @param description  A markdown formatted description of the option.
     */
    public StringListOption(List<String> defaultValue, @Nullable String description) {
        super(defaultValue, description);
    }

    /**
     * @param defaultValue  The default value of the option.
     * @param description   A markdown formatted description of the option.
     * @param updateHandler A consumer that takes the old and the new value of the option after an update (e.g. through the UI)
     */
    public StringListOption(List<String> defaultValue, @Nullable String description, @Nullable BiConsumer<List<String>, List<String>> updateHandler) {
        super(defaultValue, description, updateHandler);
    }

    @Override
    public String getPossibleValues() {
        return "Any list of strings";
    }

    @Override
    public String valueToDisplayString(List<String> value) {
        return String.join(", ", value);
    }

    @Override
    public List<String> getFromConfig() {
        return Config.getInstance().getDocument().getStringList(toPath());
    }

    /**
     * @param value The value to check
     * @return Null if the value is valid, otherwise the error message
     */
    @Override
    public @Nullable String validate(List<String> value) {
        return null;
    }

    @Override
    public ClickableItem buildClickableItem(ItemCreator creator, SmartInventory origin) {
        creator.addLore("§9Left Click to add/remove items");
        creator.addLore("§9Use §oshift + right click §r§9to reset the value");
        return ClickableItem.of(creator.build(), event -> {
            Player player = (Player) event.getWhoClicked();
            if (event.getClick().equals(ClickType.SHIFT_RIGHT)) {
                reset();
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1f, 1f);
            } else if (event.getClick().equals(ClickType.LEFT)) {
                ModifyStringListMenu.Companion.getInventory(VanillaEnhancements.getPlugin(), this, origin).open((Player) event.getWhoClicked());
            }
        });
    }

}