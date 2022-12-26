package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes;

import com.google.gson.annotations.Since;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.modules.RecipeModule;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
public class CraftCoalToBlackDye extends RecipeModule {
    public ConfigOption charCoalToBlackDye = new ConfigOption(true);
    public ConfigOption coalToBlackDye = new ConfigOption(true);

    @Override
    public void registerRecipes() {
        if(charCoalToBlackDye.asBoolean()) registerCharcoalRecipe();
        if(coalToBlackDye.asBoolean()) registerCoalRecipe();
    }

    private void registerCharcoalRecipe() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "charcoal_to_black_dye");
        ShapelessRecipe recipe = new ShapelessRecipe(recipeKey, new ItemStack(Material.BLACK_DYE));
        recipe.addIngredient(Material.CHARCOAL);
        addRecipe(recipeKey, recipe, Material.CHARCOAL);
    }

    private void registerCoalRecipe() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "coal_to_black_dye");
        ShapelessRecipe recipe = new ShapelessRecipe(recipeKey, new ItemStack(Material.BLACK_DYE));
        recipe.addIngredient(Material.COAL);
        addRecipe(recipeKey, recipe, Material.COAL);
    }

    @Override
    public @NotNull String getKey() {
        return "craft_coal_to_black_dye";
    }
}
