package yt.sehrschlecht.vanillaenhancements.modules.enhancements;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.config.Option;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.ticking.TickService;
import yt.sehrschlecht.vanillaenhancements.utils.ExternalAPIs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

//WIP ToDo
public class CommandblockHolograms extends VEModule {
    private Map<Location, Hologram> holograms = new HashMap<>();
    @Option
    public ConfigOption range = new ConfigOption("range_to_display", getKey(), 5);
    @Option
    public ConfigOption wordsToShow = new ConfigOption("words_to_show", getKey(), 2);

    @Override
    public @NotNull String getName() {
        return "Command block holograms";
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return new NamespacedKey(VanillaEnhancements.getPlugin(), "commandblock_holograms");
    }

    @Override
    public boolean shouldEnable() {
        if(ExternalAPIs.isHolographicDisplaysEnabled()) {
            return true;
        } else {
            getLogger().log(Level.SEVERE, "[" + getName() + "] This Module requires HolographicDisplays! Please install HolographicDisplays from Bukkit.org and restart your server.");
            return false;
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public @Nullable TickService getTickService() {
        return new TickService(1, () -> {
            for (Map.Entry<Location, Hologram> entry : holograms.entrySet()) {
                Hologram hologram = entry.getValue();
                if(hologram.getCreationTimestamp() < (System.currentTimeMillis() - 10000)) {
                    hologram.delete();
                    holograms.remove(entry.getKey());
                }
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                Block block = player.getTargetBlock((Set<Material>) null, range.asInt());
                Hologram currentHolo = null;
                if(block.getType().name().contains("COMMAND_BLOCK")) {
                    CommandBlock commandBlock = (CommandBlock) block.getState();
                    Location location = block.getLocation().clone();
                    location.add(0, 1.5, 0);
                    location = getCenter(location);
                    if(!commandBlock.getCommand().isEmpty()) {
                        if(holograms.containsKey(location)) {
                            Hologram hologram = holograms.get(location);
                            hologram.clearLines();
                            hologram.appendTextLine(String.join(" ", commandBlock.getCommand().split(" ", wordsToShow.asInt())));
                            if(!hologram.getVisibilityManager().isVisibleTo(player)) {
                                hologram.getVisibilityManager().showTo(player);
                            }
                            currentHolo = hologram;
                        } else {
                            Hologram hologram = HologramsAPI.createHologram(getPlugin(), location);
                            hologram.appendTextLine(String.join(" ", commandBlock.getCommand().split(" ", wordsToShow.asInt())));
                            if(!hologram.getVisibilityManager().isVisibleTo(player)) {
                                hologram.getVisibilityManager().showTo(player);
                            }
                            currentHolo = hologram;
                            holograms.put(location, hologram);
                        }
                    }
                }
                for (Hologram hologram : holograms.values()) {
                    if(hologram != currentHolo && hologram.getVisibilityManager().isVisibleTo(player)) {
                        hologram.getVisibilityManager().hideTo(player);
                    }
                }
            }
        }, getKey());
    }

    public Location getCenter(Location loc) {
        return new Location(loc.getWorld(),
                getRelativeCoord(loc.getBlockX()),
                loc.getY(),
                getRelativeCoord(loc.getBlockZ()));
    }

    private double getRelativeCoord(int i) {
        double d = i;
        d += 0.5;
        return d;
    }
}
