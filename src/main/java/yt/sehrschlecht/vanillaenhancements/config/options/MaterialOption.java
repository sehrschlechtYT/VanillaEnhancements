package yt.sehrschlecht.vanillaenhancements.config.options;

import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class MaterialOption extends ConfigOption<Material> {

    /**
     * @param defaultValue The default value of the option.
     * @param description  A markdown formatted description of the option.
     */
    public MaterialOption(Material defaultValue, String description) {
        super(defaultValue, description);
    }

    @Override
    public Material getFromConfig() {
        try {
            return Material.valueOf(Config.getInstance().getDocument().getString(toPath()).toUpperCase());
        } catch (IllegalArgumentException e) {
            VanillaEnhancements.getPlugin().getLogger().severe("Invalid material for option " + toPath() + ": " + Config.getInstance().getDocument().getString(toPath()));
            return defaultValue;
        }
    }

    @Override
    public void set(Material value) {
        Config.getInstance().set(this, value.name());
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
    public @Nullable String validate(Material value) {
        return null;
    }

}
