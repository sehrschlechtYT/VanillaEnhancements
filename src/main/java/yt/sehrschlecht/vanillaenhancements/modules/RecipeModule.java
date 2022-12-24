package yt.sehrschlecht.vanillaenhancements.modules;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.ticking.Tick;
import yt.sehrschlecht.vanillaenhancements.utils.debugging.Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public abstract class RecipeModule extends VEModule {
    protected final List<VERecipe> recipes = new ArrayList<>();
    protected List<VERecipe> addedRecipes = new ArrayList<>();
    protected boolean shouldCheckRecipes = true;

    @Override
    public void initialize() {
        registerRecipes();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Debug.RECIPES.log("Adding recipes for module {}...", getModuleKey());;
        addedRecipes = recipes;
        recipes.forEach(recipe -> Bukkit.addRecipe(recipe.recipe()));
        shouldCheckRecipes = getConfig().getDocument().getBoolean("recipes.discover");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Debug.RECIPES.log("Removing all recipes of module {}...", getModuleKey());
        recipes.forEach(recipe -> Bukkit.removeRecipe(recipe.key()));
        Bukkit.resetRecipes();
    }

    protected void reloadRecipes() {
        Debug.RECIPES.log("Reloading recipes of module {}...", getModuleKey());
        addedRecipes.forEach(recipe -> {
            if(Bukkit.getRecipe(recipe.key()) == null) {
                Debug.RECIPES.log("Recipe {} was never added.", recipe.key());
            }
            boolean removed = Bukkit.removeRecipe(recipe.key());
            Debug.RECIPES.log("Removed recipe {} {}.", recipe.key(), removed ? "successfully" : "unsuccessfully");
        });
        recipes.clear();
        registerRecipes();
        recipes.forEach(recipe -> {
            Bukkit.addRecipe(recipe.recipe());
            Debug.RECIPES.log("Added recipe {}.", recipe.key());
        });
        addedRecipes = recipes;
    }

    @Override
    public void reload() {
        super.reload();
        reloadRecipes();
    }

    public abstract void registerRecipes();

    /**
     * Adds a recipe to the module.
     * <b>WARNING: Call only in {@link #registerRecipes()}!</b>
     * @param key The key of the recipe
     * @param recipe The recipe
     */
    public void addRecipe(NamespacedKey key, Recipe recipe, @Nullable Material discoverItem) {
        Debug.RECIPES.log("Adding recipe {} to module {}...", key, getModuleKey());
        recipes.add(new VERecipe(key, recipe, discoverItem));
    }

    public void removeRecipe(NamespacedKey key) {
        Debug.RECIPES.log("Removing recipe {} from module {}...", key, getModuleKey());
        recipes.removeIf(recipe -> recipe.key().equals(key));
    }

    @Tick(period = 60, executeNow = true)
    public void checkRecipes() { //ToDo add a recipe manager that collects all recipes and checks them -> less performance impact
        if(!shouldCheckRecipes) return;
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
                        Debug.RECIPES.log("Discovered recipe {} for player {}.", recipe.key(), player.getName());
                    }
                });
    }
}
