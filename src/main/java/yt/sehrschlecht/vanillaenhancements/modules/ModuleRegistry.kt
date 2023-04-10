package yt.sehrschlecht.vanillaenhancements.modules

import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.event.HandlerList
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements
import yt.sehrschlecht.vanillaenhancements.ticking.Tick
import yt.sehrschlecht.vanillaenhancements.utils.debugging.Debug
import java.util.logging.Level

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
class ModuleRegistry {

    val enabledModules: MutableList<VEModule> = mutableListOf()
    val registeredModules: MutableList<VEModule> = mutableListOf()
    private val logger = VanillaEnhancements.getPlugin().logger

    fun registerModule(module: VEModule): Boolean {
        val inbuilt = module.moduleKey.namespace.equals(VanillaEnhancements.getPlugin().name, ignoreCase = true)
        Debug.MODULES.log(
            "Registering {} module {}...",
            if (inbuilt) "inbuilt" else "external",
            if (inbuilt) module.moduleKey.key else module.moduleKey
        )
        registeredModules.add(module)
        module.initialize()
        registerTickServices(module)
        if (!module.isEnabled) {
            Debug.MODULES.log("Module ${module.moduleKey} is disabled in config, skipping...")
            return false
        }
        return enableModule(module)
    }

    fun enableModule(module: VEModule): Boolean {
        return try {
            Debug.MODULES.log("Enabling module ${module.moduleKey}...")
            if (!module.shouldEnable()) {
                Debug.MODULES.log("Module ${module.moduleKey} should not be enabled, skipping...")
                return false
            }
            module.onEnable()
            enabledModules.add(module)
            Bukkit.getPluginManager().registerEvents(module, VanillaEnhancements.getPlugin())
            Debug.MODULES.log("Enabled module ${module.moduleKey}!")
            true
        } catch (throwable: Throwable) {
            logger.log(Level.SEVERE, "The module ${module.moduleKey} couldn't be loaded due to an error:")
            logger.log(Level.SEVERE, throwable.message)
            if (VanillaEnhancements.getPlugin().debug.isEnabled) {
                throwable.printStackTrace()
            }
            false
        }
    }

    fun disableModule(module: VEModule) {
        module.onDisable()
        HandlerList.unregisterAll(module)
        enabledModules.remove(module)
        Debug.MODULES.log("Disabled module ${module.moduleKey}!")
    }

    private fun registerTickServices(module: VEModule) {
        Debug.TICK_SERVICES.log("Registering tick services for module ${module.moduleKey}...")
        val executor = VanillaEnhancements.getPlugin().tickServiceExecutor
        val clazz: Class<out VEModule> = module.javaClass
        for (method in clazz.methods) {
            Debug.TICK_SERVICES.log("Checking method ${method.name}...")
            if (method.isAnnotationPresent(Tick::class.java)) {
                Debug.TICK_SERVICES.log("Method ${method.name} is annotated with @Tick, registering...")
                executor.addTickService(module, method.getAnnotation(Tick::class.java), method)
            }
        }
    }

    fun unregisterAllModules() {
        Debug.MODULES.log("Unregistering all modules...")
        enabledModules.clear()
        registeredModules.clear()
    }

    fun isEnabled(moduleKey: NamespacedKey): Boolean {
        return if (getModule(moduleKey) == null) false else enabledModules.contains(getModule(moduleKey))
    }

    fun getModule(key: NamespacedKey): VEModule? {
        return registeredModules.stream().filter { module -> module?.moduleKey == key }.findFirst().orElse(null)
    }

    fun getInstance(clazz: Class<out VEModule?>): VEModule? {
        return enabledModules.stream().filter { module -> module?.javaClass == clazz }.findFirst().orElse(null)
    }

    fun collectCategories(): List<ModuleCategory> {
        return registeredModules.map { it.category }.distinct()
    }

    fun collectTags(): List<ModuleTag> {
        return registeredModules.flatMap { it.tags }.distinct()
    }

}
