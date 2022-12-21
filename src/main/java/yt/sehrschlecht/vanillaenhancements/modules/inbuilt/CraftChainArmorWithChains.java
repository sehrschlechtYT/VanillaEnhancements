package yt.sehrschlecht.vanillaenhancements.modules.inbuilt;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.ticking.TickService;

public class CraftChainArmorWithChains extends VEModule {
    @Override
    public @NotNull String getName() {
        return "Craft chain armor with chains";
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return new NamespacedKey(getPlugin(), "craft_chain_armor_with_chains");
    }

    @Override
    public void onEnable() {
        initChainmailHelmetRecipe();
        initChainmailChestplateRecipe();
        initChainmailLeggingsRecipe();
        initChainmailBootsRecipe();
    }

    @Override
    public void onDisable() {

    }

    private void initChainmailHelmetRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "chainmail_helmet"), new ItemStack(Material.CHAINMAIL_HELMET));
        recipe.shape("CCC", "C C");
        recipe.setIngredient('C', Material.CHAIN);
        Bukkit.addRecipe(recipe);
    }

    private void initChainmailChestplateRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "chainmail_chestplate"), new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        recipe.shape("C C", "CCC", "CCC");
        recipe.setIngredient('C', Material.CHAIN);
        Bukkit.addRecipe(recipe);
    }

    private void initChainmailLeggingsRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "chainmail_leggings"), new ItemStack(Material.CHAINMAIL_LEGGINGS));
        recipe.shape("CCC", "C C", "C C");
        recipe.setIngredient('C', Material.CHAIN);
        Bukkit.addRecipe(recipe);
    }

    private void initChainmailBootsRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "chainmail_boots"), new ItemStack(Material.CHAINMAIL_BOOTS));
        recipe.shape("C C", "C C");
        recipe.setIngredient('C', Material.CHAIN);
        Bukkit.addRecipe(recipe);
    }

    @Override
    public @Nullable TickService getTickService() {
        return null;
    }
}
