package yt.sehrschlecht.vanillaenhancements.items.resourcepack

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.zeroturnaround.zip.ZipUtil
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.modules.CustomTextureProvider
import yt.sehrschlecht.vanillaenhancements.utils.debugging.Debug
import java.io.File
import java.net.URI
import java.security.MessageDigest

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
    private lateinit var packUrl: String
    private lateinit var hash: ByteArray
    private val force = plugin.config.getBoolean("resource_pack.force")
    private val prompt = plugin.config.getString("resource_pack.prompt")
    private lateinit var server: ResourcePackServer

    fun initialize() {
        if (!isEnabled()) return
        plugin.logger.info("Building resource pack...")
        val packFile = buildPack()
        plugin.logger.info("Finished building resource pack!")
        if (packFile == null) {
            plugin.logger.severe("Resource pack file is null! The resource pack server will not be started!")
            return
        }

        if (plugin.config.getBoolean("resource_pack.3rd_party.enabled")) {
            plugin.logger.info("Using 3rd party resource pack server.")
            packUrl = plugin.config.getString("resource_pack.3rd_party.url") ?: ""
            if (packUrl.isEmpty()) {
                plugin.logger.severe("3rd party is enabled but no url is set in the config! (resource_pack.3rd_party.url)")
                return
            }
            val providedHash = plugin.config.getString("resource_pack.3rd_party.hash") ?: ""
            hash = if (providedHash.isEmpty()) {
                calculateHash(packUrl)
            } else {
                providedHash.toByteArray()
            }
        } else {
            plugin.logger.info("Starting resource pack server...")
            server = runServer(packFile)
            plugin.logger.info("Resource pack server started!")

            val port = plugin.config.getInt("resource_pack.port")
            val hostname = plugin.config.getString("resource_pack.hostname")
            packUrl = "http://$hostname:$port/pack.zip" // ToDo support https
            hash = calculateHash(packFile)
        }
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    private fun calculateHash(file: File): ByteArray {
        with(file.readBytes()) {
            return calculateHash(this)
        }
    }

    private fun calculateHash(url: String): ByteArray {
        with(URI(url).toURL().openStream().readBytes()) {
            return calculateHash(this)
        }
    }

    private fun calculateHash(bytes: ByteArray): ByteArray {
        return MessageDigest.getInstance("SHA-1").digest(bytes)
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

    private fun runServer(file: File): ResourcePackServer {
        val port = plugin.config.getInt("resource_pack.port")
        val server = ResourcePackServer()
        server.run(file, port, plugin)
        return server
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

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        if (!isEnabled()) return
        event.player.setResourcePack(packUrl, hash, prompt, force)
    }

    fun disable() {
        if (!isEnabled()) return
        server.stop()
    }

}