package yt.sehrschlecht.vanillaenhancements.modules.inbuilt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.config.Option;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.logging.Level;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class ChatCoordinates extends VEModule {
    @Option
    public ConfigOption chatColor = new ConfigOption("main_chat_color", getModuleKey(), ChatColor.AQUA.name());
    @Option
    public ConfigOption secondChatColor = new ConfigOption("second_chat_color", getModuleKey(), ChatColor.GRAY.name());

    @Option
    public ConfigOption sendWorld = new ConfigOption("send_world", getModuleKey(), true);

    @Override
    public @NotNull String getKey() {
        return "chat_coordinates";
    }

    @Override
    public void onEnable() {
        super.onEnable();
        try {
            ChatColor.valueOf(chatColor.asString().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            getLogger().log(Level.SEVERE, "[" + getName() + "] Invalid chat color provided! Resetting to " + chatColor.getDefaultValue().toString() + ".");
            Config.getInstance().set(chatColor, chatColor.getDefaultValue());
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if(!event.getMessage().contains("~ ~ ~")) return;
        Location location = event.getPlayer().getLocation();
        DecimalFormat format = new DecimalFormat("#");
        String text = chatColor.asChatColor() + format.format(location.getX()) + " " + format.format(location.getY()) + " " + format.format(location.getZ())
                + (sendWorld.asBoolean() ? " " + secondChatColor.asChatColor() + "(" + chatColor.asChatColor() + location.getWorld().getName() + secondChatColor.asChatColor() + ")": "") + "§f";
        event.setCancelled(true);
        //ToDo message is sent twice
        //event.setMessage(event.getMessage().replace("~ ~ ~", text)); - doesn't work due to 1.19 chat signing (https://github.com/SpigotMC/BungeeCord/issues/3336)
        //event.getPlayer().chat(event.getMessage().replace("~ ~ ~", text)); - doesn't work because player get's kicked for illegal characters
        Bukkit.broadcastMessage(
                event.getFormat().formatted(event.getPlayer().getDisplayName(), event.getMessage().replace("~ ~ ~", text).strip())
        );
    }
}
