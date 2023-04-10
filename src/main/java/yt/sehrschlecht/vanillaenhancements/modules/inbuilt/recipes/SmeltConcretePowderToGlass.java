package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes;

import com.google.gson.annotations.Since;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.config.options.FloatOption;
import yt.sehrschlecht.vanillaenhancements.config.options.IntegerOption;
import yt.sehrschlecht.vanillaenhancements.modules.RecipeModule;
import yt.sehrschlecht.vanillaenhancements.utils.docs.Source;

import java.util.Arrays;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
@Source("https://vanillatweaks.net/")
public class SmeltConcretePowderToGlass extends RecipeModule {
    public FloatOption experience = new FloatOption(0f,
            "The experience given by the recipe", 0f, null);
    public IntegerOption cookingTime = new IntegerOption(200,
            "The cooking time of the recipe in ticks", 1, null);

    public SmeltConcretePowderToGlass() {
        super("Allows players to smelt concrete powder into stained glass.", INBUILT);
    }

    @Override
    public @NotNull String getName() {
        return "Smelt concrete powder to glass";
    }

    @Override
    public @NotNull String getKey() {
        return "smelt_concrete_powder_to_glass";
    }

    @Override
    public void registerRecipes() {
        Arrays.stream(Material.values())
            .filter(material -> material.name().endsWith("_CONCRETE_POWDER"))
            .forEach(powder -> {
                Material glass = Material.valueOf(powder.name().replace("_CONCRETE_POWDER", "_STAINED_GLASS"));
                NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "smelt_" + powder.name().toLowerCase() + "_to_glass");
                FurnaceRecipe recipe = new FurnaceRecipe(
                        recipeKey,
                        new ItemStack(glass),
                        powder,
                        experience.get(),
                        cookingTime.get()
                );
                addRecipe(recipeKey, recipe, powder);
            });
    }

    @Override
    public JavaPlugin getPlugin() {
        return getVEInstance();
    }

}
