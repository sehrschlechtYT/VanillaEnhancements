package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.aprilfools_2023

import com.google.gson.annotations.Since
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.plugin.java.JavaPlugin
import yt.sehrschlecht.vanillaenhancements.config.Config
import yt.sehrschlecht.vanillaenhancements.config.options.BooleanOption
import yt.sehrschlecht.vanillaenhancements.config.options.IntegerOption
import yt.sehrschlecht.vanillaenhancements.modules.ModuleTag
import yt.sehrschlecht.vanillaenhancements.modules.VEModule
import yt.sehrschlecht.vanillaenhancements.utils.docs.Source


/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
@Source("Minecraft 23w13a_or_b (april fools snapshot 2023)")
class AttackKnockback : VEModule(
    "Multiplies the knockback of attacks by a percentage",
    INBUILT,
    ModuleTag.APRIL_FOOLS_2023,
    ModuleTag.ENTITIES,
), CommandExecutor {

    val percentage = IntegerOption(100, "Multiply the knockback by this percentage", 0, 1000, 10)
    val randomizePercentage = BooleanOption(false, "Randomize the knockback percentage (${percentage.min}-${percentage.max}%)")
    val enableCommand = BooleanOption(true, "Enable a command to set the percentage")
    val applyToVerticalVelocity = BooleanOption(false, "Apply the knockback multiplier to the vertical velocity too")

    override fun getKey(): String {
        return "attack_knockback"
    }

    override fun getDisplayItem(): Material {
        return Material.WOODEN_SWORD
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
            sender.sendMessage(Config.getInstance().message("commandDisabled"))
            return true
        }
        if (args?.size != 1) {
            sender.sendMessage(Config.getInstance().message("knockback.usage"))
            return true
        }
        val input = args[0].toIntOrNull()
        if (input == null || input < percentage.min || input > percentage.max) {
            sender.sendMessage(Config.getInstance().message("knockback.invalidInput")
                .replace("%min%", percentage.min.toString())
                .replace("%max%", percentage.max.toString())
            )
            return true
        }
        percentage.set(input)
        sender.sendMessage(Config.getInstance().message("knockback.success")
            .replace("%percentage%", input.toString()))
        return true
    }

    override fun getPlugin(): JavaPlugin {
        return veInstance
    }

}