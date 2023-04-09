package yt.sehrschlecht.vanillaenhancements.items.resourcepack

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bukkit.Bukkit
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import java.io.File

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ResourcePackServer {

    fun run(file: File, port: Int, plugin: VanillaEnhancements) {
        plugin.logger.info("Starting resource pack server...")
        val server = embeddedServer(Netty, port = port) {
            routing {
                get("/pack.zip") {
                    call.respondFile(file)
                }
            }
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable {
            server.start(wait = true)
            plugin.logger.info("Successfully started resource pack server on port $port!")
        })
    }

}