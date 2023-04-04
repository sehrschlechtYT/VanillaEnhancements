package yt.sehrschlecht.vanillaenhancements.config.options;

import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class MaterialOption extends EnumConfigOption<Material> {

    /**
     * @param defaultValue The default value of the option.
     * @param description  A markdown formatted description of the option.
     */
    public MaterialOption(Material defaultValue, @Nullable String description) {
        super(defaultValue, description, Material.class);
    }

    @Override
    public String getPossibleValues() {
        return "https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html#enum-constant-summary";
    }

}
