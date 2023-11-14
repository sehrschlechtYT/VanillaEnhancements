package yt.sehrschlecht.vanillaenhancements.config.options;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator;
import yt.sehrschlecht.vanillaenhancements.utils.ModuleUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class MaterialListOption extends ConfigOption<List<Material>> { // ToDo make it a EnumListConfigOption

    /**
     * @param defaultValue The default value of the option.
     * @param description  A markdown formatted description of the option.
     */
    public MaterialListOption(List<Material> defaultValue, @Nullable String description) {
        super(defaultValue, description);
    }

    /**
     * @param defaultValue  The default value of the option.
     * @param description   A markdown formatted description of the option.
     * @param updateHandler A consumer that takes the old and the new value of the option after an update (e.g. through the UI)
     */
    public MaterialListOption(List<Material> defaultValue, @Nullable String description, @Nullable BiConsumer<List<Material>, List<Material>> updateHandler) {
        super(defaultValue, description, updateHandler);
    }

    public static MaterialListOption fromStrings(List<String> strings, String description) {
        return new MaterialListOption(strings.stream().map(s -> {
            try {
                return Material.valueOf(s.toUpperCase());
            } catch (IllegalArgumentException e) {
                return null;
            }
        }).filter(Objects::nonNull).toList(), description);
    }

    @Override
    public List<Material> getFromConfig() {
        return Config.getInstance().getDocument().getStringList(toPath())
                .stream().map(s -> {
                    try {
                        return Material.valueOf(s.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        VanillaEnhancements.getPlugin().getLogger().severe("Invalid material: " + s);
                        return null;
                    }
                }).filter(Objects::nonNull).toList();
    }

    @Override
    public void set(List<Material> value) {
        setToObject(toStringList(value), value);
    }

    public List<String> toStringList(List<Material> list) {
        return list.stream().map(Material::name).toList();
    }

    @Override
    public String getPossibleValues() {
        return "[List of materials](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html)";
    }

    @Override
    public String valueToDisplayString(List<Material> value) {
        return toStringList(value).stream().map(ModuleUtils::getNameFromKey).collect(Collectors.joining(", "));
    }

    /**
     * @param value The value to check
     * @return Null if the value is valid, otherwise the error message
     */
    @Override
    public @Nullable String validate(List<Material> value) {
        return null;
    }

    @Override
    public ClickableItem buildClickableItem(ItemCreator creator, SmartInventory origin) {
        // ToDo
        creator.displayName("§cNot implemented yet");
        return ClickableItem.of(creator.build(), event -> {

        });
    }

}
