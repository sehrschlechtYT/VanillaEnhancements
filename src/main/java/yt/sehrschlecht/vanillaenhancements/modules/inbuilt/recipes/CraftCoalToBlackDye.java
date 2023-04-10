package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes;

import com.google.gson.annotations.Since;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.config.options.BooleanOption;
import yt.sehrschlecht.vanillaenhancements.modules.RecipeModule;
import yt.sehrschlecht.vanillaenhancements.utils.docs.Source;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
@Source("https://vanillatweaks.net")
public class CraftCoalToBlackDye extends RecipeModule {
    public BooleanOption charCoalToBlackDye = new BooleanOption(true,
            "Controls if the charcoal to black dye crafting recipe will be registered");
    public BooleanOption coalToBlackDye = new BooleanOption(true,
            "Controls if the coal to black dye crafting recipe will be registered");

    public CraftCoalToBlackDye() {
        super("Allows players to craft black dye from coal and charcoal.", INBUILT);
    }

    @Override
    public void registerRecipes() {
        if (charCoalToBlackDye.get()) registerCharcoalRecipe();
        if (coalToBlackDye.get()) registerCoalRecipe();
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

    @Override
    public JavaPlugin getPlugin() {
        return getVEInstance();
    }

}
