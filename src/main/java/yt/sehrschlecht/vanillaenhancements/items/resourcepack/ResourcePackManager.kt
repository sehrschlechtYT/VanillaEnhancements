package yt.sehrschlecht.vanillaenhancements.items.resourcepack

import org.zeroturnaround.zip.ZipUtil
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.modules.CustomTextureModule
import yt.sehrschlecht.vanillaenhancements.utils.debugging.Debug
import java.io.File

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ResourcePackManager(val plugin: VanillaEnhancements) {

    private val folder = File(plugin.dataFolder, "resourcepacks")
    private val buildFolder = File(folder, "build")
    private val defaultPackFiles = mutableListOf("pack.mcmeta")

    fun initialize() {
        if (!isEnabled()) return //ToDo
    }

    private fun isEnabled(): Boolean {
        return plugin.config.getBoolean("resource_pack.enabled")
    }

    fun buildPack() {
        Debug.RESOURCE_PACKS.log("Attempting to build resource pack...")
        if (!isEnabled()) {
            plugin.logger.warning("Tried to build resource pack but it is disabled in the config! (resource_pack.enabled=false)")
            return
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
        zipPack()
        Debug.RESOURCE_PACKS.log("Zipping resource pack done!")
    }

    private fun getBuilders() : List<ResourcePackBuilder?> {
        return VanillaEnhancements.getPlugin().moduleRegistry.registeredModules.map { module ->
            if (module !is CustomTextureModule) return@map null
            return@map module.createResourcePack()
        }
    }

    private fun zipPack() {
        val target = File(folder, "pack.zip")
        if (target.exists()) target.delete()
        ZipUtil.pack(buildFolder, target)
    }

}