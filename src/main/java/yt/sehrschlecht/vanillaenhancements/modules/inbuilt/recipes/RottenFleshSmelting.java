package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes;

import com.google.gson.annotations.Since;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.config.options.FloatOption;
import yt.sehrschlecht.vanillaenhancements.config.options.IntegerOption;
import yt.sehrschlecht.vanillaenhancements.modules.RecipeModule;
import yt.sehrschlecht.vanillaenhancements.utils.docs.Source;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
@Source("https://vanillatweaks.net")
public class RottenFleshSmelting extends RecipeModule {
    public FloatOption experience = new FloatOption(0.1f,
            "The experience given by the recipe", 0f, null);
    public IntegerOption cookingTime = new IntegerOption(200,
            "The cooking time of the recipe in ticks", 0, null);
    public IntegerOption resultAmount = new IntegerOption(1,
            "The amount of leather that is produced", 1, 64);

    public RottenFleshSmelting() {
        super("Allows players to smelt rotten flesh into leather.", INBUILT);
    }

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
                new ItemStack(Material.LEATHER, resultAmount.get()),
                Material.ROTTEN_FLESH,
                experience.get(),
                cookingTime.get()
            ),
            Material.ROTTEN_FLESH
        );
    }

    @Override
    public JavaPlugin getPlugin() {
        return getVEInstance();
    }

}
