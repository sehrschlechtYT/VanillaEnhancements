package yt.sehrschlecht.vanillaenhancements.config.options;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.gui.ChooseStringMenu;
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator;

import java.util.function.BiConsumer;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class StringOption extends ConfigOption<String> {
    private final boolean allowEmpty;

    /**
     * @param defaultValue The default value of the option.
     * @param description  A markdown formatted description of the option.
     * @param allowEmpty
     */
    public StringOption(String defaultValue, @Nullable String description, boolean allowEmpty) {
        super(defaultValue, description);
        this.allowEmpty = allowEmpty;
    }

    public StringOption(String defaultValue, String description) {
        this(defaultValue, description, false);
    }

    /**
     * @param defaultValue  The default value of the option.
     * @param description   A markdown formatted description of the option.
     * @param updateHandler A consumer that takes the old and the new value of the option after an update (e.g. through the UI)
     */
    public StringOption(String defaultValue, @Nullable String description, @Nullable BiConsumer<String, String> updateHandler, boolean allowEmpty) {
        super(defaultValue, description, updateHandler);
        this.allowEmpty = allowEmpty;
    }

    /**
     * @param defaultValue  The default value of the option.
     * @param description   A markdown formatted description of the option.
     * @param updateHandler A consumer that takes the old and the new value of the option after an update (e.g. through the UI)
     */
    public StringOption(String defaultValue, @Nullable String description, @Nullable BiConsumer<String, String> updateHandler) {
        this(defaultValue, description, updateHandler, false);
    }

    @Override
    public String getFromConfig() {
        return Config.getInstance().getDocument().getString(toPath());
    }

    @Override
    public String getPossibleValues() {
        return "Any string";
    }

    @Override
    public String valueToDisplayString(String value) {
        return "\"" + value + "\"";
    }

    /**
     * @param value The value to check
     * @return Null if the value is valid, otherwise the error message
     */
    @Override
    public @Nullable String validate(String value) {
        if(!allowEmpty && value.isEmpty()) return "The value can't be empty";
        return null;
    }

    @Override
    public ClickableItem buildClickableItem(ItemCreator creator, SmartInventory origin) {
        creator.addLore("§9Click to change the value");
        return ClickableItem.of(creator.build(), event -> {
            ChooseStringMenu.Companion.getInventory(VanillaEnhancements.getPlugin(), this, origin).open((Player) event.getWhoClicked());
        });
    }
}
