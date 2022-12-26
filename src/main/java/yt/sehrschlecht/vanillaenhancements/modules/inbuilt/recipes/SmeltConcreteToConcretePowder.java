package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes;

import com.google.gson.annotations.Since;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.modules.RecipeModule;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
public class SmeltConcreteToConcretePowder extends RecipeModule {
    public ConfigOption experience = new ConfigOption(0, description);
    public ConfigOption cookingTime = new ConfigOption(200, description);

    @Override
    public @NotNull String getName() {
        return "Smelt concrete to concrete powder";
    }

    @Override
    public @NotNull String getKey() {
        return "smelt_concrete_to_concrete_powder";
    }

    @Override
    public void registerRecipes() {
        registerRecipe(Material.BLACK_CONCRETE, Material.BLACK_CONCRETE_POWDER);
        registerRecipe(Material.RED_CONCRETE, Material.RED_CONCRETE_POWDER);
        registerRecipe(Material.GREEN_CONCRETE, Material.GREEN_CONCRETE_POWDER);
        registerRecipe(Material.BROWN_CONCRETE, Material.BROWN_CONCRETE_POWDER);
        registerRecipe(Material.BLUE_CONCRETE, Material.BLUE_CONCRETE_POWDER);
        registerRecipe(Material.PURPLE_CONCRETE, Material.PURPLE_CONCRETE_POWDER);
        registerRecipe(Material.CYAN_CONCRETE, Material.CYAN_CONCRETE_POWDER);
        registerRecipe(Material.LIGHT_GRAY_CONCRETE, Material.LIGHT_GRAY_CONCRETE_POWDER);
        registerRecipe(Material.GRAY_CONCRETE, Material.GRAY_CONCRETE_POWDER);
        registerRecipe(Material.PINK_CONCRETE, Material.PINK_CONCRETE_POWDER);
        registerRecipe(Material.LIME_CONCRETE, Material.LIME_CONCRETE_POWDER);
        registerRecipe(Material.YELLOW_CONCRETE, Material.YELLOW_CONCRETE_POWDER);
        registerRecipe(Material.LIGHT_BLUE_CONCRETE, Material.LIGHT_BLUE_CONCRETE_POWDER);
        registerRecipe(Material.MAGENTA_CONCRETE, Material.MAGENTA_CONCRETE_POWDER);
        registerRecipe(Material.ORANGE_CONCRETE, Material.ORANGE_CONCRETE_POWDER);
        registerRecipe(Material.WHITE_CONCRETE, Material.WHITE_CONCRETE_POWDER);
    }

    private void registerRecipe(Material concrete, Material powder) {
        NamespacedKey key = new NamespacedKey(getPlugin(), "smelt_" + concrete.name().toLowerCase());
        FurnaceRecipe recipe = new FurnaceRecipe(
                key,
                new ItemStack(powder),
                concrete,
                experience.asInt(),
                cookingTime.asInt()
        );
        addRecipe(key, recipe, concrete);
    }
}
