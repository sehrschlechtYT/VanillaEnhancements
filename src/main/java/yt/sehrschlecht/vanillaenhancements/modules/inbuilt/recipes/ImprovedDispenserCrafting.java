package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes;

import com.google.gson.annotations.Since;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.modules.RecipeModule;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
public class ImprovedDispenserCrafting extends RecipeModule {
    public ConfigOption dropperAndBow = new ConfigOption(true);
    public ConfigOption dropperAndStringsAndSticks = new ConfigOption(true);

    @Override
    public void registerRecipes() {
        if(dropperAndBow.asBoolean()) createDropperAndBowRecipe();
        if(dropperAndStringsAndSticks.asBoolean()) createDropperAndStringsAndSticksRecipe();
    }

    public void createDropperAndBowRecipe() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "dropper_and_bow");
        ShapelessRecipe recipe = new ShapelessRecipe(recipeKey, new ItemStack(Material.DISPENSER));
        recipe.addIngredient(Material.DROPPER);
        recipe.addIngredient(Material.BOW);
        addRecipe(recipeKey, recipe, Material.DROPPER);
    }

    public void createDropperAndStringsAndSticksRecipe() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "dropper_and_strings_and_sticks");
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(Material.DISPENSER));
        recipe.shape(" SF", "SDF", " SF");
        recipe.setIngredient('S', Material.STICK);
        recipe.setIngredient('F', Material.STRING);
        recipe.setIngredient('D', Material.DROPPER);
        addRecipe(recipeKey, recipe, Material.DROPPER);
    }

    @Override
    public @NotNull String getKey() {
        return "improved_dispenser_crafting";
    }
}
