package yt.sehrschlecht.vanillaenhancements.modules.inbuilt;

import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.config.Option;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.ticking.TickService;

import java.util.Arrays;

public class ZombiesDryToHusks extends VEModule {
    @Option
    public ConfigOption dryZombiesInNether = new ConfigOption("dry_zombies_in_nether", getKey(), true);
    @Option
    public ConfigOption dryZombiesOnHotDeath = new ConfigOption("dry_zombies_on_hot_death", getKey(), true);

    @Override
    public @NotNull String getName() {
        return "Zombies dry to husks";
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return new NamespacedKey(VanillaEnhancements.getPlugin(), "zombies_dry_to_husks");
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
    public void onPortal(EntityPortalEvent event) {
        if(!event.getEntity().getType().equals(EntityType.ZOMBIE)) return;
        if(event.getTo() == null) return;
        if(!event.getTo().getWorld().getEnvironment().equals(World.Environment.NETHER)) return;
        if(!dryZombiesInNether.asBoolean()) return;
        Zombie zombie = (Zombie) event.getEntity();
        Husk husk = (Husk) zombie.getWorld().spawnEntity(event.getTo(), EntityType.HUSK);
        husk.setHealth(zombie.getHealth());
        for (PotionEffect effect : zombie.getActivePotionEffects()) {
            husk.addPotionEffect(effect);
        }
        for (MemoryKey memoryKey : MemoryKey.values()) {
            if(zombie.getMemory(memoryKey) != null) {
                husk.setMemory(memoryKey, zombie.getMemory(memoryKey));
            }
        }
        if(zombie.isAdult()) {
            husk.setAdult();
        } else {
            husk.setBaby();
        }
        husk.setCustomName(zombie.getCustomName());
        husk.setCustomNameVisible(zombie.isCustomNameVisible());
        husk.setFireTicks(zombie.getFireTicks());
        if(husk.getEquipment() != null && zombie.getEquipment() != null) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                husk.getEquipment().setItem(slot, zombie.getEquipment().getItem(slot));
            }
        }
        zombie.remove();
    }

    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageEvent event) {
        if(!event.getEntity().getType().equals(EntityType.ZOMBIE)) return;
        if(!(event.getEntity() instanceof LivingEntity)) return;
        LivingEntity livingEntity = (LivingEntity) event.getEntity();
        if(!(event.getFinalDamage() >= livingEntity.getHealth())) return;
        if(!Arrays.asList(EntityDamageEvent.DamageCause.LAVA, EntityDamageEvent.DamageCause.FIRE, EntityDamageEvent.DamageCause.FIRE_TICK, EntityDamageEvent.DamageCause.HOT_FLOOR).contains(event.getCause())) return;
        if(!dryZombiesOnHotDeath.asBoolean()) return;
        Zombie zombie = (Zombie) event.getEntity();
        zombie.getWorld().spawnEntity(zombie.getLocation(), EntityType.HUSK);
        zombie.remove();
        event.setCancelled(true);
    }
}
