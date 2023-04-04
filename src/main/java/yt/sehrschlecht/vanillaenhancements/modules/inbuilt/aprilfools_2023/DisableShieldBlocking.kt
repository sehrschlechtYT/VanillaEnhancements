package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.aprilfools_2023

import org.bukkit.Material
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import yt.sehrschlecht.vanillaenhancements.modules.VEModule
import yt.sehrschlecht.vanillaenhancements.ticking.Tick
import yt.sehrschlecht.vanillaenhancements.utils.docs.Source

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Source("Minecraft 23w13a_or_b (april fools snapshot 2023)")
class DisableShieldBlocking : VEModule(
    "Makes players unable to block with shields.",
    "DisableShieldBlocking"
) {

    override fun getKey(): String {
        return "disable_shield_blocking"
    }

    @EventHandler
    fun onShieldBlock(event: PlayerInteractEvent) { // not sure if this is needed
        event.item ?: return
        if (event.action == Action.RIGHT_CLICK_BLOCK || event.action == Action.RIGHT_CLICK_AIR) {
            if (event.item != null && event.item!!.type == Material.SHIELD) {
                event.setUseItemInHand(Event.Result.DENY)
            }
        }
    }

    @Tick(period = 20, executeNow = true)
    fun disableShields() {
        loopPlayers {
            it.setCooldown(Material.SHIELD, 1200)
        }
    }

}