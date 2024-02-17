package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.messages.Message
import yt.sehrschlecht.vanillaenhancements.modules.VEModule
import yt.sehrschlecht.vanillaenhancements.utils.PaginationType
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.addBackButton
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.asComponent
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.fillBackground
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.paginateItems

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ModuleTagsMenu(private val plugin: VanillaEnhancements, private val origin: SmartInventory?) : RecurrentInventoryInitializer(20) {

    companion object {
        fun getInventory(plugin: VanillaEnhancements, origin: SmartInventory? = null): SmartInventory = SmartInventory.builder()
            .id("moduleTags")
            .provider(ModuleTagsMenu(plugin, origin))
            .size(3, 9)
            .title("§lVE - Module tags")
            .manager(plugin.inventoryManager)
            .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        val tags = plugin.moduleRegistry.collectTags()
        val items = tags.map {
            val modules = plugin.moduleRegistry.getModulesByTag(it)
            val total = modules.size
            val enabled = modules.count(VEModule::isEnabled)
            ClickableItem.of(it.buildIcon {
                displayName(it.displayName.color(NamedTextColor.WHITE).decorate(TextDecoration.BOLD))
                appendLore(Message.MENU_TAGS_TAG_ENABLED.asComponent(plugin, enabled, total))
                allItemFlags()
            }.build()) { _ -> ModuleListMenu.getInventory(plugin, it, contents.inventory()).open(player) }
        }

        contents.paginateItems(items, player = player, paginationType = PaginationType.HORIZONTAL_7, noneItem = {
            displayName(Message.MENU_TAGS_NONE_DISPLAYNAME, plugin)
            appendLongLore(Message.MENU_TAGS_NONE_LORE.asComponent(plugin), lineStart = NamedTextColor.RED)
        }, inventoryGetter = { getInventory(plugin) })

        contents.addBackButton { origin ?: MainMenu.getInventory(plugin) }
        contents.fillBackground()
    }

}