package yt.sehrschlecht.vanillaenhancements.modules

import org.bukkit.Material
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ModuleTag(displayName: String, key: String, description: String, displayItem: Material) : AbstractModuleCategorization(displayName, key, description, displayItem) {

    companion object {
        @JvmField val APRIL_FOOLS_2023 = ModuleTag("April Fools 2023", "april_fools_2023", "Features from the april fools snapshot 23w13a_or_b", Material.CAKE)
        @JvmField val RECIPES = ModuleTag("Recipes", "recipes", "Custom recipes", Material.CRAFTING_TABLE)
        @JvmField val CHAT = ModuleTag("Chat", "chat", "Chat related features", Material.PAPER)
        @JvmField val ENTITIES = ModuleTag("Entities", "entities", "Entity related features", Material.SPAWNER)
        @JvmField val BLOCKS = ModuleTag("Blocks", "blocks", "Block related features", Material.COBBLESTONE)
        @JvmField val ITEMS = ModuleTag("Items", "items", "Item related features", Material.ITEM_FRAME)
        @JvmField val CUSTOM_ITEMS = ModuleTag("Custom Items", "custom_items", "Items with custom textures and functionality", Material.CHEST)
        @JvmField val WORLD = ModuleTag("World", "world", "World related features", Material.GRASS_BLOCK)
        @JvmField val GEN_REPLACE = ModuleTag("Gen Replace", "gen_replace", "Replace generated blocks", Material.OBSIDIAN)
        @JvmField val VANILLA_TWEAKS = ModuleTag("VanillaTweaks", "vanilla_tweaks", "Features from the VanillaTweaks datapacks", Material.COMPARATOR)
        @JvmField val FUN = ModuleTag("Fun", "fun", "Fun features", Material.TNT)
        @JvmField val UTILITY = ModuleTag("Utility", "utility", "Utility features (mostly for admins)", Material.COMMAND_BLOCK)
        @JvmField val OLD_FEATURES = ModuleTag("Old Features", "old_features", "Features that were removed from the game", Material.CLOCK)
        @JvmField val MISC = ModuleTag("Misc", "misc", "Miscellaneous features", Material.BOOK)
    }

    fun buildIcon(modify: ItemCreator.() -> Unit): ItemCreator {
        val creator = ItemCreator(displayItem) {
            displayName("§f$displayName")
            lore("§f$description")
        }
        modify(creator)
        return creator
    }

    fun buildIcon(): ItemCreator {
        return buildIcon {}
    }

}