package yt.sehrschlecht.vanillaenhancements.modules.inbuilt;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.schlechteutils.items.ItemBuilder;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.config.Option;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;
import yt.sehrschlecht.vanillaenhancements.utils.ItemUtils;

/**
 * <a href="https://www.reddit.com/r/minecraftsuggestions/comments/z3majo/crouch_shear_a_name_tagged_mob_to_get_the_name/">Source</a>
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class RemoveNametags extends VEModule {
    @Option
    public ConfigOption damageItem = new ConfigOption("damageItem", getModuleKey(), true);
    @Option
    public ConfigOption dropNametag = new ConfigOption("dropNametag", getModuleKey(), true);

    @Override
    public @NotNull String getKey() {
        return "remove_nametags";
    }

    @EventHandler
    public void onInteractOnEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if(!player.isSneaking()) return;
        if(!player.getEquipment().getItemInMainHand().getType().equals(Material.SHEARS)) return;
        Entity entity = event.getRightClicked();
        if(entity.getCustomName() == null) return;
        event.setCancelled(true);
        String customName = entity.getCustomName();
        if(dropNametag.asBoolean()) {
            entity.getWorld().dropItem(entity.getLocation(), new ItemBuilder(Material.NAME_TAG).setDisplayName(customName).build());
        }
        entity.setCustomNameVisible(false);
        entity.setCustomName(null);
        if(!player.getGameMode().equals(GameMode.CREATIVE) && damageItem.asBoolean()) {
            ItemUtils.damageItem(player.getEquipment().getItemInMainHand());
        }
        entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_SHEEP_SHEAR, 1, 1);
    }
}
