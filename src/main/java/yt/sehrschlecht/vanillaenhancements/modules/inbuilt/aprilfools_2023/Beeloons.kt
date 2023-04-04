package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.aprilfools_2023

import org.bukkit.Bukkit
import org.bukkit.entity.Bee
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.PlayerLeashEntityEvent
import org.bukkit.event.player.PlayerUnleashEntityEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.util.Vector
import yt.sehrschlecht.vanillaenhancements.config.options.IntegerOption
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

    private val bees = mutableMapOf<UUID, MutableList<Bee>>()
    val minBeesForLevitation = IntegerOption(3, "The minimum amount of bees a player needs to be dragged into the air", 1, 1000)

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
            val player = Bukkit.getPlayer(it.key)
            if (player == null) {
                list.clear()
                return@forEach
            }
            list.removeIf { bee -> !bee.isValid || bee.leashHolder != player }
            list.forEach beeLoop@{ bee ->
                val y = player.location.y + 5
                // move bee to the y position
                bee.velocity = Vector(0.0, (y - bee.location.y) / 10, 0.0)
            }
            if (list.size >= minBeesForLevitation.get()) {
                player.addPotionEffect(PotionEffect(PotionEffectType.LEVITATION, 40, list.size - minBeesForLevitation.get()))
            } else if (list.size >= 1) {
                player.addPotionEffect(PotionEffect(PotionEffectType.SLOW_FALLING, 40, list.size - 1))
            }
        }
    }

}