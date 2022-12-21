package yt.sehrschlecht.vanillaenhancements.utils;

import org.bukkit.inventory.ItemStack;

import java.util.Locale;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class ItemUtils {
    public static boolean checkName(ItemStack stack, String name) {
        return stack.getType().name().contains(name.toUpperCase(Locale.ROOT));
    }
}
