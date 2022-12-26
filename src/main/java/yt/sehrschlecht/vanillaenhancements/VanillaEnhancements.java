package yt.sehrschlecht.vanillaenhancements;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import org.bukkit.plugin.java.JavaPlugin;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.modules.ModuleRegistry;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.modules.inbuilt.*;
import yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes.*;
import yt.sehrschlecht.vanillaenhancements.ticking.TickServiceExecutor;
import yt.sehrschlecht.vanillaenhancements.utils.ExternalAPIs;
import yt.sehrschlecht.vanillaenhancements.utils.debugging.Debug;
import yt.sehrschlecht.vanillaenhancements.utils.debugging.DebugCommand;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public final class VanillaEnhancements extends JavaPlugin {
    private static VanillaEnhancements plugin;
    private static YamlDocument configuration;

    private List<VEModule> inbuiltModules;
    private ModuleRegistry moduleRegistry;
    private TickServiceExecutor tickServiceExecutor;
    private Debug debug;

    @Override
    public void onEnable() {
        plugin = this;

        debug = new Debug();

        ExternalAPIs.init();

        tickServiceExecutor = new TickServiceExecutor();

        inbuiltModules = Arrays.asList(
                new UnstripLogs(),
                //new GlowSacFishing(), - isn't working & bad practice since loot tables are not used
                new CobblestoneInStoneCutter(),
                new CraftChainArmor(),
                new RottenFleshSmelting(),
                new ZombiesDryToHusks(),
                new CreativeKeepInventory(),
                new InvisibleItemFrames(),
                //new ChatCoordinates(), - doesn't work in 1.19
                new PumpkinNametags(),
                new OldRecipes(),
                new SmeltConcreteToConcretePowder(),
                new RemoveNametags(),
                new CraftSlabsToBlocks(),
                new CraftStairsToBlocks(),
                new CraftBlocksToTwoSlabs(),
                new ImprovedDispenserCrafting(),
                new CraftCoalToBlackDye(),
                new DyeSand(),
                new MoreShapelessRecipes()
        );

        createConfig();

        registerModules();

        tickServiceExecutor.startTicking();

        getCommand("ve-debug").setExecutor(new DebugCommand());
        getCommand("ve-debug").setTabCompleter(new DebugCommand());
    }

    private void createConfig() {
        try {
            configuration = YamlDocument.create(
                    new File(getDataFolder(), "config.yml"),
                    Objects.requireNonNull(getResource("config.yml")),
                    GeneralSettings.DEFAULT,
                    LoaderSettings.builder().setAutoUpdate(true).build(),
                    DumperSettings.DEFAULT,
                    UpdaterSettings.builder().setVersioning(new BasicVersioning("config-version")).build()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Config(configuration).init();
    }




    public void registerModules() {
        moduleRegistry = new ModuleRegistry();
        for (VEModule module : inbuiltModules) {
            moduleRegistry.registerModule(module);
        }
    }

    @Override
    public void onDisable() {

    }

    public static VanillaEnhancements getPlugin() {
        return plugin;
    }

    public static String getPrefix() {
        return Config.getInstance().message("prefix");
    }

    public List<VEModule> getInbuiltModules() {
        return inbuiltModules;
    }

    public ModuleRegistry getModuleRegistry() {
        return moduleRegistry;
    }

    public TickServiceExecutor getTickServiceExecutor() {
        return tickServiceExecutor;
    }

    public Debug getDebug() {
        return debug;
    }
}
