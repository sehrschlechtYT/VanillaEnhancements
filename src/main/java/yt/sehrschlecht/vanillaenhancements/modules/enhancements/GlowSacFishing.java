package yt.sehrschlecht.vanillaenhancements.modules.enhancements;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.config.Option;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.ticking.TickService;

import java.util.concurrent.ThreadLocalRandom;

public class GlowSacFishing extends VEModule {
    @Option
    public ConfigOption chance = new ConfigOption("chance", getInstance().getKey(), 0.01D);


    @Override
    public @NotNull String getName() {
        return "Glow Sac Fishing";
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return new NamespacedKey(VanillaEnhancements.getPlugin(), "glow_sac_fishing");
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public @Nullable TickService getTickService() {
        return null;
    }

    @EventHandler(ignoreCancelled = true)
    public void onFish(PlayerFishEvent event) {
        if(event.getCaught() instanceof Item) {
            Item item = (Item) event.getCaught();
            if(ThreadLocalRandom.current().nextDouble() < chance.asDouble()) {
                item.setItemStack(new ItemStack(Material.GLOW_INK_SAC));
            }
        }
    }
}
