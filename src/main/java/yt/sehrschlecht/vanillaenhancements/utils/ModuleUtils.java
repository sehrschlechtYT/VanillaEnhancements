package yt.sehrschlecht.vanillaenhancements.utils;

import org.bukkit.NamespacedKey;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class ModuleUtils {
    public static String getNameFromKey(NamespacedKey key) {
        String name = key.getKey();
        name = name.replace("_", " ");
        // Capitalize first letter
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }
}
