package yt.sehrschlecht.vanillaenhancements.modules.enhancements;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.config.Option;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.ticking.TickService;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.logging.Level;

public class ChatCoordinates extends VEModule {
    @Option
    public ConfigOption chatColor = new ConfigOption("chat_color", getKey(), "AQUA");
    @Option
    public ConfigOption sendWorld = new ConfigOption("send_world", getKey(), true);

    @Override
    public @NotNull String getName() {
        return "Chat coordinates";
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return new NamespacedKey(VanillaEnhancements.getPlugin(), "chat_coordinates");
    }

    @Override
    public void onEnable() {
        try {
            ChatColor.valueOf(chatColor.asString().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            getLogger().log(Level.SEVERE, "[" + getName() + "] Invalid chat color provided! Resetting to " + chatColor.getDefaultValue().toString() + ".");
            Config.set(chatColor, chatColor.getDefaultValue());
        }
    }

    @Override
    public void onDisable() {

    }

    @Override
    public @Nullable TickService getTickService() {
        return null;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if(!event.getMessage().contains("~ ~ ~")) return;
        Location location = event.getPlayer().getLocation();
        DecimalFormat format = new DecimalFormat("#");
        String text = ChatColor.valueOf(chatColor.asString()) + format.format(location.getX()) + " " + format.format(location.getY()) + " " + format.format(location.getZ()) + (sendWorld.asBoolean() ? " §7(" + ChatColor.valueOf(chatColor.asString()) + location.getWorld().getName() + "§7)": "") + "§f";
        event.setMessage(event.getMessage().replace("~ ~ ~", text));
    }
}
