package yt.sehrschlecht.vanillaenhancements.config.options;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator;

import java.util.function.BiConsumer;

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

    /**
     * @param defaultValue  The default value of the option.
     * @param description   A markdown formatted description of the option.
     * @param updateHandler A consumer that takes the old and the new value of the option after an update (e.g. through the UI)
     * @param typeClass     The class of the enum
     */
    public MaterialOption(Material defaultValue, @Nullable String description, @Nullable BiConsumer<Material, Material> updateHandler) {
        super(defaultValue, description, updateHandler, Material.class);
    }

    @Override
    public String getPossibleValues() {
        return "https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html#enum-constant-summary";
    }

    @Override
    public ClickableItem buildClickableItem(ItemCreator creator, SmartInventory origin) {
        creator.addLore("§cNot implemented yet!"); // ToDo: Implement
        return ClickableItem.of(creator.build(), event -> {

        });
    }

}
