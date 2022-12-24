package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
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
public class CraftStairsToBlocks extends RecipeModule {
    public ConfigOption excludedStairs = new ConfigOption(new ArrayList<String>());
    public ConfigOption requiredStairsAmount = new ConfigOption(4);
    public ConfigOption blockAmount = new ConfigOption(3);

    @Override
    public void registerRecipes() {
        Arrays.stream(Material.values()).filter(m -> m.name().endsWith("_STAIRS")).forEach(stairs -> {
            if(excludedStairs.asMaterialList().contains(stairs)) return;
            String blockName = getBlockName(stairs);
            if(blockName == null) return;
            Material block = Material.valueOf(blockName);
            NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "stairs_blocks_" + block.name());
            ShapelessRecipe recipe = new ShapelessRecipe(recipeKey, new ItemStack(block, blockAmount.asInt()));
            recipe.addIngredient(requiredStairsAmount.asInt(), stairs);
            addRecipe(recipeKey, recipe, stairs);
        });
    }

    @Override
    public @NotNull String getKey() {
        return "craft_stairs_to_blocks";
    }

    private @Nullable String getBlockName(Material stairs) {
        return switch (stairs.name()) {
            case "OAK_STAIRS" -> "OAK_PLANKS";
            case "SPRUCE_STAIRS" -> "SPRUCE_PLANKS";
            case "BIRCH_STAIRS" -> "BIRCH_PLANKS";
            case "JUNGLE_STAIRS" -> "JUNGLE_PLANKS";
            case "ACACIA_STAIRS" -> "ACACIA_PLANKS";
            case "DARK_OAK_STAIRS" -> "DARK_OAK_PLANKS";
            case "MANGROVE_STAIRS" -> "MANGROVE_PLANKS";
            case "CRIMSON_STAIRS" -> "CRIMSON_PLANKS";
            case "WARPED_STAIRS" -> "WARPED_PLANKS";
            case "STONE_BRICK_STAIRS" -> "STONE_BRICKS";
            case "MUD_BRICK_STAIRS" -> "MUD_BRICKS";
            case "PURPUR_STAIRS" -> "PURPUR_BLOCK";
            case "PRISMARINE_BRICK_STAIRS" -> "PRISMARINE_BRICKS";
            case "MOSSY_STONE_BRICK_STAIRS" -> "MOSSY_STONE_BRICKS";
            case "END_STONE_BRICK_STAIRS" -> "END_STONE_BRICKS";
            case "RED_NETHER_BRICK_STAIRS" -> "RED_NETHER_BRICKS";
            case "DEEPSLATE_BRICK_STAIRS" -> "DEEPSLATE_BRICKS";
            case "DEEPSLATE_TILE_STAIRS" -> "DEEPSLATE_TILES";
            case "POLISHED_BLACKSTONE_BRICK_STAIRS" -> "POLISHED_BLACKSTONE_BRICKS";
            default -> {
                try {
                    String blockName = stairs.name().replace("_STAIRS", "");
                    Material.valueOf(blockName);
                    yield blockName;
                } catch (IllegalArgumentException e) {
                    getLogger().info("Could not find block for stairs: " + stairs.name() + " with block name: " + stairs.name().replace("_STAIRS", ""));
                    yield null;
                }
            }
        };
    }
}
