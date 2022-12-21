package yt.sehrschlecht.vanillaenhancements.modules.inbuilt;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.StonecuttingRecipe;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class CobblestoneInStoneCutter extends VEModule {
    @Override
    public @NotNull String getName() {
        return "Craft cobblestone in stone cutter";
    }

    @Override
    public @NotNull String getKey() {
        return "cobblestone_stonecutter";
    }

    @Override
    public void onEnable() {
        StonecuttingRecipe recipe = new StonecuttingRecipe(getModuleKey(), new ItemStack(Material.COBBLESTONE), Material.STONE);
        Bukkit.addRecipe(recipe);
    }
}
