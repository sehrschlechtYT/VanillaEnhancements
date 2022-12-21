package yt.sehrschlecht.vanillaenhancements.modules.inbuilt;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.ticking.TickService;

public class CraftChainArmor extends VEModule {
    @Override
    public @NotNull String getName() {
        return "Chain armor crafting";
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return new NamespacedKey(VanillaEnhancements.getPlugin(), "chain_armor_crafting");
    }

    @Override
    public void onEnable() {
        Bukkit.addRecipe(helmetCrafting());
        Bukkit.addRecipe(chestplateCrafting());
        Bukkit.addRecipe(leggingsCrafting());
        Bukkit.addRecipe(bootsCrafting());
    }

    private ShapedRecipe helmetCrafting() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(VanillaEnhancements.getPlugin(), "chain_helmet"), new ItemStack(Material.CHAINMAIL_HELMET));
        recipe.shape("CCC", "C C");
        recipe.setIngredient('C', Material.CHAIN);
        return recipe;
    }

    private ShapedRecipe chestplateCrafting() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(VanillaEnhancements.getPlugin(), "chain_chestplate"), new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        recipe.shape("C C", "CCC", "CCC");
        recipe.setIngredient('C', Material.CHAIN);
        return recipe;
    }

    private ShapedRecipe leggingsCrafting() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(VanillaEnhancements.getPlugin(), "chain_leggings"), new ItemStack(Material.CHAINMAIL_LEGGINGS));
        recipe.shape("CCC", "C C", "C C");
        recipe.setIngredient('C', Material.CHAIN);
        return recipe;
    }

    private ShapedRecipe bootsCrafting() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(VanillaEnhancements.getPlugin(), "chain_boots"), new ItemStack(Material.CHAINMAIL_BOOTS));
        recipe.shape("C C", "C C");
        recipe.setIngredient('C', Material.CHAIN);
        return recipe;
    }

    @Override
    public void onDisable() {

    }

    @Override
    public @Nullable TickService getTickService() {
        return null;
    }
}
