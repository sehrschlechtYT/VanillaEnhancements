package yt.sehrschlecht.vanillaenhancements.modules;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import yt.sehrschlecht.vanillaenhancements.recipes.VERecipe;
import yt.sehrschlecht.vanillaenhancements.ticking.Tick;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public abstract class RecipeModule extends VEModule {
    protected final List<VERecipe> recipes = new ArrayList<>();
    protected boolean shouldCheckRecipes = true;

    @Override
    public void initialize() {
        registerRecipes();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        recipes.forEach(VERecipe::register);
        shouldCheckRecipes = getConfig().getDocument().getBoolean("recipes.discover");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        recipes.forEach(VERecipe::unregister);
    }

    public abstract void registerRecipes();

    /**
     * Adds a recipe to the module.
     * <b>WARNING: Call only in {@link #registerRecipes()}!</b>
     * @param key The key of the recipe
     * @param recipe The recipe
     */
    public void addRecipe(VERecipe recipe) {
        recipes.add(recipe);
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
                .filter(recipe -> recipe.getDiscoverItem() != null)
                .filter(recipe -> collectedItem == recipe.getDiscoverItem())
                .forEach(recipe -> {
                    if(!player.hasDiscoveredRecipe(recipe.getKey())) {
                        player.discoverRecipe(recipe.getKey());
                    }
                });
    }
}
