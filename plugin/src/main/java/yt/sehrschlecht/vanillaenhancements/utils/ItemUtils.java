package yt.sehrschlecht.vanillaenhancements.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Locale;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class ItemUtils {
    public static boolean checkName(ItemStack stack, String name) {
        return stack.getType().name().contains(name.toUpperCase(Locale.ROOT));
    }

    public static void damageItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if(!(meta instanceof Damageable)) return;
        Damageable damageable = (Damageable) meta;
        damageable.setDamage(damageable.getDamage() + 1);
        item.setItemMeta(meta);
    }
}
