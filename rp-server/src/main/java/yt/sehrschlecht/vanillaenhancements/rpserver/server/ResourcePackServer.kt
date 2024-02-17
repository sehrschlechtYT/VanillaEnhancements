package yt.sehrschlecht.vanillaenhancements.rpserver.server

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bukkit.Bukkit
import yt.sehrschlecht.vanillaenhancements.rpserver.VERPServerPlugin
import java.io.File

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ResourcePackServer {
    private lateinit var server: ApplicationEngine;

    fun run(file: File, port: Int, plugin: VERPServerPlugin) {
        plugin.logger.info("Starting resource pack server...")
        server = embeddedServer(Netty, port = port) {
            routing {
                get("/pack.zip") {
                    call.respondFile(file)
                }
            }
        }
        // ToDo redirect log output to plugin logger
        Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable {
            // ToDo maybe run sync to prevent exception on reload
            // The exception is likely caused by the classloader no longer being able to find Ktor classes because they are unloaded
            server.start(wait = true)
            plugin.logger.info("Resource pack server has shut down!")
        })
    }

    fun stop() {
        server.stop() // ToDo - Doesn't work because the shutdown takes some time and the plugin is disabled instantly
    }

}