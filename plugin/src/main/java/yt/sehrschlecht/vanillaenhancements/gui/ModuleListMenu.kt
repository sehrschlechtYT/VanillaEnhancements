package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.modules.ModuleTag
import yt.sehrschlecht.vanillaenhancements.utils.PaginationType
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.addBackButton
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.fillBackground
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.paginateItems
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.removeColorCodes
import yt.sehrschlecht.vanillaenhancements.utils.VESound

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ModuleListMenu(private val plugin: VanillaEnhancements, private val tag: ModuleTag) : InventoryProvider {
    companion object {
        fun getInventory(plugin: VanillaEnhancements, tag: ModuleTag): SmartInventory = SmartInventory.builder()
            .id("moduleList")
            .provider(ModuleListMenu(plugin, tag))
            .size(3, 9)
            .title("§lVE - ${tag.displayName} Modules")
            .manager(plugin.inventoryManager)
            .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        val modules = plugin.moduleRegistry.getModulesByTag(tag)
        val items = modules.map {
            ClickableItem.of(it.buildIcon().apply {
                val color = if (it.isEnabled) "§a" else "§c"
                displayName("$color§l${getDisplayName().removeColorCodes()}")
                it.description?.let { desc -> addLongLore(desc, lineStart = "§f§o") }
                addLore("§fCategory: ${it.category.displayName}")
                addLore("§9§oLeft click to open menu")
                addLore("§9§oShift + Left click to toggle")
                glow(it.isEnabled)
                itemFlag(*ItemFlag.values())
            }.build()) listener@{ event ->
                if (event.isShiftClick) {
                    (if (it.isEnabled) VESound.CONFIG_DISABLE else VESound.CONFIG_ENABLE).play(player)
                    it.toggle()
                    return@listener
                }
                ModuleMenu.getInventory(plugin, module = it, tag).open(player)
            }
        }

        contents.paginateItems(items, player = player, paginationType = PaginationType.HORIZONTAL_7, noneItem = {
            displayName("§c§lNo modules found")
            addLongLore("This should not be the case. Please report this bug to the discord server (see SpigotMC page of the VanillaEnhancements plugin)!", lineStart = "§c")
        }, inventoryGetter = { getInventory(plugin, tag) })

        contents.addBackButton { _ -> ModuleTagsMenu.getInventory(plugin) }
        contents.fillBackground()
    }

    override fun update(player: Player, contents: InventoryContents) {
        val state = contents.property("state", 0)
        contents.setProperty("state", state + 1)

        if (state % 20 == 0) {
            init(player, contents)
        }
    }
}