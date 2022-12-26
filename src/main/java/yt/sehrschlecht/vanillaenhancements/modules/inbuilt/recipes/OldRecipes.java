package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes;

import com.google.gson.annotations.Since;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.config.options.BooleanOption;
import yt.sehrschlecht.vanillaenhancements.modules.RecipeModule;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
public class OldRecipes extends RecipeModule {
    public BooleanOption enchantedGoldenAppleRecipe = new BooleanOption(false,
            "Controls if the enchanted golden apple crafting recipe will be registered");
    public BooleanOption oldGoldenAppleRecipe = new BooleanOption(false,
            "Controls if the old golden apple crafting recipe will be registered");
    public BooleanOption horseArmorRecipes = new BooleanOption(false,
            "Controls if the horse armor crafting recipes will be registered");

    private RecipeChoice.MaterialChoice woolChoice;

    @Override
    public void registerRecipes() {
        woolChoice = new RecipeChoice.MaterialChoice(Tag.WOOL);

        if(enchantedGoldenAppleRecipe.get()) {
            initEnchantedGoldenAppleRecipe();
        }
        if(oldGoldenAppleRecipe.get()) {
            initOldGoldenAppleRecipe();
        }
        if(horseArmorRecipes.get()) {
            initLeatherHorseArmorRecipe();
            initIronHorseArmorRecipe();
            initGoldenHorseArmorRecipe();
            initDiamondHorseArmorRecipe();
        }
    }

    @Override
    public @NotNull String getKey() {
        return "old_recipes";
    }

    private void initEnchantedGoldenAppleRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "enchanted_golden_apple"), new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
        recipe.shape("GGG", "GAG", "GGG");
        recipe.setIngredient('G', Material.GOLD_BLOCK);
        recipe.setIngredient('A', Material.APPLE);
        addRecipe(new NamespacedKey(getPlugin(), "enchanted_golden_apple"), recipe, Material.APPLE);
    }

    private void initOldGoldenAppleRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "old_golden_apple"), new ItemStack(Material.GOLDEN_APPLE));
        recipe.shape("GGG", "GAG", "GGG");
        recipe.setIngredient('G', Material.GOLD_NUGGET);
        recipe.setIngredient('A', Material.APPLE);
        addRecipe(new NamespacedKey(getPlugin(), "old_golden_apple"), recipe, Material.APPLE);
    }

    private void initLeatherHorseArmorRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "leather_horse_armor"), new ItemStack(Material.LEATHER_HORSE_ARMOR));
        recipe.shape("  L", "LWL", "LLL");
        recipe.setIngredient('L', Material.LEATHER);
        recipe.setIngredient('W', woolChoice);
        addRecipe(new NamespacedKey(getPlugin(), "leather_horse_armor"), recipe, Material.LEATHER);
    }

    private void initIronHorseArmorRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "iron_horse_armor"), new ItemStack(Material.IRON_HORSE_ARMOR));
        recipe.shape("  L", "LWL", "LLL");
        recipe.setIngredient('L', Material.IRON_INGOT);
        recipe.setIngredient('W', woolChoice);
        addRecipe(new NamespacedKey(getPlugin(), "iron_horse_armor"), recipe, Material.IRON_INGOT);
    }

    private void initGoldenHorseArmorRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "gold_horse_armor"), new ItemStack(Material.GOLDEN_HORSE_ARMOR));
        recipe.shape("  L", "LWL", "LLL");
        recipe.setIngredient('L', Material.GOLD_INGOT);
        recipe.setIngredient('W', woolChoice);
        addRecipe(new NamespacedKey(getPlugin(), "gold_horse_armor"), recipe, Material.GOLD_INGOT);
    }

    private void initDiamondHorseArmorRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(getPlugin(), "diamond_horse_armor"), new ItemStack(Material.DIAMOND_HORSE_ARMOR));
        recipe.shape("  L", "LWL", "LLL");
        recipe.setIngredient('L', Material.DIAMOND);
        recipe.setIngredient('W', woolChoice);
        addRecipe(new NamespacedKey(getPlugin(), "diamond_horse_armor"), recipe, Material.DIAMOND);
    }
}
