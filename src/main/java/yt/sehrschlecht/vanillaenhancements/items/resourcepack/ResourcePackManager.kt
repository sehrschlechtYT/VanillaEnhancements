package yt.sehrschlecht.vanillaenhancements.items.resourcepack

import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.modules.CustomTextureModule
import java.io.File

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ResourcePackManager(val plugin: VanillaEnhancements) {

    private val folder = File(plugin.dataFolder, "resourcepacks")
    val buildFolder = File(folder, "build")
    private val defaultPackFiles = mutableListOf("pack.mcmeta")

    fun initialize() {
        if (!folder.exists()) folder.mkdirs()
        if (buildFolder.exists()) {
            // clear build folder
            buildFolder.listFiles()?.forEach { it.delete() }
        } else {
            buildFolder.mkdirs()
        }
        if (!isEnabled()) return
        plugin.saveResource("resourcepacks/build/pack.mcmeta", false)
        // plugin.saveResource("resourcepacks/build/pack.png", false)
    }

    private fun isEnabled(): Boolean {
        return plugin.config.getBoolean("resource_pack.enabled")
    }

    fun buildPack() {
        if (!isEnabled()) return
        defaultPackFiles.forEach { file ->
            // get from classpath and copy to build folder
            val target = File(buildFolder, file)
            javaClass.getResourceAsStream("/resourcepacks/build/$file")?.copyTo(target.outputStream())
        }
        getBuilders().forEach { builder ->
            builder?.run(buildFolder)
        }
    }

    fun getBuilders() : List<ResourcePackBuilder?> {
        VanillaEnhancements.getPlugin().moduleRegistry.registeredModules.map { module ->
            if (module !is CustomTextureModule) return@map null
            return@map module.createResourcePack()
        }
        return listOf()
    }

}