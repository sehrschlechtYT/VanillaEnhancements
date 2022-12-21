package yt.sehrschlecht.vanillaenhancements.modules.inbuilt;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
    public ConfigOption chatColor = new ConfigOption("main_chat_color", getModuleKey(), ChatColor.AQUA.name());
    @Option
    public ConfigOption secondChatColor = new ConfigOption("second_chat_color", getModuleKey(), ChatColor.GRAY.name());

    @Option
    public ConfigOption sendWorld = new ConfigOption("send_world", getModuleKey(), true);

    @Override
    public @NotNull String getName() {
        return "Chat coordinates";
    }

    @Override
    public @NotNull String getKey() {
        return "chat_coordinates";
    }

    @Override
    public void onEnable() {
        try {
            ChatColor.valueOf(chatColor.asString().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            getLogger().log(Level.SEVERE, "[" + getName() + "] Invalid chat color provided! Resetting to " + chatColor.getDefaultValue().toString() + ".");
            Config.getInstance().set(chatColor, chatColor.getDefaultValue());
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
        String text = chatColor.asChatColor() + format.format(location.getX()) + " " + format.format(location.getY()) + " " + format.format(location.getZ())
                + (sendWorld.asBoolean() ? " " + secondChatColor.asChatColor() + "(" + chatColor.asChatColor() + location.getWorld().getName() + secondChatColor.asChatColor() + ")": "") + "§f";
        event.setMessage(event.getMessage().replace("~ ~ ~", text));
    }
}
