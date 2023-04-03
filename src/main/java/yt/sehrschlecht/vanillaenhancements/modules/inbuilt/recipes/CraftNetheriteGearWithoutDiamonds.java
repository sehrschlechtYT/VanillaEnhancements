package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes;

import com.google.gson.annotations.Since;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.SmithingRecipe;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.config.options.BooleanOption;
import yt.sehrschlecht.vanillaenhancements.modules.RecipeModule;
import yt.sehrschlecht.vanillaenhancements.utils.debugging.Debug;

import java.util.Iterator;
import java.util.List;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
public class CraftNetheriteGearWithoutDiamonds extends RecipeModule {
    public BooleanOption removeVanillaRecipes = new BooleanOption(true,
            "Removes the vanilla smithing table recipes for netherite gear.");
    public BooleanOption craftNetheriteSword = new BooleanOption(true,
            "Controls if the recipe for crafting a netherite sword is enabled.");
    public BooleanOption craftNetheriteShovel = new BooleanOption(true,
            "Controls if the recipe for crafting a netherite shovel is enabled.");
    public BooleanOption craftNetheritePickaxe = new BooleanOption(true,
            "Controls if the recipe for crafting a netherite pickaxe is enabled.");
    public BooleanOption craftNetheriteAxe = new BooleanOption(true,
            "Controls if the recipe for crafting a netherite axe is enabled.");
    public BooleanOption craftNetheriteHoe = new BooleanOption(true,
            "Controls if the recipe for crafting a netherite hoe is enabled.");
    public BooleanOption craftNetheriteHelmet = new BooleanOption(true,
            "Controls if the recipe for crafting a netherite helmet is enabled.");
    public BooleanOption craftNetheriteChestplate = new BooleanOption(true,
            "Controls if the recipe for crafting a netherite chestplate is enabled.");
    public BooleanOption craftNetheriteLeggings = new BooleanOption(true,
            "Controls if the recipe for crafting a netherite leggings is enabled.");
    public BooleanOption craftNetheriteBoots = new BooleanOption(true,
            "Controls if the recipe for crafting a netherite boots is enabled.");

    public CraftNetheriteGearWithoutDiamonds() {
        super("Allows players to craft netherite gear by using netherite ingots in a crafting table.");
    }

    @Override
    public void registerRecipes() {
        if(removeVanillaRecipes.get()) {
            Iterator<Recipe> iterator = Bukkit.recipeIterator();
            while (iterator.hasNext()) {
                Recipe recipe = iterator.next();
                if(recipe instanceof SmithingRecipe smithingRecipe) {
                    if(List.of(
                            Material.NETHERITE_SWORD,
                            Material.NETHERITE_SHOVEL,
                            Material.NETHERITE_PICKAXE,
                            Material.NETHERITE_AXE,
                            Material.NETHERITE_HOE,
                            Material.NETHERITE_HELMET,
                            Material.NETHERITE_CHESTPLATE,
                            Material.NETHERITE_LEGGINGS,
                            Material.NETHERITE_BOOTS
                    ).contains(recipe.getResult().getType())) {
                        Debug.RECIPES.log("Removed netherite recipe {}.", smithingRecipe.getKey());
                        iterator.remove();
                    }
                }
            }
        }

        if(craftNetheriteSword.get()) addNetheriteSword();
        if(craftNetheriteShovel.get()) addNetheriteShovel();
        if(craftNetheritePickaxe.get()) addNetheritePickaxe();
        if(craftNetheriteAxe.get()) addNetheriteAxe();
        if(craftNetheriteHoe.get()) addNetheriteHoe();
        if(craftNetheriteHelmet.get()) addNetheriteHelmet();
        if(craftNetheriteChestplate.get()) addNetheriteChestplate();
        if(craftNetheriteLeggings.get()) addNetheriteLeggings();
        if(craftNetheriteBoots.get()) addNetheriteBoots();
    }

    private void addNetheriteSword() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "netherite_sword_crafting");
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(Material.NETHERITE_SWORD));
        recipe.shape("N", "N", "S");
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('S', Material.STICK);
        addRecipe(recipeKey, recipe, Material.NETHERITE_INGOT);
    }

    private void addNetheriteShovel() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "netherite_shovel_crafting");
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(Material.NETHERITE_SHOVEL));
        recipe.shape("N", "S", "S");
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('S', Material.STICK);
        addRecipe(recipeKey, recipe, Material.NETHERITE_INGOT);
    }

    private void addNetheritePickaxe() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "netherite_pickaxe_crafting");
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(Material.NETHERITE_PICKAXE));
        recipe.shape("NNN", " S ", " S ");
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('S', Material.STICK);
        addRecipe(recipeKey, recipe, Material.NETHERITE_INGOT);
    }

    private void addNetheriteAxe() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "netherite_axe_crafting");
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(Material.NETHERITE_AXE));
        recipe.shape("NN", "NS", " S");
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('S', Material.STICK);
        addRecipe(recipeKey, recipe, Material.NETHERITE_INGOT);
    }

    private void addNetheriteHoe() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "netherite_hoe_crafting");
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(Material.NETHERITE_HOE));
        recipe.shape("NN", " S", " S");
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('S', Material.STICK);
        addRecipe(recipeKey, recipe, Material.NETHERITE_INGOT);
    }

    private void addNetheriteHelmet() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "netherite_helmet_crafting");
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(Material.NETHERITE_HELMET));
        recipe.shape("NNN", "N N");
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        addRecipe(recipeKey, recipe, Material.NETHERITE_INGOT);
    }

    private void addNetheriteChestplate() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "netherite_chestplate_crafting");
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(Material.NETHERITE_CHESTPLATE));
        recipe.shape("N N", "NNN", "NNN");
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        addRecipe(recipeKey, recipe, Material.NETHERITE_INGOT);
    }

    private void addNetheriteLeggings() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "netherite_leggings_crafting");
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(Material.NETHERITE_LEGGINGS));
        recipe.shape("NNN", "N N", "N N");
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        addRecipe(recipeKey, recipe, Material.NETHERITE_INGOT);
    }

    private void addNetheriteBoots() {
        NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "netherite_boots_crafting");
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(Material.NETHERITE_BOOTS));
        recipe.shape("N N", "N N");
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        addRecipe(recipeKey, recipe, Material.NETHERITE_INGOT);
    }

    @Override
    public @NotNull String getKey() {
        return "craft_netherite_gear_without_diamonds";
    }
}
