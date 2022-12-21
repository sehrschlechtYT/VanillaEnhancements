package yt.sehrschlecht.vanillaenhancements.modules.inbuilt;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.config.Option;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.utils.BlockUtils;
import yt.sehrschlecht.vanillaenhancements.utils.ItemUtils;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class UnstripLogs extends VEModule {
    @Option
    public ConfigOption damageTools = new ConfigOption("damageTools", getModuleKey(), true);

    @Override
    public @NotNull String getKey() {
        return "unstrip_logs";
    }

    @EventHandler(ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if(!event.hasBlock() || !event.hasItem()) return;
        Block clickedBlock = event.getClickedBlock();
        if(!BlockUtils.checkName(clickedBlock, "STRIPPED") || !ItemUtils.checkName(event.getItem(), "AXE")) return;
        event.setCancelled(true);
        Material newBlock = Material.valueOf(clickedBlock.getType().name().replace("STRIPPED_", ""));
        clickedBlock.setType(newBlock);
        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ITEM_AXE_STRIP, 1, 1);
        if(damageTools.asBoolean()) {
            ItemStack stack = event.getItem();
            if(stack instanceof Damageable) {
                Damageable damageable = (Damageable) stack;
                damageable.setDamage(damageable.getDamage() + 1);
            }
        }
    }
}
