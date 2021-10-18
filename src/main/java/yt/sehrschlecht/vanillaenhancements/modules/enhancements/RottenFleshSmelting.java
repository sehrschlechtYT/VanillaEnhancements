package yt.sehrschlecht.vanillaenhancements.modules.enhancements;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.config.Option;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.ticking.TickService;

public class RottenFleshSmelting extends VEModule {
    @Option
    public ConfigOption experience = new ConfigOption("experience", getKey(), 0.1D);
    @Option
    public ConfigOption cookingTime = new ConfigOption("cookingTime", getKey(), 5);

    @Override
    public @NotNull String getName() {
        return "Rotten flesh smelting";
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return new NamespacedKey(VanillaEnhancements.getPlugin(), "rotten_flesh_smelting");
    }

    @Override
    public void onEnable() {
        FurnaceRecipe recipe = new FurnaceRecipe(getKey(), new ItemStack(Material.LEATHER), Material.ROTTEN_FLESH, (float) experience.asDouble(), cookingTime.asInt() * 20);
        Bukkit.addRecipe(recipe);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public @Nullable TickService getTickService() {
        return null;
    }
}
