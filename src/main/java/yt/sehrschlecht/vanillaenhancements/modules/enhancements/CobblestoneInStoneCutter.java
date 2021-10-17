package yt.sehrschlecht.vanillaenhancements.modules.enhancements;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.StonecuttingRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.ticking.TickService;

public class CobblestoneInStoneCutter extends VEModule {
    @Override
    public @NotNull String getName() {
        return "Cobblestone in stone cutter";
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return new NamespacedKey(VanillaEnhancements.getPlugin(), "cobblestone_stonecutter");
    }

    @Override
    public void onEnable() {
        StonecuttingRecipe recipe = new StonecuttingRecipe(getKey(), new ItemStack(Material.COBBLESTONE), Material.STONE);
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
