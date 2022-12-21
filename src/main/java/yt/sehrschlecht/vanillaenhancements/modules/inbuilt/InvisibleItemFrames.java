package yt.sehrschlecht.vanillaenhancements.modules.inbuilt;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.config.Option;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;

public class InvisibleItemFrames extends VEModule {
    @Option
    public ConfigOption useMilk = new ConfigOption("use_milk_to_make_visible", getModuleKey(), true);

    @Override
    public @NotNull String getKey() {
        return "invisible_item_frames";
    }

    @EventHandler(ignoreCancelled = true)
    public void onInteract(PlayerInteractEntityEvent event) {
        if(!(event.getRightClicked() instanceof ItemFrame)) return;
        ItemStack stack = event.getPlayer().getEquipment().getItem(event.getHand());
        ItemFrame itemFrame = (ItemFrame) event.getRightClicked();
        Player player = event.getPlayer();
        switch (stack.getType()) {
            case POTION:
                PotionMeta meta = (PotionMeta) stack.getItemMeta();
                if(meta == null) return;
                if(meta.getBasePotionData().getType().equals(PotionType.INVISIBILITY)) {
                    if(itemFrame.isVisible()) {
                        itemFrame.setVisible(false);
                        if(!player.getGameMode().equals(GameMode.CREATIVE)) {
                            stack.setType(Material.GLASS_BOTTLE);
                        }
                        itemFrame.getWorld().playSound(itemFrame.getLocation(), Sound.ENTITY_GENERIC_DRINK, 1, 1);
                        event.setCancelled(true);
                    }
                }
                break;
            case MILK_BUCKET:
                if(!useMilk.asBoolean()) break;
                if(!itemFrame.isVisible()) {
                    itemFrame.setVisible(true);
                    if(!player.getGameMode().equals(GameMode.CREATIVE)) {
                        stack.setType(Material.BUCKET);
                    }
                    itemFrame.getWorld().playSound(itemFrame.getLocation(), Sound.ENTITY_GENERIC_DRINK, 1, 1);
                    event.setCancelled(true);
                }
                break;
        }
    }
}
