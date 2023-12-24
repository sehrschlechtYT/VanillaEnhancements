package yt.sehrschlecht.vanillaenhancements.modules.inbuilt;

import com.google.gson.annotations.Since;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.vanillaenhancements.config.options.BooleanOption;
import yt.sehrschlecht.vanillaenhancements.modules.ModuleTag;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Since(1.0)
public class InvisibleItemFrames extends VEModule {
    public BooleanOption useMilkToMakeVisible = new BooleanOption(true,
            "Controls if players can use milk buckets to make item frames visible again.");

    public InvisibleItemFrames() {
        super("Allows players to make item frames invisible by using invisibility potions on them.",
                INBUILT, ModuleTag.ENTITIES, ModuleTag.BLOCKS);
    }

    @Override
    public @NotNull String getKey() {
        return "invisible_item_frames";
    }

    @EventHandler(ignoreCancelled = true)
    public void onInteract(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof ItemFrame itemFrame)) return;
        ItemStack stack = event.getPlayer().getEquipment().getItem(event.getHand());
        Player player = event.getPlayer();
        switch (stack.getType()) {
            case POTION -> {
                PotionMeta meta = (PotionMeta) stack.getItemMeta();
                if (meta == null) return;
                if (meta.getBasePotionData().getType().equals(PotionType.INVISIBILITY)) {
                    if (itemFrame.isVisible()) {
                        itemFrame.setVisible(false);
                        if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                            stack.setType(Material.GLASS_BOTTLE);
                        }
                        itemFrame.getWorld().playSound(itemFrame.getLocation(), Sound.ENTITY_GENERIC_DRINK, 1, 1);
                        event.setCancelled(true);
                    }
                }
            }
            case MILK_BUCKET -> {
                if (!useMilkToMakeVisible.get()) break;
                if (!itemFrame.isVisible()) {
                    itemFrame.setVisible(true);
                    if (!player.getGameMode().equals(GameMode.CREATIVE)) {
                        stack.setType(Material.BUCKET);
                    }
                    itemFrame.getWorld().playSound(itemFrame.getLocation(), Sound.ENTITY_GENERIC_DRINK, 1, 1);
                    event.setCancelled(true);
                }
            }
        }
    }

    @Override
    public JavaPlugin getPlugin() {
        return getVEInstance();
    }

    @Override
    public Material getDisplayItem() {
        return Material.ITEM_FRAME;
    }

}
