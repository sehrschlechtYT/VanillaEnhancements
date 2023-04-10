package yt.sehrschlecht.vanillaenhancements.modules.inbuilt;

import com.google.gson.annotations.Since;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.modules.ModuleTag;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
public class ChatCoordinates extends VEModule {
    /*public ConfigOption chatColor = new ConfigOption(ChatColor.AQUA.name(), description);
    public ConfigOption secondChatColor = new ConfigOption(ChatColor.GRAY.name(), description);

    public ConfigOption sendWorld = new ConfigOption(true, description);*/

    @Override
    public @NotNull String getKey() {
        return "chat_coordinates";
    }

    public ChatCoordinates() {
        super("Allows players to send their coordinates in chat by typing \"~ ~ ~\".",
                INBUILT, ModuleTag.CHAT);
    }

    /*@Override
    public void onEnable() {
        super.onEnable();
        try {
            ChatColor.valueOf(chatColor.asString().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            getLogger().log(Level.SEVERE, "[" + getName() + "] Invalid chat color provided! Resetting to " + chatColor.getDefaultValue().toString() + ".");
            chatColor.reset();
        }
    }*/

    /*@EventHandler
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
    }*/

    @Override
    public JavaPlugin getPlugin() {
        return getVEInstance();
    }

    @Override
    public Material getDisplayItem() {
        return Material.FILLED_MAP;
    }

}
