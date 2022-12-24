package yt.sehrschlecht.vanillaenhancements.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.schlechteutils.data.Pair;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public interface RecipeConstructor {
    default VERecipe shapeless(NamespacedKey key, @Nullable Material discoverItem, Supplier<List<Pair<Material, Integer>>> inputs, Supplier<ItemStack> result) {
        return new ShapelessVERecipe(key, discoverItem, inputs, result);
    }

    default VERecipe shapeless(NamespacedKey key, @Nullable Material discoverItem, List<Pair<Material, Integer>> inputs, ItemStack result) {
        return new ShapelessVERecipe(key, discoverItem, () -> inputs, () -> result);
    }

    default VERecipe shaped(NamespacedKey key, @Nullable Material discoverItem, Supplier<ItemStack> result, Supplier<String[]> shape, Supplier<Map<Character, Material>> ingredients) {
        return new ShapedVERecipe(key, discoverItem, result, shape, ingredients);
    }

    default VERecipe shaped(NamespacedKey key, @Nullable Material discoverItem, ItemStack result, String[] shape, Map<Character, Material> ingredients) {
        return new ShapedVERecipe(key, discoverItem, () -> result, () -> shape, () -> ingredients);
    }
}
