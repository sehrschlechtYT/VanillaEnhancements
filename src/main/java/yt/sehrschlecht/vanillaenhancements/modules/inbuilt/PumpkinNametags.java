package yt.sehrschlecht.vanillaenhancements.modules.inbuilt;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.ticking.Tick;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class PumpkinNametags extends VEModule {
    private Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

    @Override
    public @NotNull String getName() {
        return "Hide nametag if wearing pumpkin";
    }

    @Override
    public @NotNull String getKey() {
        return "pumpkin_hide_nametags";
    }

    @Tick(period = 5, executeNow = true)
    public void updateNametags() {
        //ToDo testing required
        for (Player player : Bukkit.getOnlinePlayers()) {
            Team team = scoreboard.getTeam(player.getName());
            if(team == null) continue;
            if(player.getEquipment().getItem(EquipmentSlot.HEAD) != null && player.getEquipment().getItem(EquipmentSlot.HEAD).getType().equals(Material.CARVED_PUMPKIN)) {
                if(!team.hasEntry(player.getName())) {
                    team.addEntry(player.getName());
                }
            } else if(team.hasEntry(player.getName())) {
                team.removeEntry(player.getName());
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Team team = scoreboard.registerNewTeam(player.getName());
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Team team = scoreboard.getTeam(player.getName());
        team.unregister();
    }
}
