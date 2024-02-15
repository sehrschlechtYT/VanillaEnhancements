package yt.sehrschlecht.vanillaenhancements.config.options;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator;
import yt.sehrschlecht.vanillaenhancements.utils.ModuleUtils;

import java.util.List;
import java.util.function.BiConsumer;

public class EnumListConfigOption<T extends Enum<T>> extends ConfigOption<List<T>> {
    protected final Class<T> typeClass;

    /**
     * @param defaultValue The default value of the option.
     * @param description  A markdown formatted description of the option.
     * @param typeClass
     */
    public EnumListConfigOption(List<T> defaultValue, @Nullable String description, Class<T> typeClass) {
        super(defaultValue, description);
        this.typeClass = typeClass;
    }

    /**
     * @param defaultValue  The default value of the option.
     * @param description   A markdown formatted description of the option.
     * @param updateHandler A consumer that takes the old and the new value of the option after an update (e.g. through the UI)
     * @param typeClass
     */
    public EnumListConfigOption(List<T> defaultValue, @Nullable String description, @Nullable BiConsumer<List<T>, List<T>> updateHandler, Class<T> typeClass) {
        super(defaultValue, description, updateHandler);
        this.typeClass = typeClass;
    }

    @Override
    public List<T> getFromConfig() {
        List<String> value = Config.getInstance().getDocument().getStringList(toPath());
        return value.stream().map(v -> T.valueOf(typeClass, v.toUpperCase())).toList();
    }

    @Override
    public void set(List<T> value) {
        setToObject(value.stream().map(Enum::toString).toList(), value);
    }

    @Override
    public String getPossibleValues() {
        return "Any list of " + typeClass.getSimpleName();
    }

    @Override
    public String valueToDisplayString(List<T> value) {
        return String.join(", ", value.stream().map(v -> ModuleUtils.getNameFromKey(v.name())).toList());
    }

    /**
     * @param value The value to check
     * @return Null if the value is valid, otherwise the error message
     */
    @Override
    public @Nullable String validate(List<T> value) {
        return null;
    }

    @Override
    public ClickableItem buildClickableItem(ItemCreator creator, SmartInventory origin) {
        // todo add a menu for this
        creator.addLongLore("Please modify this setting using the config file. Modifying it via the menu is not available yet.", "§c", 60);
        return ClickableItem.empty(creator.build());
    }
}
