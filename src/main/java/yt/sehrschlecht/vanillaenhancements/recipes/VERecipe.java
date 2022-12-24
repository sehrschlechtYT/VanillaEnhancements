package yt.sehrschlecht.vanillaenhancements.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.utils.debugging.Debug;

import java.util.function.Supplier;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public abstract class VERecipe {
    private final NamespacedKey key;
    private final @Nullable Material discoverItem;
    private final Supplier<ItemStack> result;

    public VERecipe(NamespacedKey key, @Nullable Material discoverItem, Supplier<ItemStack> result) {
        this.key = key;
        this.discoverItem = discoverItem;
        this.result = result;
    }

    public NamespacedKey getKey() {
        return key;
    }

    public @Nullable Material getDiscoverItem() {
        return discoverItem;
    }

    public abstract Recipe toBukkitRecipe();

    public Supplier<ItemStack> getResult() {
        return result;
    }

    public void register() {
        Debug.RECIPES.log("Registering recipe {}", key);
        Bukkit.addRecipe(toBukkitRecipe());
    }

    public void unregister() {
        Debug.RECIPES.log("Unregistering recipe {}", key);
        Bukkit.removeRecipe(getKey());
    }

    public void update() {
        Debug.RECIPES.log("Updating recipe {}", key);
        unregister();
        register();
    }
}
