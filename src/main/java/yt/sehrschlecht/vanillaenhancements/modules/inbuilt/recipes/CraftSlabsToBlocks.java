package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes;

import com.google.gson.annotations.Since;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.modules.RecipeModule;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
public class CraftSlabsToBlocks extends RecipeModule {
    public ConfigOption excludedSlabs = new ConfigOption(new ArrayList<String>(), description);

    @Override
    public void registerRecipes() {
        Arrays.stream(Material.values()).filter(m -> m.name().endsWith("_SLAB")).forEach(slab -> {
            if(excludedSlabs.asMaterialList().contains(slab)) return;
            String blockName = getBlockName(slab);
            if(blockName == null) return;
            Material block = Material.valueOf(blockName);
            NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "slabs_blocks_" + block.name());
            ShapedRecipe recipe = new ShapedRecipe(recipeKey, new ItemStack(block));
            recipe.shape("A", "A");
            recipe.setIngredient('A', slab);
            addRecipe(recipeKey, recipe, slab); //TODO maybe make shapeless
        });
    }

    @Override
    public @NotNull String getKey() {
        return "craft_slabs_to_blocks";
    }

    private @Nullable String getBlockName(Material slab) {
        return switch (slab.name()) {
            case "OAK_SLAB" -> "OAK_PLANKS";
            case "SPRUCE_SLAB" -> "SPRUCE_PLANKS";
            case "BIRCH_SLAB" -> "BIRCH_PLANKS";
            case "JUNGLE_SLAB" -> "JUNGLE_PLANKS";
            case "ACACIA_SLAB" -> "ACACIA_PLANKS";
            case "DARK_OAK_SLAB" -> "DARK_OAK_PLANKS";
            case "MANGROVE_SLAB" -> "MANGROVE_PLANKS";
            case "CRIMSON_SLAB" -> "CRIMSON_PLANKS";
            case "WARPED_SLAB" -> "WARPED_PLANKS";
            case "STONE_BRICK_SLAB" -> "STONE_BRICKS";
            case "MUD_BRICK_SLAB" -> "MUD_BRICKS";
            case "PURPUR_SLAB" -> "PURPUR_BLOCK";
            case "PRISMARINE_BRICK_SLAB" -> "PRISMARINE_BRICKS";
            case "MOSSY_STONE_BRICK_SLAB" -> "MOSSY_STONE_BRICKS";
            case "END_STONE_BRICK_SLAB" -> "END_STONE_BRICKS";
            case "RED_NETHER_BRICK_SLAB" -> "RED_NETHER_BRICKS";
            case "DEEPSLATE_BRICK_SLAB" -> "DEEPSLATE_BRICKS";
            case "DEEPSLATE_TILE_SLAB" -> "DEEPSLATE_TILES";
            case "POLISHED_BLACKSTONE_BRICK_SLAB" -> "POLISHED_BLACKSTONE_BRICKS";
            case "PETRIFIED_OAK_SLAB" -> null;
            default -> {
                try {
                    String blockName = slab.name().replace("_SLAB", "");
                    Material.valueOf(blockName);
                    yield blockName;
                } catch (IllegalArgumentException e) {
                    getLogger().info("Could not find block for slab: " + slab.name() + " with block name: " + slab.name().replace("_SLAB", ""));
                    yield null;
                }
            }
        };
    }
}
