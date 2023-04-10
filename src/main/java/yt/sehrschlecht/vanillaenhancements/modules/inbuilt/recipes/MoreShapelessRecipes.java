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
public class MoreShapelessRecipes extends RecipeModule {
    public BooleanOption breadRecipe = new BooleanOption(true,
            "Controls if the bread recipe will be registered.");
    public BooleanOption paperRecipe = new BooleanOption(true,
            "Controls if the paper recipe will be registered.");
    public BooleanOption shulkerBoxRecipe = new BooleanOption(true,
            "Controls if the shulker box recipe will be registered.");

    public MoreShapelessRecipes() {
        super("Adds shapeless recipes for some items.", INBUILT);
    }

    @Override
    public void registerRecipes() {
        if (breadRecipe.get()) addBreadRecipe();
        if (paperRecipe.get()) addPaperRecipe();
        if (shulkerBoxRecipe.get()) addShulkerBoxRecipe();
    }

    private void addBreadRecipe() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "shapeless_bread");
        ShapelessRecipe recipe = new ShapelessRecipe(recipeKey, new ItemStack(Material.BREAD));
        recipe.addIngredient(Material.WHEAT);
        recipe.addIngredient(Material.WHEAT);
        recipe.addIngredient(Material.WHEAT);
        addRecipe(recipeKey, recipe, Material.WHEAT);
    }

    private void addPaperRecipe() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "shapeless_paper");
        ShapelessRecipe recipe = new ShapelessRecipe(recipeKey, new ItemStack(Material.PAPER, 3));
        recipe.addIngredient(Material.SUGAR_CANE);
        recipe.addIngredient(Material.SUGAR_CANE);
        recipe.addIngredient(Material.SUGAR_CANE);
        addRecipe(recipeKey, recipe, Material.SUGAR_CANE);
    }

    private void addShulkerBoxRecipe() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "shapeless_shulker_box");
        ShapelessRecipe recipe = new ShapelessRecipe(recipeKey, new ItemStack(Material.SHULKER_BOX));
        recipe.addIngredient(Material.SHULKER_SHELL);
        recipe.addIngredient(Material.SHULKER_SHELL);
        recipe.addIngredient(Material.CHEST);
        addRecipe(recipeKey, recipe, Material.SHULKER_SHELL);
    }

    @Override
    public @NotNull String getKey() {
        return "more_shapeless_recipes";
    }

    @Override
    public JavaPlugin getPlugin() {
        return getVEInstance();
    }

}
