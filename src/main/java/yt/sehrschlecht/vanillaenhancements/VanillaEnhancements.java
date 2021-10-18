package yt.sehrschlecht.vanillaenhancements;

import co.aikar.commands.PaperCommandManager;
import io.papermc.lib.PaperLib;
import org.bukkit.plugin.java.JavaPlugin;
import yt.sehrschlecht.vanillaenhancements.commands.VECommand;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.modules.ModuleRegistry;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.modules.enhancements.*;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public final class VanillaEnhancements extends JavaPlugin {
    private static VanillaEnhancements plugin;
    private static PaperCommandManager commandManager;

    public static List<VEModule> modules;

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();

        getLogger().log(Level.INFO, "Thank you for using vanilla enhancements!");
        PaperLib.suggestPaper(this);

        modules = Arrays.asList(
                new UnstripLogs(),
                new GlowSacFishing(),
                new CobblestoneInStoneCutter(),
                new CraftChainArmor(),
                new RottenFleshSmelting(),
                new ZombiesDryToHusks(),
                new CreativeKeepInventory()
        );

        Config.init();

        registerModules();

        commandManager = new PaperCommandManager(this);

        commandManager.getCommandCompletions().registerCompletion("modules", c -> ModuleRegistry.getEnabledModules().stream().map(m -> m.getKey().getKey()).collect(Collectors.toList()));

        commandManager.registerCommand(new VECommand());
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

    public static PaperCommandManager getCommandManager() {
        return commandManager;
    }

    public static String getPrefix() {
        return Config.message("prefix");
    }
}
