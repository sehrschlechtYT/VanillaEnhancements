package yt.sehrschlecht.vanillaenhancements.modules.inbuilt;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.config.Option;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.ticking.TickService;

public class OldRecipes extends VEModule {
    @Option public ConfigOption enchantedGoldenAppleRecipe = new ConfigOption("enchanted_golden_apple_recipe", getModuleKey(), false);
    @Option public ConfigOption oldGoldenAppleRecipe = new ConfigOption("old_golden_apple_recipe", getModuleKey(), false);
    @Option public ConfigOption horseArmorRecipes = new ConfigOption("horse_armor_recipes", getModuleKey(), false);

    private RecipeChoice.MaterialChoice woolChoice;

    @Override
    public @NotNull String getName() {
        return "Old recipes";
    }

    @Override
    public @NotNull NamespacedKey getModuleKey() {
        return new NamespacedKey(getPlugin(), "old_recipes");
    }

    @Override
    public void onEnable() {
        woolChoice = new RecipeChoice.MaterialChoice(
                Material.WHITE_WOOL, Material.ORANGE_WOOL, Material.MAGENTA_WOOL, Material.LIGHT_BLUE_WOOL, Material.YELLOW_WOOL, Material.LIME_WOOL,
                Material.PINK_WOOL, Material.GRAY_WOOL, Material.LIGHT_GRAY_WOOL, Material.CYAN_WOOL, Material.PURPLE_WOOL, Material.BLUE_WOOL,
                Material.BROWN_WOOL, Material.GREEN_WOOL, Material.RED_WOOL, Material.BLACK_WOOL
        );

        if(enchantedGoldenAppleRecipe.asBoolean()) {
            initEnchantedGoldenAppleRecipe();
        }
        if(oldGoldenAppleRecipe.asBoolean()) {
            initOldGoldenAppleRecipe();
        }
        if(horseArmorRecipes.asBoolean()) {
            initLeatherHorseArmorRecipe();
            initIronHorseArmorRecipe();
            initGoldenHorseArmorRecipe();
            initDiamondHorseArmorRecipe();
        }
    }

    private void initEnchantedGoldenAppleRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "enchanted_golden_apple"), new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
        recipe.shape("GGG", "GAG", "GGG");
        recipe.setIngredient('G', Material.GOLD_BLOCK);
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

    private void initLeatherHorseArmorRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "leather_horse_armor"), new ItemStack(Material.LEATHER_HORSE_ARMOR));
        recipe.shape("  L", "LWL", "LLL");
        recipe.setIngredient('L', Material.LEATHER);
        recipe.setIngredient('W', woolChoice);
        Bukkit.addRecipe(recipe);
    }

    private void initIronHorseArmorRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "iron_horse_armor"), new ItemStack(Material.IRON_HORSE_ARMOR));
        recipe.shape("  L", "LWL", "LLL");
        recipe.setIngredient('L', Material.IRON_INGOT);
        recipe.setIngredient('W', woolChoice);
        Bukkit.addRecipe(recipe);
    }

    private void initGoldenHorseArmorRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "gold_horse_armor"), new ItemStack(Material.GOLDEN_HORSE_ARMOR));
        recipe.shape("  L", "LWL", "LLL");
        recipe.setIngredient('L', Material.GOLD_INGOT);
        recipe.setIngredient('W', woolChoice);
        Bukkit.addRecipe(recipe);
    }

    private void initDiamondHorseArmorRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "diamond_horse_armor"), new ItemStack(Material.DIAMOND_HORSE_ARMOR));
        recipe.shape("  L", "LWL", "LLL");
        recipe.setIngredient('L', Material.DIAMOND);
        recipe.setIngredient('W', woolChoice);
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
