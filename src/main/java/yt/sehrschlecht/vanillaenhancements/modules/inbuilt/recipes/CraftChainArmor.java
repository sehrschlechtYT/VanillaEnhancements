package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes;

import com.google.gson.annotations.Since;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.modules.RecipeModule;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
public class CraftChainArmor extends RecipeModule {
    public ConfigOption chainHelmet = new ConfigOption(true);
    public ConfigOption chainChestplate = new ConfigOption(true);
    public ConfigOption chainLeggings = new ConfigOption(true);
    public ConfigOption chainBoots = new ConfigOption(true);

    @Override
    public @NotNull String getKey() {
        return "chain_armor_crafting";
    }

    @Override
    public void registerRecipes() {
        if(chainHelmet.asBoolean()) addChainRecipe("chain_helmet", Material.CHAINMAIL_HELMET, "CCC", "C C");
        if(chainChestplate.asBoolean()) addChainRecipe("chain_chestplate", Material.CHAINMAIL_CHESTPLATE, "C C", "CCC", "CCC");
        if(chainLeggings.asBoolean()) addChainRecipe("chain_leggings", Material.CHAINMAIL_LEGGINGS, "CCC", "C C", "C C");
        if(chainBoots.asBoolean()) addChainRecipe("chain_boots", Material.CHAINMAIL_BOOTS, "C C", "C C");
    }

    private void addChainRecipe(String key, Material result, String... shape) {
        NamespacedKey recipeKey = new NamespacedKey(VanillaEnhancements.getPlugin(), key);
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(result));
        recipe.shape(shape);
        recipe.setIngredient('C', Material.CHAIN);
        addRecipe(recipeKey, recipe, Material.CHAIN);
    }
}
