package yt.sehrschlecht.vanillaenhancements;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import fr.minuskube.inv.InventoryManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.gui.VECommand;
import yt.sehrschlecht.vanillaenhancements.items.ItemManager;
import yt.sehrschlecht.vanillaenhancements.items.VEItemListener;
import yt.sehrschlecht.vanillaenhancements.items.resourcepack.ResourcePackManager;
import yt.sehrschlecht.vanillaenhancements.modules.ModuleRegistry;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.modules.inbuilt.*;
import yt.sehrschlecht.vanillaenhancements.modules.inbuilt.aprilfools_2023.*;
import yt.sehrschlecht.vanillaenhancements.modules.inbuilt.recipes.*;
import yt.sehrschlecht.vanillaenhancements.recipes.RecipeManager;
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
    private RecipeManager recipeManager;
    private ResourcePackManager resourcePackManager;
    private ItemManager itemManager;
    private InventoryManager inventoryManager;

    @Override
    public void onEnable() {
        plugin = this;

        debug = new Debug();

        if (debug.isEnabled() && debug.componentEnabled(Debug.ComponentType.DELETE_CONFIG_ON_STARTUP)) {
            getLogger().warning("Attempting to delete config.yml (this behaviour was enabled in the .debug file)");
            File configFile = new File(getDataFolder(), "config.yml");
            if (configFile.exists()) {
                configFile.delete();
                getLogger().warning("Successfully deleted config.yml");
            } else {
                getLogger().warning("config.yml does not exist (yet), skipping deletion");
            }
        }

        ExternalAPIs.init();

        itemManager = new ItemManager(this);
        recipeManager = new RecipeManager();
        tickServiceExecutor = new TickServiceExecutor();
        resourcePackManager = new ResourcePackManager(this);
        inventoryManager = new InventoryManager(this);

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
                new MoreShapelessRecipes(),
                new MoreBlackstoneRecipes(),
                new SmeltConcretePowderToGlass(),
                new CraftNetheriteGearWithoutDiamonds(),
                new MobGriefingControl(),

                // 23w13a_or_b modules
                new AttackKnockback(),
                new AlwaysFlying(),
                new BasaltGenReplace(),
                new CobblestoneGenReplace(),
                new ObsidianGenReplace(),
                new StoneGenReplace(),
                new IceGenReplace(), // not in 23w13a_or_b but related to the other modules above
                new AlwaysThunder(),
                new BedPVP(),
                new Beeloons(),
                new ChargedCreepers(),
                new DefaultSheepColor(),
                new DisableShieldBlocking(),
                new FrenchMode()
        );

        createConfig();

        registerModules();

        tickServiceExecutor.startTicking();
        recipeManager.discoverRecipes();
        resourcePackManager.initialize();
        itemManager.initialize();
        inventoryManager.init();

        getCommand("ve-debug").setExecutor(new DebugCommand());
        getCommand("ve-debug").setTabCompleter(new DebugCommand());

        VECommand veCommand = new VECommand(this);
        getCommand("ve").setExecutor(veCommand);
        getCommand("ve").setTabCompleter(veCommand);

        registerListeners(
                new VEItemListener()
        );
    }

    @Override
    public void onDisable() {
        moduleRegistry.shutdown();
    }

    private void createConfig() {
        try {
            configuration = YamlDocument.create(
                    new File(getDataFolder(), "config.yml"),
                    Objects.requireNonNull(getResource("config.yml")),
                    GeneralSettings.DEFAULT,
                    LoaderSettings.builder().setAutoUpdate(true).build(),
                    DumperSettings.DEFAULT,
                    UpdaterSettings.builder().setVersioning(new BasicVersioning("config-version")).setKeepAll(true).build()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Config(configuration).init();
    }

    public void registerModules() {
        moduleRegistry = new ModuleRegistry(this);
        for (VEModule module : inbuiltModules) {
            moduleRegistry.registerModule(module);
            Debug.MODULES.log("Registered module {}", module.getName());
        }
    }

    public void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    public static VanillaEnhancements getPlugin() {
        return plugin;
    }

    public static String getPrefix() { // ToDo: Currently unused
        return Config.getInstance().message("prefix");
    }

    /**
     * @deprecated The list of inbuilt modules will be removed in order to improve support for external plugins.
     */
    @Deprecated(forRemoval = true)
    public List<VEModule> getInbuiltModules() {
        return inbuiltModules;
    }

    public ModuleRegistry getModuleRegistry() {
        return moduleRegistry;
    }

    public TickServiceExecutor getTickServiceExecutor() {
        return tickServiceExecutor;
    }

    public RecipeManager getRecipeManager() {
        return recipeManager;
    }

    public ResourcePackManager getResourcePackManager() {
        return resourcePackManager;
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public Debug getDebug() {
        return debug;
    }
}
