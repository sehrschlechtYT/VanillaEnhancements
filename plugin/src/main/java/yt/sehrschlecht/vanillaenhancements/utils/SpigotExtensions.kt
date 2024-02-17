package yt.sehrschlecht.vanillaenhancements.utils

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.SlotIterator
import fr.minuskube.inv.content.SlotPos
import net.kyori.adventure.text.Component
import org.bukkit.ChatColor
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.messages.Message

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class SpigotExtensions {

    companion object {
        fun InventoryContents.addBackButton(onClick: ((Player) -> SmartInventory?)? = null) {
            set(inventory().rows - 1, inventory().columns - 1, ClickableItem.of(
                ItemCreator(Material.IRON_DOOR) {
                    if (onClick == null) {
                        displayName("§cClose")
                    } else {
                        displayName("§cBack")
                    }
                }.build()
            ) click@{ event ->
                val player = event.whoClicked as Player
                onClick ?: return@click player.closeInventory()
                val inventory = onClick(player)
                inventory ?: return@click player.closeInventory()
                inventory.open(player)
            })
        }

        fun InventoryContents.fillBackground() {
            while (true) {
                val firstEmpty = firstEmpty() ?: break
                if (firstEmpty.isEmpty) return
                set(firstEmpty.get(), ClickableItem.empty(ItemCreator(Material.GRAY_STAINED_GLASS_PANE) {
                    displayName("§0")
                }.build()))
            }
        }

        @Deprecated(message = "Use the adventure API instead")
        fun String.removeColorCodes(): String {
            return this.replace("§[0-9a-fk-or]".toRegex(), "")
        }

        fun List<ClickableItem>.center(row: Int): Map<SlotPos, ClickableItem> {
            return when (size) {
                1 -> mapOf(4 to this[0])
                2 -> mapOf(3 to this[0], 5 to this[1])
                3 -> mapOf(3 to this[0], 4 to this[1], 5 to this[2])
                4 -> mapOf(2 to this[0], 3 to this[1], 5 to this[2], 6 to this[3])
                5 -> mapOf(2 to this[0], 3 to this[1], 4 to this[2], 5 to this[3], 6 to this[4])
                6 -> mapOf(1 to this[0], 2 to this[1], 3 to this[2], 5 to this[3], 6 to this[4], 7 to this[5])
                7 -> mapOf(1 to this[0], 2 to this[1], 3 to this[2], 4 to this[3], 5 to this[4], 6 to this[5], 7 to this[6])
                else -> {
                    VanillaEnhancements.getPlugin().logger.severe("SpigotExtensions: List<ClickableItem>.center(): Received a list with more than 7 items! This is not supported! Please report this to the developer!")
                    mapOf()
                }
            }.mapKeys { SlotPos(row, it.key) }
        }

        fun InventoryContents.paginateItems(items: List<ClickableItem>, row: Int = 1, player: Player, paginationType: PaginationType = PaginationType.HORIZONTAL_5, // items per page: either 5 or 7
                                            noneItem: ItemCreator.() -> Unit, inventoryGetter: () -> SmartInventory) {
            val itemsPerPage = when (paginationType) {
                PaginationType.HORIZONTAL_5 -> 5
                PaginationType.HORIZONTAL_7 -> 7
            }
            if (items.isEmpty()) {
                set(row, 4, ClickableItem.empty(ItemCreator(Material.BARRIER) {
                    noneItem()
                }.build()))
            } else if (items.size <= itemsPerPage) {
                items.center(row).forEach(this::set)
            } else {
                val pagination = pagination()
                pagination.setItems(*items.toTypedArray())
                pagination.setItemsPerPage(itemsPerPage)
                pagination.addToIterator(newIterator(SlotIterator.Type.HORIZONTAL, row, if (itemsPerPage == 5) 2 else 1))

                if (!pagination.isLast) {
                    set(row, if (itemsPerPage == 5) 7 else 8, ClickableItem.of(
                        ItemCreator(Material.ARROW) {
                            displayName("§fNext Page")
                            addLore("§f§oShift click: Last page")
                        }.build()
                    ) { event ->
                        if (event.isShiftClick) {
                            inventoryGetter().open(player, pagination.last().page)
                        } else {
                            inventoryGetter().open(player, pagination.next().page)
                        }
                    })
                }

                if (!pagination.isFirst) {
                    set(row, if (itemsPerPage == 5) 1 else 0, ClickableItem.of(
                        ItemCreator(Material.ARROW) {
                            displayName("§fPrevious Page")
                            addLore("§f§oShift click: First page")
                        }.build()
                    ) { event ->
                        if (event.isShiftClick) {
                            inventoryGetter().open(player, pagination.first().page)
                        } else {
                            inventoryGetter().open(player, pagination.previous().page)
                        }
                    })
                }
            }
        }

        fun DyeColor.asChatColor(): ChatColor {
            return when(this) {
                DyeColor.BLACK -> ChatColor.BLACK
                DyeColor.BLUE -> ChatColor.BLUE
                DyeColor.BROWN -> ChatColor.DARK_RED
                DyeColor.CYAN -> ChatColor.AQUA
                DyeColor.GRAY -> ChatColor.GRAY
                DyeColor.GREEN -> ChatColor.GREEN
                DyeColor.LIGHT_BLUE -> ChatColor.AQUA
                DyeColor.LIME -> ChatColor.GREEN
                DyeColor.MAGENTA -> ChatColor.LIGHT_PURPLE
                DyeColor.ORANGE -> ChatColor.GOLD
                DyeColor.PINK -> ChatColor.LIGHT_PURPLE
                DyeColor.PURPLE -> ChatColor.DARK_PURPLE
                DyeColor.RED -> ChatColor.RED
                DyeColor.LIGHT_GRAY -> ChatColor.GRAY
                DyeColor.WHITE -> ChatColor.WHITE
                DyeColor.YELLOW -> ChatColor.YELLOW
            }
        }

        fun ItemStack.toEmptyClickableItem(): ClickableItem {
            return ClickableItem.empty(this)
        }

        fun Material.toEmptyClickableItem(): ClickableItem {
            return ItemStack(this).toEmptyClickableItem()
        }

        fun Message.send(receiver: CommandSender, veInstance: VanillaEnhancements, vararg args: Any) {
            veInstance.messageManager.send(this, receiver, *args)
        }

        /**
         * Converts a message with args to a component.
         * @param args The args for the message. Tags will be escaped for all objects that are not a [Component].
         */
        fun Message.asComponent(veInstance: VanillaEnhancements, vararg args: Any): Component {
            return veInstance.miniMessage().deserialize(
                veInstance.messageManager.format(veInstance.messageManager.get(this), args.map { arg ->
                    if (arg is Component) {
                        return@map veInstance.miniMessage().serialize(arg)
                    }
                    return@map veInstance.miniMessage().escapeTags(arg.toString())
                }.toTypedArray())
            )
        }

        fun String.removeNewlines(): String {
            return this.lines().joinToString(" ")
        }
    }

}

enum class PaginationType {
    HORIZONTAL_5, HORIZONTAL_7
}