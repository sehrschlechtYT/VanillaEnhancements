package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.entity.Player
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.config.options.DyeColorOption
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator
import yt.sehrschlecht.vanillaenhancements.utils.ModuleUtils
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.addBackButton
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.asChatColor
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.fillBackground

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ChooseDyeColorMenu(private val option: DyeColorOption, private val origin: SmartInventory) : InventoryProvider {

    companion object {
        fun getInventory(plugin: VanillaEnhancements, option: DyeColorOption, origin: SmartInventory): SmartInventory = SmartInventory.builder()
            .id("chooseDyeColor")
            .provider(ChooseDyeColorMenu(option, origin))
            .size(2, 9)
            .title("§lChoose a color")
            .manager(plugin.inventoryManager)
            .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        for (color in DyeColor.values()) {
            contents.add(ClickableItem.of(
                ItemCreator(Material.valueOf(color.name + "_WOOL")) {
                    displayName("${color.asChatColor()}§l${ModuleUtils.getNameFromKey(color.name)}")
                    addLongLore("§fClick to set the option ${ModuleUtils.beautifyLowerCamelCase(option.key)} to ${ModuleUtils.getNameFromKey(color.name)}.")
                }.build()
            ) { _ ->
                option.set(color)
                origin.open(player)
            })
        }
        contents.addBackButton { origin }
        contents.fillBackground()
    } // ToDo: After selecting the value, the options are not reloaded!!

    override fun update(player: Player, contents: InventoryContents) {

    }

}