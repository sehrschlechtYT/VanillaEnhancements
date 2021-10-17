package yt.sehrschlecht.vanillaenhancements.utils;

import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class ItemUtils {
    public static boolean checkName(ItemStack stack, String name) {
        return stack.getType().name().contains(name.toUpperCase(Locale.ROOT));
    }
}
