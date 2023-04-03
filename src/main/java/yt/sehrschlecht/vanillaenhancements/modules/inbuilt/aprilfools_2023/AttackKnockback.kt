package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.aprilfools_2023

import com.google.gson.annotations.Since
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import yt.sehrschlecht.vanillaenhancements.config.options.BooleanOption
import yt.sehrschlecht.vanillaenhancements.config.options.IntegerOption
import yt.sehrschlecht.vanillaenhancements.modules.VEModule


/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Suppress("MemberVisibilityCanBePrivate")
@Since(1.0)
class AttackKnockback : VEModule(), CommandExecutor {

    val percentage = IntegerOption(100, "Multiply the knockback by this percentage", 0, 1000)
    val randomizePercentage = BooleanOption(false, "Randomize the knockback percentage (${percentage.min}-${percentage.max}%)")
    val enableCommand = BooleanOption(true, "Enable a command to set the percentage")
    val applyToVerticalVelocity = BooleanOption(false, "Apply the knockback multiplier to the vertical velocity too")

    override fun getKey(): String {
        return "attack_knockback"
    }

    override fun onEnable() {
        if (enableCommand.get()) {
            plugin.getCommand("knockback")?.setExecutor(this)
        }
    }

    @EventHandler
    fun onAttack(event: EntityDamageByEntityEvent) {
        val multiplier = (if (randomizePercentage.get()) (Math.random() * percentage.max).toInt() else percentage.get()) / 100.0
        if (multiplier == 1.0) return
        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            val y = if (multiplier != 0.0) event.entity.velocity.y else 0.0

            val velocity = event.entity.velocity.multiply(multiplier)
            if (!applyToVerticalVelocity.get()) {
                velocity.setY(y)
            }
            event.entity.velocity = velocity
        }, 1)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (!enableCommand.get() || !isEnabled) {
            sender.sendMessage("§cThis command is disabled!")
            return true
        }
        if (args?.size != 1) {
            sender.sendMessage("§cUsage: /knockback <percentage>")
            return true
        }
        val input = args[0].toIntOrNull()
        if (input == null || input < percentage.min || input > percentage.max) {
            sender.sendMessage("§cThe percentage must be between ${percentage.min} and ${percentage.max}!")
            return true
        }
        percentage.set(input)
        sender.sendMessage("Set the knockback multiplier to $input%")
        return true
    }

}