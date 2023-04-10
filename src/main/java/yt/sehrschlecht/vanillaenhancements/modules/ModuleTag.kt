package yt.sehrschlecht.vanillaenhancements.modules

import org.bukkit.Material

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ModuleTag(displayName: String, key: String, description: String, displayItem: Material) : AbstractModuleCategorization(displayName, key, description, displayItem) {
    companion object {
        val APRIL_FOOLS_2023 = ModuleTag("April Fools 2023", "april_fools_2023", "Features from the april fools snapshot 23w13a_or_b", Material.CAKE)
        val RECIPES = ModuleTag("Recipes", "recipes", "Custom recipes", Material.CRAFTING_TABLE)
        val CHAT = ModuleTag("Chat", "chat", "Chat related features", Material.PAPER)
        val ENTITIES = ModuleTag("Entities", "entities", "Entity related features", Material.SPAWNER)
        val BLOCKS = ModuleTag("Blocks", "blocks", "Block related features", Material.GRASS_BLOCK)
        val MISC = ModuleTag("Misc", "misc", "Miscellaneous features", Material.BOOK)
    }
}