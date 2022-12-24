package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.config.Option;
import yt.sehrschlecht.vanillaenhancements.modules.RecipeModule;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class DyeSand extends RecipeModule {
    @Option
    public ConfigOption dyeSandRed = new ConfigOption("dyeSandRed", getModuleKey(), true);
    @Option
    public ConfigOption dyeSandstoneRed = new ConfigOption("dyeSandstoneRed", getModuleKey(), true);
    @Option
    public ConfigOption dyeRedSandWhite = new ConfigOption("dyeRedSandWhite", getModuleKey(), true);
    @Option
    public ConfigOption dyeRedSandstoneWhite = new ConfigOption("dyeRedSandstoneWhite", getModuleKey(), true);

    @Override
    public void registerRecipes() {
        if(dyeSandRed.asBoolean()) addDyeSandRedRecipe();
        if(dyeSandstoneRed.asBoolean()) addDyeSandstoneRedRecipe();
        if(dyeRedSandWhite.asBoolean()) addDyeRedSandWhiteRecipe();
        if(dyeRedSandstoneWhite.asBoolean()) addDyeRedSandstoneWhiteRecipe();
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
