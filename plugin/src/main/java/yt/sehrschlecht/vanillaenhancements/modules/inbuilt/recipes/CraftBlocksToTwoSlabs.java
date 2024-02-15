package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes;

import com.google.gson.annotations.Since;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.config.options.MaterialListOption;
import yt.sehrschlecht.vanillaenhancements.modules.ModuleTag;
import yt.sehrschlecht.vanillaenhancements.modules.RecipeModule;
import yt.sehrschlecht.vanillaenhancements.utils.docs.Source;

import java.util.Arrays;
import java.util.List;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
@Source("https://vanillatweaks.net")
public class CraftBlocksToTwoSlabs extends RecipeModule {
    public MaterialListOption excludedBlocks = MaterialListOption.fromStrings(List.of(
            "OAK_PLANKS", "SPRUCE_PLANKS", "BIRCH_PLANKS", "JUNGLE_PLANKS", "ACACIA_PLANKS", "DARK_OAK_PLANKS", "MANGROVE_PLANKS", "CRIMSON_PLANKS", "WARPED_PLANKS",
            "POLISHED_BLACKSTONE" //prevent button recipes from being overridden
    ), "Exclude recipes for blocks from being registered");

    public CraftBlocksToTwoSlabs() {
        super("Allows players to craft two slabs from one block.",
                INBUILT, ModuleTag.VANILLA_TWEAKS);
    }

    @Override
    public void registerRecipes() {
        Arrays.stream(Material.values()).forEach(block -> {
            if (excludedBlocks.get().contains(block)) return;
            String blockName = getSlabName(block);
            if (blockName == null) return;
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
            case "QUARTZ" -> null;
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

    @Override
    public JavaPlugin getPlugin() {
        return getVEInstance();
    }

    @Override
    public Material getDisplayItem() {
        return Material.OAK_SLAB;
    }

}