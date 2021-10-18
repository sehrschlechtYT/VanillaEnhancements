package yt.sehrschlecht.vanillaenhancements.modules.enhancements;

import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.config.Option;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.ticking.TickService;

public class CreativeKeepInventory extends VEModule {
    @Option
    public ConfigOption requirePermission = new ConfigOption("require_permission", getKey(), false);
    @Option
    public ConfigOption permission = new ConfigOption("permission", getKey(), "ve.keep_inventory_in_creative");

    @Override
    public @NotNull String getName() {
        return "Keep inventory in creative mode";
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return new NamespacedKey(VanillaEnhancements.getPlugin(), "creative_keep_inventory");
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

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if(!player.getGameMode().equals(GameMode.CREATIVE)) return;
        if(player.getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY)) return;
        if(requirePermission.asBoolean()) {
            if(!player.hasPermission(permission.asString())) return;
        }
        event.setKeepInventory(true);
        event.setKeepLevel(true);
    }
}
