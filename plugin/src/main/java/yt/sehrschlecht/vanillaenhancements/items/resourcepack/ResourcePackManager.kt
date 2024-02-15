package yt.sehrschlecht.vanillaenhancements.items.resourcepack

import org.bukkit.Material
import org.bukkit.event.Listener
import org.zeroturnaround.zip.ZipUtil
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.events.ResourcePackBuildCompletionEvent
import yt.sehrschlecht.vanillaenhancements.modules.CustomTextureProvider
import yt.sehrschlecht.vanillaenhancements.utils.debugging.Debug
import java.io.File

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ResourcePackManager(val plugin: VanillaEnhancements) : Listener {

    private val enabled = plugin.config.getBoolean("resource_pack.enabled")
    private val folder = File(plugin.dataFolder, "resourcepacks")
    private val buildFolder = File(folder, "build")
    private val defaultPackFiles = mutableListOf("pack.mcmeta")
    private val customModelData = mutableMapOf<Material, Int>()

    fun initialize() {
        if (!isEnabled()) return
        plugin.logger.info("Building resource pack...")
        val packFile = buildPack()
        plugin.logger.info("Finished building resource pack!")

        plugin.server.pluginManager.callEvent(
            ResourcePackBuildCompletionEvent(
                packFile
            )
        )
    }

    private fun isEnabled(): Boolean {
        return enabled
    }

    fun buildPack(): File? {
        Debug.RESOURCE_PACKS.log("Attempting to build resource pack...")
        if (!isEnabled()) {
            plugin.logger.warning("Tried to build resource pack but it is disabled in the config! (resource_pack.enabled=false)")
            return null
        }

        Debug.RESOURCE_PACKS.log("Setting up folders...")
        if (!folder.exists()) folder.mkdirs()
        if (buildFolder.exists()) {
            // clear build folder
            buildFolder.deleteRecursively()
            Debug.RESOURCE_PACKS.log("Cleared build folder!")
        }

        buildFolder.mkdirs()
        Debug.RESOURCE_PACKS.log("Created build folder!")
        Debug.RESOURCE_PACKS.log("Setting up folders done!")

        Debug.RESOURCE_PACKS.log("Copying default files...")
        defaultPackFiles.forEach { file ->
            // get from classpath and copy to build folder
            val target = File(buildFolder, file)
            javaClass.getResourceAsStream("/resourcepacks/build/$file")?.copyTo(target.outputStream())
            Debug.RESOURCE_PACKS.log("Copied $file to ${target.absolutePath}")
        }
        Debug.RESOURCE_PACKS.log("Copying default files done!")
        Debug.RESOURCE_PACKS.log("Running builders...")
        var i = 1
        getBuilders().forEach { builder ->
            builder?:return@forEach
            Debug.RESOURCE_PACKS.log("Running builder $i...")
            builder.run(buildFolder)
            Debug.RESOURCE_PACKS.log("Builder $i done!")
            i++
        }
        Debug.RESOURCE_PACKS.log("Running builders done!")
        Debug.RESOURCE_PACKS.log("Building resource pack done!")

        Debug.RESOURCE_PACKS.log("Zipping resource pack...")
        val packFile = zipPack()
        Debug.RESOURCE_PACKS.log("Zipping resource pack done!")
        return packFile
    }

    private fun getBuilders() : List<ResourcePackBuilder?> {
        return VanillaEnhancements.getPlugin().moduleRegistry.registeredModules.map { module ->
            if (module !is CustomTextureProvider) return@map null
            return@map module.createResourcePack()
        }
    }

    private fun zipPack(): File {
        val target = File(folder, "pack.zip")
        if (target.exists()) target.delete()
        ZipUtil.pack(buildFolder, target)
        return target
    }

    fun getNextCustomModelData(vanillaItem: Material): Int {
        var current = customModelData[vanillaItem] ?: 0
        current++
        customModelData[vanillaItem] = current
        return current
    }

}