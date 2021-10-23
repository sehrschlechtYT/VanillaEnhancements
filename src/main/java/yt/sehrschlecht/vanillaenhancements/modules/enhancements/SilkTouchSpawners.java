package yt.sehrschlecht.vanillaenhancements.modules.enhancements;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.ticking.TickService;

public class SilkTouchSpawners extends VEModule {
    //Todo make it work!

    @Override
    public @NotNull String getName() {
        return "Silk touch spawners";
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return new NamespacedKey(VanillaEnhancements.getPlugin(), "silk_touch_spawners");
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
    public void onBreak(BlockBreakEvent event) {
        if(!event.getBlock().getType().equals(Material.SPAWNER)) return;
        Player player = event.getPlayer();
        ItemStack handItem = player.getEquipment().getItemInMainHand();
        if(handItem.getType().isAir() || !handItem.hasItemMeta() || handItem.getEnchantmentLevel(Enchantment.SILK_TOUCH) == 0) return;
        event.setDropItems(false);
        CreatureSpawner spawner = (CreatureSpawner) event.getBlock().getState();
        EntityType entityType = spawner.getSpawnedType();
        ItemStack stack = new ItemStack(Material.SPAWNER);
        BlockStateMeta meta = (BlockStateMeta) stack.getItemMeta();
        CreatureSpawner metaSpawner = (CreatureSpawner) meta.getBlockState();
        metaSpawner.setSpawnedType(entityType);
        meta.setBlockState(metaSpawner);
        stack.setItemMeta(meta);
        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), stack);
    }
}
