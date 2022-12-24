package yt.sehrschlecht.vanillaenhancements.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class ShapedVERecipe extends VERecipe {
    private final Supplier<String[]> shape;
    private final Supplier<Map<Character, Material>> ingredients;

    public ShapedVERecipe(NamespacedKey key, @Nullable Material discoverItem, Supplier<ItemStack> result, Supplier<String[]> shape, Supplier<Map<Character, Material>> ingredients) {
        super(key, discoverItem, result);
        this.shape = shape;
        this.ingredients = ingredients;
    }

    @Override
    public Recipe toBukkitRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(getKey(), getResult().get());
        recipe.shape(shape.get());
        ingredients.get().forEach(recipe::setIngredient);
        return recipe;
    }
}
