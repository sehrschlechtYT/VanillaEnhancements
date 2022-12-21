package yt.sehrschlecht.vanillaenhancements.modules.inbuilt;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.config.Option;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class RottenFleshSmelting extends VEModule {
    @Option
    public ConfigOption experience = new ConfigOption("experience", getModuleKey(), 0.1D);
    @Option
    public ConfigOption cookingTime = new ConfigOption("cookingTime", getModuleKey(), 5);

    @Override
    public @NotNull String getKey() {
        return "rotten_flesh_smelting";
    }

    @Override
    public void onEnable() {
        FurnaceRecipe recipe = new FurnaceRecipe(getModuleKey(), new ItemStack(Material.LEATHER), Material.ROTTEN_FLESH, (float) experience.asDouble(), cookingTime.asInt() * 20);
        Bukkit.addRecipe(recipe);
    }
}
