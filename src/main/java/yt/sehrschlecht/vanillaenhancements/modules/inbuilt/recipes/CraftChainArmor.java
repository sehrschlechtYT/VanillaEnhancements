package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes;

import com.google.gson.annotations.Since;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.options.BooleanOption;
import yt.sehrschlecht.vanillaenhancements.modules.RecipeModule;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
public class CraftChainArmor extends RecipeModule {
    public BooleanOption chainHelmet = new BooleanOption(true,
            "Controls if the chain helmet crafting recipe will be registered");
    public BooleanOption chainChestplate = new BooleanOption(true,
            "Controls if the chain chestplate crafting recipe will be registered");
    public BooleanOption chainLeggings = new BooleanOption(true,
            "Controls if the chain leggings crafting recipe will be registered");
    public BooleanOption chainBoots = new BooleanOption(true,
            "Controls if the chain boots crafting recipe will be registered");

    public CraftChainArmor() {
        super("Allows players to craft chain armor using chains.", INBUILT);
    }

    @Override
    public @NotNull String getKey() {
        return "chain_armor_crafting";
    }

    @Override
    public void registerRecipes() {
        if (chainHelmet.get()) addChainRecipe("chain_helmet", Material.CHAINMAIL_HELMET, "CCC", "C C");
        if (chainChestplate.get()) addChainRecipe("chain_chestplate", Material.CHAINMAIL_CHESTPLATE, "C C", "CCC", "CCC");
        if (chainLeggings.get()) addChainRecipe("chain_leggings", Material.CHAINMAIL_LEGGINGS, "CCC", "C C", "C C");
        if (chainBoots.get()) addChainRecipe("chain_boots", Material.CHAINMAIL_BOOTS, "C C", "C C");
    }

    private void addChainRecipe(String key, Material result, String... shape) {
        NamespacedKey recipeKey = new NamespacedKey(VanillaEnhancements.getPlugin(), key);
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(result));
        recipe.shape(shape);
        recipe.setIngredient('C', Material.CHAIN);
        addRecipe(recipeKey, recipe, Material.CHAIN);
    }

    @Override
    public JavaPlugin getPlugin() {
        return getVEInstance();
    }

}
