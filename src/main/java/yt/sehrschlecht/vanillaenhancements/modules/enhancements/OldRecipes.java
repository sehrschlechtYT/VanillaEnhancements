package yt.sehrschlecht.vanillaenhancements.modules.enhancements;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.config.Option;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.ticking.TickService;

public class OldRecipes extends VEModule {
    @Option public ConfigOption enchantedGoldenAppleRecipe = new ConfigOption("enchanted_golden_apple_recipe", getKey(), false);
    @Option public ConfigOption useIngotsForEnchantedGoldenApple = new ConfigOption("enchanted_golden_apple_use_ingots", getKey(), false);
    @Option public ConfigOption oldGoldenAppleRecipe = new ConfigOption("old_golden_apple_recipe", getKey(), false);
    @Option public ConfigOption oldChainmailArmorRecipe = new ConfigOption("old_chainmail_armor_recipe", getKey(), false);

    @Override
    public @NotNull String getName() {
        return "Old recipes";
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return new NamespacedKey(getPlugin(), "old_recipes");
    }

    @Override
    public void onEnable() {
        if(enchantedGoldenAppleRecipe.asBoolean()) {
            initEnchantedGoldenAppleRecipe();
        }
        if(oldGoldenAppleRecipe.asBoolean()) {
            initOldGoldenAppleRecipe();
        }
        if(oldChainmailArmorRecipe.asBoolean()) {
            initChainmailHelmetRecipe();
            initChainmailChestplateRecipe();
            initChainmailLeggingsRecipe();
            initChainmailBootsRecipe();
        }
    }

    private void initEnchantedGoldenAppleRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "enchanted_golden_apple"), new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
        recipe.shape("GGG", "GAG", "GGG");
        recipe.setIngredient('G', useIngotsForEnchantedGoldenApple.asBoolean() ? Material.GOLD_INGOT : Material.GOLD_BLOCK);
        recipe.setIngredient('A', Material.GOLDEN_APPLE);
        Bukkit.addRecipe(recipe);
    }

    private void initOldGoldenAppleRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "old_golden_apple"), new ItemStack(Material.GOLDEN_APPLE));
        recipe.shape("GGG", "GAG", "GGG");
        recipe.setIngredient('G', Material.GOLD_NUGGET);
        recipe.setIngredient('A', Material.GOLDEN_APPLE);
        Bukkit.addRecipe(recipe);
    }

    private void initChainmailHelmetRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "chainmail_helmet"), new ItemStack(Material.CHAINMAIL_HELMET));
        recipe.shape("FFF", "F F");
        recipe.setIngredient('F', Material.FIRE);
        Bukkit.addRecipe(recipe);
    }

    private void initChainmailChestplateRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "chainmail_chestplate"), new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        recipe.shape("F F", "FFF", "FFF");
        recipe.setIngredient('F', Material.FIRE);
        Bukkit.addRecipe(recipe);
    }

    private void initChainmailLeggingsRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "chainmail_leggings"), new ItemStack(Material.CHAINMAIL_LEGGINGS));
        recipe.shape("FFF", "F F", "F F");
        recipe.setIngredient('F', Material.FIRE);
        Bukkit.addRecipe(recipe);
    }

    private void initChainmailBootsRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "chainmail_boots"), new ItemStack(Material.CHAINMAIL_BOOTS));
        recipe.shape("F F", "F F");
        recipe.setIngredient('F', Material.FIRE);
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
