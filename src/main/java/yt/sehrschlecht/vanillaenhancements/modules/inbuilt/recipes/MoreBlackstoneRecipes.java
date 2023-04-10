package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes;

import com.google.gson.annotations.Since;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.config.options.BooleanOption;
import yt.sehrschlecht.vanillaenhancements.modules.ModuleTag;
import yt.sehrschlecht.vanillaenhancements.modules.RecipeModule;
import yt.sehrschlecht.vanillaenhancements.utils.docs.Source;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
@Source("https://vanillatweaks.net/")
public class MoreBlackstoneRecipes extends RecipeModule {
    public BooleanOption brewingStandRecipe = new BooleanOption(true,
            "Controls if the brewing stand recipe will be registered.");
    public BooleanOption dispenserRecipe = new BooleanOption(true,
            "Controls if the dispenser recipe will be registered.");
    public BooleanOption dropperRecipe = new BooleanOption(true,
            "Controls if the dropper recipe will be registered.");
    public BooleanOption leverRecipe = new BooleanOption(true,
            "Controls if the lever recipe will be registered.");
    public BooleanOption observerRecipe = new BooleanOption(true,
            "Controls if the observer recipe will be registered.");
    public BooleanOption pistonRecipe = new BooleanOption(true,
            "Controls if the piston recipe will be registered.");

    public MoreBlackstoneRecipes() {
        super("Makes all recipes that use cobblestone allow blackstone as well.",
                INBUILT, ModuleTag.VANILLA_TWEAKS);
    }

    @Override
    public void registerRecipes() {
        if (brewingStandRecipe.get()) addBrewingStandRecipe();
        if (dispenserRecipe.get()) addDispenserRecipe();
        if (dropperRecipe.get()) addDropperRecipe();
        if (leverRecipe.get()) addLeverRecipe();
        if (observerRecipe.get()) addObserverRecipe();
        if (pistonRecipe.get()) addPistonRecipe();
    }

    private void addBrewingStandRecipe() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "blackstone_brerwing_stand");
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(Material.BREWING_STAND));
        recipe.shape(" R ", "BBB");
        recipe.setIngredient('R', Material.BLAZE_ROD);
        recipe.setIngredient('B', Material.BLACKSTONE);
        addRecipe(recipeKey, recipe, Material.BLAZE_ROD);
    }

    private void addDispenserRecipe() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "blackstone_dispenser");
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(Material.DISPENSER));
        recipe.shape("BBB", "BWB", "BRB");
        recipe.setIngredient('B', Material.BLACKSTONE);
        recipe.setIngredient('W', Material.BOW);
        recipe.setIngredient('R', Material.REDSTONE);
        addRecipe(recipeKey, recipe, Material.REDSTONE);
    }

    private void addDropperRecipe() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "blackstone_dropper");
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(Material.DROPPER));
        recipe.shape("BBB", "B B", "BRB");
        recipe.setIngredient('B', Material.BLACKSTONE);
        recipe.setIngredient('R', Material.REDSTONE);
        addRecipe(recipeKey, recipe, Material.REDSTONE);
    }

    private void addLeverRecipe() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "blackstone_lever");
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(Material.LEVER));
        recipe.shape("S", "B");
        recipe.setIngredient('S', Material.STICK);
        recipe.setIngredient('B', Material.BLACKSTONE);
        addRecipe(recipeKey, recipe, Material.STICK);
    }

    private void addObserverRecipe() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "blackstone_observer");
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(Material.OBSERVER));
        recipe.shape("BBB", "RRQ", "BBB");
        recipe.setIngredient('B', Material.BLACKSTONE);
        recipe.setIngredient('R', Material.REDSTONE);
        recipe.setIngredient('Q', Material.QUARTZ);
        addRecipe(recipeKey, recipe, Material.REDSTONE);
    }

    private void addPistonRecipe() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "blackstone_piston");
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(Material.PISTON));
        recipe.shape("PPP", "BIB", "BRB");
        recipe.setIngredient('P', new RecipeChoice.MaterialChoice(Tag.PLANKS));
        recipe.setIngredient('B', Material.BLACKSTONE);
        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('R', Material.REDSTONE);
        addRecipe(recipeKey, recipe, Material.REDSTONE);
    }

    @Override
    public @NotNull String getKey() {
        return "more_blackstone_recipes";
    }

    @Override
    public JavaPlugin getPlugin() {
        return getVEInstance();
    }

}
