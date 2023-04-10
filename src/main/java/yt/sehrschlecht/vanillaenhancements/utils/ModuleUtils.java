package yt.sehrschlecht.vanillaenhancements.utils;

import org.bukkit.NamespacedKey;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class ModuleUtils {
    public static String getNameFromKey(NamespacedKey key) {
        return getNameFromKey(key.getKey());
    }

    public static String getNameFromKey(String key) {
        key = key.toLowerCase();
        key = key.replace("_", " ");
        key = key.replace("-", " ");
        // Capitalize first letter
        key = key.substring(0, 1).toUpperCase() + key.substring(1);
        return key;
    }
}
