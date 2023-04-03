package yt.sehrschlecht.vanillaenhancements.modules.inbuilt.aprilfools_2023

import org.bukkit.Bukkit
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.inventory.EquipmentSlot
import yt.sehrschlecht.vanillaenhancements.config.options.DoubleOption
import yt.sehrschlecht.vanillaenhancements.modules.VEModule
import yt.sehrschlecht.vanillaenhancements.ticking.Tick
import yt.sehrschlecht.vanillaenhancements.utils.docs.Source
import java.util.*

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Source("Minecraft 23w13a_or_b (april fools snapshot 2023)")
class BedPVP : VEModule(
    "Gives beds attack damage and speed",
    "1.0"
) {

    val attackDamage = DoubleOption(7.0, "The attack damage of beds", 0.0, Int.MAX_VALUE.toDouble())
    val attackSpeed = DoubleOption(1.6, "The attack speed of beds", 0.0, Int.MAX_VALUE.toDouble())

    private val damageUUID = UUID.randomUUID()
    private val speedUUID = UUID.randomUUID()

    override fun getKey(): String {
        return "bed_pvp"
    }

    override fun getName(): String {
        return "Bed PVP"
    }

    @Tick(period = 20, executeNow = true)
    fun updateBeds() {
        for (player in Bukkit.getOnlinePlayers()) {
            player.inventory.contents.forEach {
                it ?: return@forEach
                if (it.type.name.endsWith("_BED")) {
                    val meta = it.itemMeta
                    meta ?: return@forEach
                    if (meta.attributeModifiers?.containsKey(Attribute.GENERIC_ATTACK_DAMAGE) == true || meta.attributeModifiers?.containsKey(Attribute.GENERIC_ATTACK_SPEED) == true) return@forEach
                    meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, AttributeModifier(damageUUID, "generic.attackDamage", attackDamage.get(), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND))
                    meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, AttributeModifier(speedUUID, "generic.attackSpeed", attackSpeed.get(), AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND))
                    it.itemMeta = meta
                }
            }
        }
    }

}