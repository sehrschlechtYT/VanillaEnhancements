package yt.sehrschlecht.vanillaenhancements.config.options;

import org.bukkit.DyeColor;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class DyeColorOption extends EnumConfigOption<DyeColor> {

    /**
     * @param defaultValue The default value of the option.
     * @param description  A markdown formatted description of the option.
     */
    public DyeColorOption(DyeColor defaultValue, String description) {
        super(defaultValue, description, DyeColor.class);
    }
    @Override
    public String getPossibleValues() {
        return "[List of materials](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/DyeColor.html#enum-constant-summary)";
    }



}
