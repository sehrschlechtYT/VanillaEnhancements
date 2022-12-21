package yt.sehrschlecht.vanillaenhancements.modules;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public abstract class RecipeModule extends VEModule {
    protected final Map<NamespacedKey, Recipe> recipes = new HashMap<>();

    @Override
    public void initialize() {
        registerRecipes();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        recipes.values().forEach(Bukkit::addRecipe);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        recipes.keySet().forEach(Bukkit::removeRecipe);
    }

    public abstract void registerRecipes();

    /**
     * Adds a recipe to the module.
     * <b>WARNING: Call only in {@link #registerRecipes()}!</b>
     * @param key The key of the recipe
     * @param recipe The recipe
     */
    public void addRecipe(NamespacedKey key, Recipe recipe) {
        recipes.put(key, recipe);
    }
}
