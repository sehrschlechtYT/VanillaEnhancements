package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes;

import com.google.gson.annotations.Since;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.config.options.IntegerOption;
import yt.sehrschlecht.vanillaenhancements.config.options.MaterialListOption;
import yt.sehrschlecht.vanillaenhancements.modules.ModuleTag;
import yt.sehrschlecht.vanillaenhancements.modules.RecipeModule;
import yt.sehrschlecht.vanillaenhancements.utils.docs.Source;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
@Source("https://vanillatweaks.net")
public class CraftStairsToBlocks extends RecipeModule {
    public MaterialListOption excludedStairs = new MaterialListOption(Collections.emptyList(),
            "Exclude recipes for stairs from being registered");
    public IntegerOption requiredStairsAmount = new IntegerOption(4,
            "The required amount of stairs to craft the blocks", 1, 9, 1);
    public IntegerOption blockAmount = new IntegerOption(3,
            "The amount of blocks that players will receive", 1, 64, 1);

    public CraftStairsToBlocks() {
        super("Allows players to craft stairs back into blocks.",
                INBUILT, ModuleTag.VANILLA_TWEAKS);
    }

    @Override
    public void registerRecipes() {
        Arrays.stream(Material.values()).filter(m -> m.name().endsWith("_STAIRS")).forEach(stairs -> {
            if (excludedStairs.get().contains(stairs)) return;
            String blockName = getBlockName(stairs);
            if (blockName == null) return;
            Material block = Material.valueOf(blockName);
            NamespacedKey recipeKey = new NamespacedKey(getPlugin(), "stairs_blocks_" + block.name());
            ShapelessRecipe recipe = new ShapelessRecipe(recipeKey, new ItemStack(block, blockAmount.get()));
            recipe.addIngredient(requiredStairsAmount.get(), stairs);
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
            case "QUARTZ_STAIRS" -> "QUARTZ_BLOCK";
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

    @Override
    public JavaPlugin getPlugin() {
        return getVEInstance();
    }

    @Override
    public Material getDisplayItem() {
        return Material.OAK_STAIRS;
    }

}
