package yt.sehrschlecht.vanillaenhancements.modules;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.recipes.RecipeManager;
import yt.sehrschlecht.vanillaenhancements.utils.debugging.Debug;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public abstract class RecipeModule extends VEModule {
    protected List<VERecipe> addedRecipes = new ArrayList<>();

    @Override
    public void initialize() {
        registerRecipes();
    }

    /**
     * @param description A <b>short</b> description of the module.
     * @param since       The version since the module is available.
     */
    public RecipeModule(@Nullable String description, @Nullable String since, @NotNull ModuleCategory category, ModuleTag... tags) {
        super(description, since, category, tags);
        this.tags.add(ModuleTag.RECIPES);
    }

    /**
     * @param description A <b>short</b> description of the module.
     */
    public RecipeModule(@Nullable String description, @NotNull ModuleCategory category, ModuleTag... tags) {
        super(description, category, tags);
        this.tags.add(ModuleTag.RECIPES);
    }

    public RecipeModule(@NotNull ModuleCategory category, ModuleTag... tags) {
        super(category, tags);
        this.tags.add(ModuleTag.RECIPES);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Debug.RECIPES.log("Adding recipes for module {}...", getModuleKey());;
        addedRecipes = VanillaEnhancements.getPlugin().getRecipeManager().getRecipes(this);
        getRecipeManager().getRecipes(this).forEach(recipe -> Bukkit.addRecipe(recipe.recipe()));
        Debug.RECIPES.log("Added {} recipes for module {}.", addedRecipes.size(), getModuleKey());
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (getVEInstance().isShuttingDown()) return;
        Debug.RECIPES.log("Removing all recipes of module {}...", getModuleKey());
        getRecipeManager().getRecipes(this).forEach(recipe -> Bukkit.removeRecipe(recipe.key()));
        Bukkit.resetRecipes();
    }

    protected void reloadRecipes() {
        Debug.RECIPES.log("Reloading recipes of module {}...", getModuleKey());
        addedRecipes.forEach(recipe -> {
            if (Bukkit.getRecipe(recipe.key()) == null) {
                Debug.RECIPES.log("Recipe {} was never added.", recipe.key());
            }
            boolean removed = Bukkit.removeRecipe(recipe.key());
            Debug.RECIPES.log("Removed recipe {} {}.", recipe.key(), removed ? "successfully" : "unsuccessfully");
        });
        getRecipeManager().clearRecipes(this);
        registerRecipes();
        getRecipeManager().getRecipes(this).forEach(recipe -> {
            Bukkit.addRecipe(recipe.recipe());
            Debug.RECIPES.log("Added recipe {}.", recipe.key());
        });
        addedRecipes = getRecipeManager().getRecipes(this);
    }

    protected RecipeManager getRecipeManager() {
        return getVEInstance().getRecipeManager();
    }

    @Override
    public void reload() {
        super.reload();
        reloadRecipes();
    }

    public abstract void registerRecipes();

    /**
     * Adds a recipe to the module.
     * <b>WARNING: Call only in {@link #registerRecipes()}!</b>
     * @param key The key of the recipe
     * @param recipe The recipe
     */
    public void addRecipe(NamespacedKey key, Recipe recipe, @Nullable Material discoverItem) {
        getRecipeManager().addRecipe(getModuleKey(), new VERecipe(key, recipe, discoverItem));
    }

    public void removeRecipe(NamespacedKey key) {
        getRecipeManager().removeRecipe(getModuleKey(), key);
    }

}
