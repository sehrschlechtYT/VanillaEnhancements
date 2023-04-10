package yt.sehrschlecht.vanillaenhancements.modules

import org.bukkit.Material

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ModuleCategory(displayName: String, key: String, description: String, displayItem: Material) : AbstractModuleCategorization(displayName, key, description, displayItem) {
    companion object {
        val INBUILT = ModuleCategory("Inbuilt", "inbuilt", "Modules that are shipped with the VE plugin by default", Material.BOOK)
        val EXTERNAL = ModuleCategory("External", "external", "Uncategorized modules from other plugins", Material.WRITABLE_BOOK)
    }
}