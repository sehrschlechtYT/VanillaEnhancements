package yt.sehrschlecht.vanillaenhancements.modules.enhancements;

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
    @Option public ConfigOption enchantedGoldenAppleRecipe = new ConfigOption("enchanted_golden_apple_recipe", getKey(), false);
    @Option public ConfigOption useIngotsForEnchantedGoldenApple = new ConfigOption("enchanted_golden_apple_use_ingots", getKey(), false);
    @Option public ConfigOption oldGoldenAppleRecipe = new ConfigOption("old_golden_apple_recipe", getKey(), false);
    @Option public ConfigOption oldChainmailArmorRecipe = new ConfigOption("old_chainmail_armor_recipe", getKey(), false);
    @Option public ConfigOption horseArmorRecipes = new ConfigOption("horse_armor_recipes", getKey(), false);

    private RecipeChoice.MaterialChoice woolChoice;

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
        if(oldChainmailArmorRecipe.asBoolean()) {
            initChainmailHelmetRecipe();
            initChainmailChestplateRecipe();
            initChainmailLeggingsRecipe();
            initChainmailBootsRecipe();
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
