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

    @Override
    public void onEnable() {
        plugin = this;

        ExternalAPIs.init();

        inbuiltModules = Arrays.asList(
                new UnstripLogs(),
                new GlowSacFishing(),
                new CobblestoneInStoneCutter(),
                new CraftChainArmor(),
                new RottenFleshSmelting(),
                new ZombiesDryToHusks(),
                new CreativeKeepInventory(),
                new InvisibleItemFrames(),
                //new ChatCoordinates(),
                new PumpkinNametags(),
                new OldRecipes(),
                new SmeltConcreteToConcretePowder(),
                new RemoveNametags()
        );

        createConfig();

        registerModules();

        tickServiceExecutor = new TickServiceExecutor();
        tickServiceExecutor.startTicking();
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
}
