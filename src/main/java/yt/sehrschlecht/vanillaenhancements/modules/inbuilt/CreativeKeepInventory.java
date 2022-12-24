package yt.sehrschlecht.vanillaenhancements.modules.inbuilt;

import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class CreativeKeepInventory extends VEModule {
    public ConfigOption requirePermission = new ConfigOption(false);
    public ConfigOption permission = new ConfigOption("ve.keep_inventory_in_creative");

    @Override
    public @NotNull String getName() {
        return "Keep inventory in creative mode";
    }

    @Override
    public @NotNull String getKey() {
        return "creative_keep_inventory";
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if(!player.getGameMode().equals(GameMode.CREATIVE)) return;
        if(Boolean.TRUE.equals(player.getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY))) return;
        if(requirePermission.asBoolean()) {
            if(!player.hasPermission(permission.asString())) return;
        }
        event.setKeepInventory(true);
        event.setKeepLevel(true);
        event.getDrops().clear();
        event.setDroppedExp(0);
    }
}
