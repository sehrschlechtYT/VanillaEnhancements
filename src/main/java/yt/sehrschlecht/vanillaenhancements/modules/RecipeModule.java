package yt.sehrschlecht.vanillaenhancements.modules;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.ticking.Tick;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public abstract class RecipeModule extends VEModule {
    protected final List<VERecipe> recipes = new ArrayList<>();

    @Override
    public void initialize() {
        registerRecipes();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        recipes.forEach(recipe -> Bukkit.addRecipe(recipe.recipe()));
    }

    @Override
    public void onDisable() {
        super.onDisable();
        recipes.forEach(recipe -> Bukkit.removeRecipe(recipe.key()));
    }

    public abstract void registerRecipes();

    /**
     * Adds a recipe to the module.
     * <b>WARNING: Call only in {@link #registerRecipes()}!</b>
     * @param key The key of the recipe
     * @param recipe The recipe
     */
    public void addRecipe(NamespacedKey key, Recipe recipe, @Nullable Material discoverItem) {
        recipes.add(new VERecipe(key, recipe, discoverItem));
    }

    @Tick(period = 60, executeNow = true)
    public void checkRecipes() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (ItemStack stack : player.getInventory().getContents()) {
                if(stack == null) continue;
                if(!stack.getType().isItem()) continue;
                discoverRecipes(player, stack.getType());
            }
        }
    }

    private void discoverRecipes(Player player, Material collectedItem) {
        recipes.stream()
                .filter(recipe -> recipe.discoverItem() != null)
                .filter(recipe -> collectedItem == recipe.discoverItem())
                .forEach(recipe -> {
                    if(!player.hasDiscoveredRecipe(recipe.key())) {
                        player.discoverRecipe(recipe.key());
                    }
                });
    }
}
