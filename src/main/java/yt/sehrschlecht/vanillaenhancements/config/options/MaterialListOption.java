package yt.sehrschlecht.vanillaenhancements.config.options;

import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class MaterialListOption extends EnumListConfigOption<Material> {

    /**
     * @param defaultValue The default value of the option.
     * @param description  A markdown formatted description of the option.
     */
    public MaterialListOption(List<Material> defaultValue, @Nullable String description) {
        super(defaultValue, description, Material.class);
    }

    /**
     * @param defaultValue  The default value of the option.
     * @param description   A markdown formatted description of the option.
     * @param updateHandler A consumer that takes the old and the new value of the option after an update (e.g. through the UI)
     */
    public MaterialListOption(List<Material> defaultValue, @Nullable String description, @Nullable BiConsumer<List<Material>, List<Material>> updateHandler) {
        super(defaultValue, description, updateHandler, Material.class);
    }

    @Override
    public String getPossibleValues() {
        return "[List of materials](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html)";
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
}
