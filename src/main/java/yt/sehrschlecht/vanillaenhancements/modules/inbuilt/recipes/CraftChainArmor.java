package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.modules.RecipeModule;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class CraftChainArmor extends RecipeModule {

    @Override
    public @NotNull String getKey() {
        return "chain_armor_crafting";
    }

    @Override
    public void registerRecipes() {
        addChainRecipe("chain_helmet", Material.CHAINMAIL_HELMET, "CCC", "C C");
        addChainRecipe("chain_chestplate", Material.CHAINMAIL_CHESTPLATE, "C C", "CCC", "CCC");
        addChainRecipe("chain_leggings", Material.CHAINMAIL_LEGGINGS, "CCC", "C C", "C C");
        addChainRecipe("chain_boots", Material.CHAINMAIL_BOOTS, "C C", "C C");
    }

    private void addChainRecipe(String key, Material result, String... shape) {
        NamespacedKey recipeKey = new NamespacedKey(VanillaEnhancements.getPlugin(), key);
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(result));
        recipe.shape(shape);
        recipe.setIngredient('C', Material.CHAIN);
        addRecipe(recipeKey, recipe, Material.CHAIN);
    }
}
