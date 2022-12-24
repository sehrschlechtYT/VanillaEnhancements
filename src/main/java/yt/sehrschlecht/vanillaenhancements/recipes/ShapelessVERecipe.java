package yt.sehrschlecht.vanillaenhancements.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.schlechteutils.data.Pair;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class ShapelessVERecipe extends VERecipe {
    private final Supplier<List<Pair<Material, Integer>>> inputs;

    public ShapelessVERecipe(NamespacedKey key, @Nullable Material discoverItem, Supplier<List<Pair<Material, Integer>>> inputs, Supplier<ItemStack> result) {
        super(key, discoverItem, result);
        this.inputs = inputs;
    }

    @Override
    public Recipe toBukkitRecipe() {
        ShapelessRecipe recipe = new ShapelessRecipe(getKey(), getResult().get());
        inputs.get().forEach(pair -> recipe.addIngredient(pair.getSecond(), pair.getFirst()));
        return recipe;
    }
}
