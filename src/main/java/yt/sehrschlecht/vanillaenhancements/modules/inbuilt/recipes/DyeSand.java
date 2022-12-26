package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes;

import com.google.gson.annotations.Since;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.config.options.BooleanOption;
import yt.sehrschlecht.vanillaenhancements.modules.RecipeModule;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
public class DyeSand extends RecipeModule {
    public BooleanOption dyeSandRed = new BooleanOption(true,
            "The amount of blocks that players will receive");

    public BooleanOption dyeSandstoneRed = new BooleanOption(true,
            "Controls if the craft sandstone to red sandstone crafting recipe will be registered");
    public BooleanOption dyeRedSandWhite = new BooleanOption(true,
            "Controls if the craft red sand to sand crafting recipe will be registered");
    public BooleanOption dyeRedSandstoneWhite = new BooleanOption(true,
            "Controls if the craft red sandstone to sandstone crafting recipe will be registered");

    @Override
    public void registerRecipes() {
        if(dyeSandRed.get()) addDyeSandRedRecipe();
        if(dyeSandstoneRed.get()) addDyeSandstoneRedRecipe();
        if(dyeRedSandWhite.get()) addDyeRedSandWhiteRecipe();
        if(dyeRedSandstoneWhite.get()) addDyeRedSandstoneWhiteRecipe();
    }

    private void addDyeSandRedRecipe() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "dye_sand_red");
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(Material.RED_SAND));
        recipe.shape("SSS", "SDS", "SSS");
        recipe.setIngredient('S', Material.SAND);
        recipe.setIngredient('D', Material.RED_DYE);
        addRecipe(recipeKey, recipe, Material.SAND);
    }

    private void addDyeSandstoneRedRecipe() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "dye_sandstone_red");
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(Material.RED_SANDSTONE));
        recipe.shape("SSS", "SDS", "SSS");
        recipe.setIngredient('S', Material.SANDSTONE);
        recipe.setIngredient('D', Material.RED_DYE);
        addRecipe(recipeKey, recipe, Material.SANDSTONE);
    }

    private void addDyeRedSandWhiteRecipe() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "dye_red_sand_white");
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(Material.SAND));
        recipe.shape("SSS", "SDS", "SSS");
        recipe.setIngredient('S', Material.RED_SAND);
        recipe.setIngredient('D', Material.WHITE_DYE);
        addRecipe(recipeKey, recipe, Material.RED_SAND);
    }

    private void addDyeRedSandstoneWhiteRecipe() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "dye_red_sandstone_white");
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(Material.SANDSTONE));
        recipe.shape("SSS", "SDS", "SSS");
        recipe.setIngredient('S', Material.RED_SANDSTONE);
        recipe.setIngredient('D', Material.WHITE_DYE);
        addRecipe(recipeKey, recipe, Material.RED_SANDSTONE);
    }

    @Override
    public @NotNull String getKey() {
        return "dye_sand";
    }
}
