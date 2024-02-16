package yt.sehrschlecht.vanillaenhancements.docsgenerator;

import org.bukkit.plugin.java.JavaPlugin;

public final class VEDocsGeneratorPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        VEDocsCommand command = new VEDocsCommand(this);
        getCommand("ve-docs").setExecutor(command);
        getCommand("ve-docs").setTabCompleter(command);
    }

}
