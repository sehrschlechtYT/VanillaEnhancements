package yt.sehrschlecht.vanillaenhancements.config.options;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.gui.ChooseDyeColorMenu;
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator;
import yt.sehrschlecht.vanillaenhancements.utils.ModuleUtils;

import java.util.function.BiConsumer;

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

    /**
     * @param defaultValue  The default value of the option.
     * @param description   A markdown formatted description of the option.
     * @param updateHandler A consumer that takes the old and the new value of the option after an update (e.g. through the UI)
     */
    public DyeColorOption(DyeColor defaultValue, @Nullable String description, @Nullable BiConsumer<DyeColor, DyeColor> updateHandler) {
        super(defaultValue, description, updateHandler, DyeColor.class);
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

    @Override
    public ClickableItem buildClickableItem(ItemCreator creator, SmartInventory origin) {
        creator.type(asWool());
        creator.addLore("§9§oLeft click §r§9to change the value");
        creator.addLore("§9Use §oshift + right click §r§9to reset the value");
        return ClickableItem.of(creator.build(), event -> {
            Player player = (Player) event.getWhoClicked();
            if (event.getClick().equals(ClickType.SHIFT_RIGHT)) {
                reset();
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1f, 1f);
            } else if (event.getClick().equals(ClickType.LEFT)) {
                ChooseDyeColorMenu.Companion.getInventory(VanillaEnhancements.getPlugin(), this, origin).open((Player) event.getWhoClicked());
            }
        });
    }

    protected Material asWool() {
        try {
            return Material.valueOf(get().name() + "_WOOL");
        } catch (IllegalArgumentException e) {
            return Material.WHITE_WOOL;
        }
    }

}
