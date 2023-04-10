package yt.sehrschlecht.vanillaenhancements.modules.inbuilt;

import com.google.gson.annotations.Since;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.config.options.DoubleOption;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
public class GlowSacFishing extends VEModule {
    public DoubleOption chance = new DoubleOption(0.01D, "The chance for a glow inc sac to drop when fishing", 0D, 1D);

    public GlowSacFishing() {
        super("Adds a chance for a glow ink sac to drop when fishing.", INBUILT);
    }

    @Override
    public @NotNull String getKey() {
        return "glow_sac_fishing";
    }

    @EventHandler(ignoreCancelled = true)
    public void onFish(PlayerFishEvent event) {
        if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;
        //ToDo doesn't work
        if (event.getCaught() instanceof Item item) {
            if (ThreadLocalRandom.current().nextDouble() < chance.get()) {
                item.setItemStack(new ItemStack(Material.GLOW_INK_SAC));
            }
        }
    }

    @Override
    public JavaPlugin getPlugin() {
        return getVEInstance();
    }

}
