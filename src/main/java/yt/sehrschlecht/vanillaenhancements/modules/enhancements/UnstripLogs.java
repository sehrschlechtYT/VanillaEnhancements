package yt.sehrschlecht.vanillaenhancements.modules.enhancements;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.config.Option;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.ticking.TickService;
import yt.sehrschlecht.vanillaenhancements.utils.BlockUtils;
import yt.sehrschlecht.vanillaenhancements.utils.ItemUtils;

public class UnstripLogs extends VEModule {
    @Option
    public ConfigOption damageTools = new ConfigOption("damageTools", getKey(), true);

    @Override
    public @NotNull String getName() {
        return "Unstrip Logs";
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return new NamespacedKey(VanillaEnhancements.getPlugin(), "unstrip_logs");
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
    public void onInteract(PlayerInteractEvent event) {
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if(!event.hasBlock() || !event.hasItem()) return;
        if(!BlockUtils.checkName(event.getClickedBlock(), "STRIPPED") || !ItemUtils.checkName(event.getItem(), "AXE")) return;
        Material newBlock = Material.valueOf(event.getClickedBlock().getType().name().replace("STRIPPED_", ""));
        event.getClickedBlock().setType(newBlock);
        if(damageTools.asBoolean()) {
            ItemStack stack = event.getItem();
            if(stack instanceof Damageable) {
                Damageable damageable = (Damageable) stack;
                damageable.setDamage(damageable.getDamage() + 1);
            }
        }
    }
}
