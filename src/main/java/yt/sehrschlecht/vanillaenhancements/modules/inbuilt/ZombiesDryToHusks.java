package yt.sehrschlecht.vanillaenhancements.modules.inbuilt;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Husk;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;

import java.util.Arrays;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class ZombiesDryToHusks extends VEModule {
    public ConfigOption dryZombiesInNether = new ConfigOption(true);
    public ConfigOption dryZombiesOnHotDeath = new ConfigOption(true);

    @Override
    public @NotNull String getKey() {
        return "zombies_dry_to_husks";
    }
    //ToDo broken
    @EventHandler(ignoreCancelled = true)
    public void onPortal(EntityPortalEvent event) {
        if(!event.getEntity().getType().equals(EntityType.ZOMBIE)) return;
        if(event.getTo() == null) return;
        if(!event.getTo().getWorld().getEnvironment().equals(World.Environment.NETHER)) return;
        if(!dryZombiesInNether.asBoolean()) return;
        //Clone the zombie
        Zombie zombie = (Zombie) event.getEntity();
        replace(zombie, event.getTo());
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
        replace(zombie, zombie.getLocation());
    }

    private Husk replace(Zombie zombie, Location location) {
        Husk husk = (Husk) zombie.getWorld().spawnEntity(location, EntityType.HUSK);
        husk.setHealth(zombie.getHealth());
        for (PotionEffect effect : zombie.getActivePotionEffects()) {
            husk.addPotionEffect(effect);
        }
        try {
            if(zombie.getMemory(MemoryKey.ANGRY_AT) != null) {
                husk.setMemory(MemoryKey.ANGRY_AT, zombie.getMemory(MemoryKey.ANGRY_AT));
            }
        } catch (IllegalStateException ignored) {
            //ignore if the memory is not registered
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
        return husk;
    }
}
