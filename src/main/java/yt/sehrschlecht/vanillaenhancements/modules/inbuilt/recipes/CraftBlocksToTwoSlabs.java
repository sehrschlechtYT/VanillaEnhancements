package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.modules.RecipeModule;

import java.awt.event.TextEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class CraftBlocksToTwoSlabs extends RecipeModule {
    public ConfigOption excludedBlocks = new ConfigOption(List.of(
            "OAK_PLANKS", "SPRUCE_PLANKS", "BIRCH_PLANKS", "JUNGLE_PLANKS", "ACACIA_PLANKS", "DARK_OAK_PLANKS", "MANGROVE_PLANKS", "CRIMSON_PLANKS", "WARPED_PLANKS",
            "POLISHED_BLACKSTONE" //prevent button recipes from being overridden
    ));

    @Override
    public void registerRecipes() {
        Arrays.stream(Material.values()).forEach(block -> {
            if(excludedBlocks.asMaterialList().contains(block)) return;
            String blockName = getSlabName(block);
            if(blockName == null) return;
            Material slab = Material.valueOf(blockName);
            NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "block_to_slabs_" + block.name());
            ShapelessRecipe recipe = new ShapelessRecipe(recipeKey, new ItemStack(slab, 2));
            recipe.addIngredient(block);
            addRecipe(recipeKey, recipe, block);
        });
    }

    @Override
    public @NotNull String getKey() {
        return "craft_blocks_to_two_slabs";
    }

    private @Nullable String getSlabName(Material block) {
        return switch (block.name()) {
            case "OAK_PLANKS" -> "OAK_SLAB";
            case "SPRUCE_PLANKS" -> "SPRUCE_SLAB";
            case "BIRCH_PLANKS" -> "BIRCH_SLAB";
            case "JUNGLE_PLANKS" -> "JUNGLE_SLAB";
            case "ACACIA_PLANKS" -> "ACACIA_SLAB";
            case "DARK_OAK_PLANKS" -> "DARK_OAK_SLAB";
            case "MANGROVE_PLANKS" -> "MANGROVE_SLAB";
            case "CRIMSON_PLANKS" -> "CRIMSON_SLAB";
            case "WARPED_PLANKS" -> "WARPED_SLAB";
            case "STONE_BRICKS" -> "STONE_BRICK_SLAB";
            case "MUD_BRICKS" -> "MUD_BRICK_SLAB";
            case "PURPUR_BLOCK" -> "PURPUR_SLAB";
            case "PRISMARINE_BRICKS" -> "PRISMARINE_BRICK_SLAB";
            case "MOSSY_STONE_BRICKS" -> "MOSSY_STONE_BRICK_SLAB";
            case "END_STONE_BRICKS" -> "END_STONE_BRICK_SLAB";
            case "RED_NETHER_BRICKS" -> "RED_NETHER_BRICK_SLAB";
            case "DEEPSLATE_BRICKS" -> "DEEPSLATE_BRICK_SLAB";
            case "DEEPSLATE_TILES" -> "DEEPSLATE_TILE_SLAB";
            case "POLISHED_BLACKSTONE_BRICKS" -> "POLISHED_BLACKSTONE_BRICK_SLAB";
            case "QUARTZ_BLOCK" -> "QUARTZ_SLAB";
            default -> {
                try {
                    String blockName = block.name() + "_SLAB";
                    Material.valueOf(blockName);
                    yield blockName;
                } catch (IllegalArgumentException e) {
                    yield null;
                }
            }
        };
    }
}
