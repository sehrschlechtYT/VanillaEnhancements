package yt.sehrschlecht.vanillaenhancements.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ExternalAPIs {
    private static boolean holographicDisplaysEnabled;

    public static void init() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        holographicDisplaysEnabled = pluginManager.isPluginEnabled("HolographicDisplays");
    }

    public static boolean isHolographicDisplaysEnabled() {
        return holographicDisplaysEnabled;
    }
}
