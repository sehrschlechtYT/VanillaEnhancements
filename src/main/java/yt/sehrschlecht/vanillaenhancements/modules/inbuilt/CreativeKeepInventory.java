package yt.sehrschlecht.vanillaenhancements.modules.inbuilt;

import com.google.gson.annotations.Since;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.config.options.BooleanOption;
import yt.sehrschlecht.vanillaenhancements.config.options.StringOption;
import yt.sehrschlecht.vanillaenhancements.modules.ModuleTag;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
public class CreativeKeepInventory extends VEModule {
    public BooleanOption requirePermission = new BooleanOption(false,
            "Controls if a permission is required to keep inventory.");
    public StringOption permission = new StringOption("ve.keep_inventory_in_creative",
            "Controls the permission that is required to keep the inventory. `requirePermission` has to be enabled.");
    // ToDo add keepLevel option

    public CreativeKeepInventory() {
        super("Makes players keep their inventory when they die in creative mode.",
                INBUILT, ModuleTag.MISC, ModuleTag.UTILITY);
    }

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
        if (!player.getGameMode().equals(GameMode.CREATIVE)) return;
        if (Boolean.TRUE.equals(player.getWorld().getGameRuleValue(GameRule.KEEP_INVENTORY))) return;
        if (requirePermission.get()) {
            if (!player.hasPermission(permission.get())) return;
        }
        event.setKeepInventory(true);
        event.setKeepLevel(true);
        event.getDrops().clear();
        event.setDroppedExp(0);
    }

    @Override
    public JavaPlugin getPlugin() {
        return getVEInstance();
    }

}
