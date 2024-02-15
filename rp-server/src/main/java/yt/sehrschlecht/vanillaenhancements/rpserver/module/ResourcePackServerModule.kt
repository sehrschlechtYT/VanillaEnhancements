package yt.sehrschlecht.vanillaenhancements.rpserver.module

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import yt.sehrschlecht.vanillaenhancements.config.options.BooleanOption
import yt.sehrschlecht.vanillaenhancements.config.options.IntegerOption
import yt.sehrschlecht.vanillaenhancements.config.options.StringOption
import yt.sehrschlecht.vanillaenhancements.events.ResourcePackBuildCompletionEvent
import yt.sehrschlecht.vanillaenhancements.modules.ModuleCategory
import yt.sehrschlecht.vanillaenhancements.modules.ModuleTag
import yt.sehrschlecht.vanillaenhancements.modules.VEModule
import yt.sehrschlecht.vanillaenhancements.rpserver.VERPServerPlugin
import yt.sehrschlecht.vanillaenhancements.rpserver.server.ResourcePackServer
import java.io.File
import java.security.MessageDigest

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ResourcePackServerModule(val plugin: VERPServerPlugin, moduleCategory: ModuleCategory) : VEModule(
    "Addon for VanillaEnhancements that provides an inbuilt HTTP server for resource pack hosting.",
    moduleCategory,
    ModuleTag.MISC
) {

    val port = IntegerOption(
        8080,
        "The port on which the resource pack server should listen. This port must be open for the players to be able to download the resource pack.",
        1,
        65535,
        1
    )
    val hostname = StringOption(
        "http://127.0.0.1",
        "The hostname of the server without a trailing slash. This is used to create the download link for the resource pack."
    )

    val force = BooleanOption(
        false,
        "Whether players should be forced to use the resource pack."
    )
    val prompt = StringOption(
        "Please download the resource pack to use custom textures and models.",
        "The prompt message that is displayed on the download screen."
    )

    private var server: ResourcePackServer? = null
    private var packUrl: String? = null
    private var hash: ByteArray? = null

    override fun getKey(): String {
        return "rp_server"
    }

    override fun getName(): String {
        return "Resource Pack Server"
    }

    /**
     * @return An instance of the plugin this module belongs to.
     */
    override fun getPlugin(): JavaPlugin {
        return plugin
    }

    override fun getDisplayItem(): Material {
        return Material.COMMAND_BLOCK_MINECART
    }

    @EventHandler
    fun onResourcePackBuildCompletion(event: ResourcePackBuildCompletionEvent) {
        val packFile = event.outputFile
        if (packFile == null) {
            plugin.logger.severe("Resource pack file is null! The resource pack server will not be started!")
            return
        }
        plugin.logger.info("Starting resource pack server...")
        server = runServer(packFile)
        plugin.logger.info("Resource pack server started!")

        packUrl = "${hostname.get()}:${port.get()}/pack.zip"
        hash = calculateHash(packFile)
    }

    private fun calculateHash(file: File): ByteArray {
        with(file.readBytes()) {
            return calculateHash(this)
        }
    }

    private fun calculateHash(bytes: ByteArray): ByteArray {
        return MessageDigest.getInstance("SHA-1").digest(bytes)
    }

    private fun runServer(file: File): ResourcePackServer {
        val port = port.get()
        val server = ResourcePackServer()
        server.run(file, port, plugin)
        return server
    }

    override fun onDisable() {
        server?.stop()
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        event.player.setResourcePack(packUrl ?: return, hash, prompt.get(), force.get())
    }

    @EventHandler
    fun onCommand(event: PlayerCommandPreprocessEvent) {
        if (event.message.equals("/reload confirm", true)) {
            event.player.sendMessage("§c§lReloading the server will break the resource pack server of VanillaEnhancements! Please restart the server instead.")
        }
    }


}