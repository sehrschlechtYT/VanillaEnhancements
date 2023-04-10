package yt.sehrschlecht.vanillaenhancements.config.options;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.utils.ModuleUtils;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class DyeColorOption extends EnumConfigOption<DyeColor> {

    /**
     * @param defaultValue The default value of the option.
     * @param description  A markdown formatted description of the option.
     */
    public DyeColorOption(DyeColor defaultValue, @Nullable String description) {
        super(defaultValue, description, DyeColor.class);
    }
    @Override
    public String getPossibleValues() {
        return "[List of materials](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/DyeColor.html#enum-constant-summary)";
    }

    @Override
    public String valueToDisplayString(DyeColor value) {
        ChatColor color;
        try {
            color = ChatColor.valueOf(value.name());
        } catch (IllegalArgumentException e) {
            color = ChatColor.WHITE;
        }
        return color + ModuleUtils.getNameFromKey(value.name());
    }

}
