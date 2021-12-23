package yt.sehrschlecht.vanillaenhancements;

import io.papermc.lib.PaperLib;
import org.bukkit.plugin.java.JavaPlugin;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.modules.ModuleRegistry;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.modules.enhancements.*;
import yt.sehrschlecht.vanillaenhancements.ticking.TickServiceExecutor;
import yt.sehrschlecht.vanillaenhancements.utils.ExternalAPIs;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public final class VanillaEnhancements extends JavaPlugin {
    private static VanillaEnhancements plugin;
    public static List<VEModule> modules;

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();

        getLogger().log(Level.INFO, "Thank you for using vanilla enhancements!");
        PaperLib.suggestPaper(this);

        ExternalAPIs.init();

        modules = Arrays.asList(
                new UnstripLogs(),
                new GlowSacFishing(),
                new CobblestoneInStoneCutter(),
                new CraftChainArmor(),
                new RottenFleshSmelting(),
                new ZombiesDryToHusks(),
                new CreativeKeepInventory(),
                new InvisibleItemFrames(),
                new ChatCoordinates(),
                new PumpkinNametags(),
                new OldRecipes()
        );

        Config.init();

        registerModules();

        TickServiceExecutor.startTicking();
    }

    public void registerModules() {
        for (VEModule module : modules) {
            ModuleRegistry.registerModule(module);
        }
    }

    @Override
    public void onDisable() {

    }

    public static VanillaEnhancements getPlugin() {
        return plugin;
    }

    public static String getPrefix() {
        return Config.message("prefix");
    }
}
