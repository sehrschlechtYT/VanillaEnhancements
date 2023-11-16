package yt.sehrschlecht.vanillaenhancements.gui

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.conversations.ConversationContext
import org.bukkit.conversations.ConversationFactory
import org.bukkit.conversations.Prompt
import org.bukkit.conversations.ValidatingPrompt
import org.bukkit.entity.Player
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.config.options.StringListOption
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator
import yt.sehrschlecht.vanillaenhancements.utils.PaginationType
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.addBackButton
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.fillBackground
import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.paginateItems

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ModifyStringListMenu(private val plugin: VanillaEnhancements, private val option: StringListOption, private val origin: SmartInventory) : InventoryProvider {

    companion object {
        fun getInventory(plugin: VanillaEnhancements, option: StringListOption, origin: SmartInventory): SmartInventory = SmartInventory.builder()
            .id("chooseDyeColor")
            .provider(ModifyStringListMenu(plugin, option, origin))
            .size(3, 9)
            .title("§lModify option values")
            .manager(plugin.inventoryManager)
            .build()
    }

    override fun init(player: Player, contents: InventoryContents) {
        contents.fill(null)
        contents.fillBackground()
        val items = option.get().map {
            ClickableItem.of(
                ItemCreator(Material.PAPER) {
                    displayName("§f§l${it}")
                    addLore("§9Right click to remove")
                }.build()
            ) { event ->
                if (!event.isRightClick) return@of
                val list = option.get().toMutableList()
                list.remove(it)
                option.set(list)
                init(player, contents)
            }
        }
        contents.paginateItems(items, player = player, paginationType = PaginationType.HORIZONTAL_7, noneItem = {
            displayName("§c§lNo values")
            addLongLore("There are no values yet for this option.", lineStart = "§c")
        }, inventoryGetter = { getInventory(plugin, option, origin) })
        contents.set(2, 0, ClickableItem.of(
            ItemCreator(Material.EMERALD) {
                displayName("§a§lAdd value")
                addLongLore("§fClick to add a new value to this option.")
            }.build()
        ) {
            val input = object: ValidatingPrompt() {
                override fun getPromptText(context: ConversationContext): String {
                    return "§lPlease input the value you want to add in the chat. Type §r§ocancel §r§f§lto cancel the process. §r§oThis prompt will time out after 30 seconds."
                }

                override fun isInputValid(context: ConversationContext, input: String): Boolean {
                    return input.isNotBlank()
                }

                override fun acceptValidatedInput(context: ConversationContext, input: String): Prompt? {
                    val list = option.get().toMutableList()
                    list.add(input)
                    option.set(list)
                    player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                    origin.open(player)
                    return null
                }
            }

            val conversationFactory = ConversationFactory(plugin)
                .withFirstPrompt(input)
                .withTimeout(30)
                .withEscapeSequence("cancel")
                .addConversationAbandonedListener { abandonedEvent ->
                    contents.inventory().open(player)
                    if (!abandonedEvent.gracefulExit()) {
                        player.sendMessage("§cCancelled input.")
                        player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f)
                    } else {
                        player.sendMessage("§aSaved value.")
                    }
                }
                .withLocalEcho(true)

            player.closeInventory()

            val conversation = conversationFactory.buildConversation(player)
            conversation.begin()

            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_BELL, 2f, 0f);
            player.sendTitle("§lAdd value", "Please enter a value in the chat.", 5, 100, 20)
        })
        contents.addBackButton { origin }
    }

    override fun update(player: Player, contents: InventoryContents) {
        val state = contents.property("state", 0)
        contents.setProperty("state", state + 1)

        if (state % 5 == 0) {
            init(player, contents)
        }
    }

}