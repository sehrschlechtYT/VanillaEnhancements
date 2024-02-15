package yt.sehrschlecht.vanillaenhancements.rpserver;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.modules.ModuleCategory;
import yt.sehrschlecht.vanillaenhancements.rpserver.module.ResourcePackServerModule;

public final class VERPServerPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        VanillaEnhancements.getPlugin().getModuleRegistry().registerModules(
            new ResourcePackServerModule(this, new ModuleCategory(
                    "RP Server",
                    "rpserver",
                    "Modules that belong to the Resource Pack Server Extension for VE",
                    Material.COMMAND_BLOCK_MINECART
            ))
        );
    }

}
