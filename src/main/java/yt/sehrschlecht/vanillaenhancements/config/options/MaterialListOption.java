package yt.sehrschlecht.vanillaenhancements.config.options;

import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;

import java.util.List;
import java.util.Objects;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class MaterialListOption extends ConfigOption<List<Material>> { // ToDo make it a EnumListConfigOption

    /**
     * @param defaultValue The default value of the option.
     * @param description  A markdown formatted description of the option.
     */
    public MaterialListOption(List<Material> defaultValue, String description) {
        super(defaultValue, description);
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
        Config.getInstance().set(this, toStringList(value));
    }

    public List<String> toStringList(List<Material> list) {
        return list.stream().map(Material::name).toList();
    }

    @Override
    public String getPossibleValues() {
        return "[List of materials](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html)";
    }

    /**
     * @param value The value to check
     * @return Null if the value is valid, otherwise the error message
     */
    @Override
    public @Nullable String validate(List<Material> value) {
        return null;
    }

}
