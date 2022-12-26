package yt.sehrschlecht.vanillaenhancements.modules.inbuilt;

import com.google.gson.annotations.Since;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
public class GlowSacFishing extends VEModule {
    public ConfigOption chance = new ConfigOption(0.01D, description);

    @Override
    public @NotNull String getKey() {
        return "glow_sac_fishing";
    }

    @EventHandler(ignoreCancelled = true)
    public void onFish(PlayerFishEvent event) {
        if(event.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;
        //ToDo doesn't work
        if(event.getCaught() instanceof Item) {
            Item item = (Item) event.getCaught();
            if(ThreadLocalRandom.current().nextDouble() < chance.asDouble()) {
                item.setItemStack(new ItemStack(Material.GLOW_INK_SAC));
            }
        }
    }
}
