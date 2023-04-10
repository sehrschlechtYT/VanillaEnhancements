package yt.sehrschlecht.vanillaenhancements.utils;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

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

    @NotNull
    public static String beautifyLowerCamelCase(@NotNull String key) {
        key = key.replaceAll("([a-z])([A-Z])", "$1 $2");
        key = key.replaceAll("([A-Z])([A-Z][a-z])", "$1 $2");
        return key;
    }

}
