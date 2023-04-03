package yt.sehrschlecht.vanillaenhancements.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import yt.sehrschlecht.schlechteutils.data.Pair;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.modules.VERecipe;
import yt.sehrschlecht.vanillaenhancements.utils.debugging.Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class RecipeManager {
    private final List<Pair<NamespacedKey, VERecipe>> recipes;

    public RecipeManager() {
        recipes = new ArrayList<>();
    }

    public void discoverRecipes() {
        long period = Config.getInstance().getDocument().getLong("recipe-discover-period", 60L);
        if(period <= 0) period = 60L;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(VanillaEnhancements.getPlugin(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                for (ItemStack stack : player.getInventory().getContents()) {
                    if(stack == null) continue;
                    if(!stack.getType().isItem()) continue;
                    discoverRecipes(player, stack.getType());
                }
            }
        }, 0L, period);
    }

    private List<VERecipe> getRegisteredRecipes() {
        return recipes.stream().map(Pair::getSecond).filter(VERecipe::isRegistered).toList();
    }

    private void discoverRecipes(Player player, Material collectedItem) {
        Debug.RECIPE_DISCOVERING.log("Discovering recipes for {} with item {}...", player.getName(), collectedItem);
        getRegisteredRecipes().stream()
                .filter(recipe -> recipe.discoverItem() != null)
                .filter(recipe -> collectedItem == recipe.discoverItem())
                .forEach(recipe -> {
                    Debug.RECIPE_DISCOVERING.log("Has player {} discovered recipe {}? {}", player.getName(), recipe.key(), player.hasDiscoveredRecipe(recipe.key()));
                    if(!player.hasDiscoveredRecipe(recipe.key())) {
                        player.discoverRecipe(recipe.key());
                        Debug.RECIPE_DISCOVERING.log("Discovered recipe {} for player {}.", recipe.key(), player.getName());
                    }
                });
    }

    public void addRecipe(NamespacedKey moduleKey, VERecipe recipe) {
        Debug.RECIPES.log("Adding recipe {} from module {}...", recipe.key(), moduleKey);
        recipes.add(new Pair<>(moduleKey, recipe));
    }

    public void removeRecipe(NamespacedKey moduleKey, NamespacedKey recipeKey) {
        Debug.RECIPES.log("Removing recipe {} from module {}...", recipeKey, moduleKey);
        recipes.stream()
                .filter(pair -> pair.getSecond().key().equals(recipeKey))
                .findFirst()
                .ifPresent(recipes::remove); //todo maybe unregister from bukkit (not sure if needed)
    }

    public List<VERecipe> getRecipes(VEModule module) {
        return recipes.stream()
                .filter(pair -> pair.getFirst().equals(module.getModuleKey()))
                .map(Pair::getSecond)
                .toList();
    }

    public List<Pair<NamespacedKey, VERecipe>> getRecipes() {
        return recipes;
    }

    public void clearRecipes(VEModule module) {
        recipes.removeIf(pair -> pair != null && pair.getFirst().equals(module.getModuleKey()));
    }
}
