package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes;

import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.modules.RecipeModule;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class RottenFleshSmelting extends RecipeModule {
    public ConfigOption experience = new ConfigOption(0.1D);
    public ConfigOption cookingTime = new ConfigOption(200);
    public ConfigOption resultAmount = new ConfigOption(1);

    @Override
    public @NotNull String getKey() {
        return "rotten_flesh_smelting";
    }

    @Override
    public void registerRecipes() {
        addRecipe(
            getModuleKey(),
            new FurnaceRecipe(
                getModuleKey(),
                new ItemStack(Material.LEATHER, resultAmount.asInt()),
                Material.ROTTEN_FLESH,
                (float) experience.asDouble(),
                cookingTime.asInt()
            ),
            Material.ROTTEN_FLESH
        );
    }
}
