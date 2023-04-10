package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes;

import com.google.gson.annotations.Since;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.StonecuttingRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.modules.RecipeModule;


/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
public class CobblestoneInStoneCutter extends RecipeModule {

    public CobblestoneInStoneCutter() {
        super("Allows players to craft cobblestone in a stone cutter.", INBUILT);
    }

    @Override
    public @NotNull String getName() {
        return "Craft cobblestone in stone cutter";
    }

    @Override
    public @NotNull String getKey() {
        return "cobblestone_stonecutter";
    }

    @Override
    public void registerRecipes() {
        addRecipe(getModuleKey(), new StonecuttingRecipe(getModuleKey(), new ItemStack(Material.COBBLESTONE), Material.STONE), Material.STONE);
    }

    @Override
    public JavaPlugin getPlugin() {
        return getVEInstance();
    }

}
