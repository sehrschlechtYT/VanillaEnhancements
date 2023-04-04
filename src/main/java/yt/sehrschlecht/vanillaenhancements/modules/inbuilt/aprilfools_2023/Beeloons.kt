package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.aprilfools_2023

import org.bukkit.Bukkit
import org.bukkit.entity.Bee
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.PlayerLeashEntityEvent
import org.bukkit.event.player.PlayerUnleashEntityEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import yt.sehrschlecht.vanillaenhancements.modules.VEModule
import yt.sehrschlecht.vanillaenhancements.ticking.Tick
import yt.sehrschlecht.vanillaenhancements.utils.docs.Source
import java.util.*

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Source("Minecraft 23w13a_or_b (april fools snapshot 2023)")
class Beeloons : VEModule(
    "Players who are holding at least 3 bees with leashes will be dragged into the air",
    "1.0"
) {
    // ToDo modify pathfinding to make bees fly above the player

    private val bees = mutableMapOf<UUID, MutableList<Bee>>()

    override fun getKey(): String {
        return "beeloons"
    }

    @EventHandler
    fun onLeashEntity(event: PlayerLeashEntityEvent) {
        if (event.entity.type != EntityType.BEE) return
        val player = event.player
        val beeList = bees[player.uniqueId] ?: mutableListOf()
        val newList = beeList.toMutableList()
        newList.add(event.entity as Bee)
        bees[player.uniqueId] = newList
    }

    @EventHandler
    fun onUnleashEntity(event: PlayerUnleashEntityEvent) {
        if (event.entity.type != EntityType.BEE) return
        val player = event.player
        val beeList = bees[player.uniqueId] ?: mutableListOf()
        val newList = beeList.toMutableList()
        newList.remove(event.entity as Bee)
        bees[player.uniqueId] = newList
    }

    @Tick(period = 10)
    fun updateBeeList() {
        bees.entries.forEach {
            val list = it.value
            list.removeIf { bee -> !bee.isValid }
            if (list.size >= 3) {
                val player = Bukkit.getPlayer(it.key)
                player ?: return@forEach
                player.addPotionEffect(PotionEffect(PotionEffectType.LEVITATION, 40, list.size - 3))
            } else if (list.size >= 1) {
                val player = Bukkit.getPlayer(it.key)
                player ?: return@forEach
                player.addPotionEffect(PotionEffect(PotionEffectType.SLOW_FALLING, 40, list.size - 1))
            }
        }
    }

}