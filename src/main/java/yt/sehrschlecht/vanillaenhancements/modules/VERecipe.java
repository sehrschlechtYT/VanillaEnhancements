package yt.sehrschlecht.vanillaenhancements.modules;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.Nullable;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public record VERecipe(NamespacedKey key, Recipe recipe, @Nullable Material discoverItem) {

}
