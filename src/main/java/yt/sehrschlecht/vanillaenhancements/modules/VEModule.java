package yt.sehrschlecht.vanillaenhancements.modules;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.config.options.BooleanOption;
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator;
import yt.sehrschlecht.vanillaenhancements.utils.ModuleUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public abstract class VEModule implements Listener {

    private final @Nullable String description;
    private final @Nullable String since;
    private final @NotNull ModuleCategory category;
    protected final @NotNull List<ModuleTag> tags = new ArrayList<>();

    public final BooleanOption enabled = new BooleanOption(false, null);

    protected final static ModuleCategory INBUILT = ModuleCategory.Companion.getINBUILT();
    protected final static ModuleCategory EXTERNAL = ModuleCategory.Companion.getEXTERNAL();

    /**
     * @param description A <b>short</b> description of the module.
     * @param since       The version since the module is available.
     * @param category
     * @param displayItem
     */
    public VEModule(@Nullable String description, @Nullable String since, @NotNull ModuleCategory category, ModuleTag... tags) {
        this.description = description;
        this.since = since;
        this.category = category;
        Collections.addAll(this.tags, tags);
    }

    /**
     * @param description A <b>short</b> description of the module. Best practice is to describe the functionality of the module in one sentence.
     * @param category
     * @param displayItem
     */
    public VEModule(@Nullable String description, @NotNull ModuleCategory category, ModuleTag... tags) {
        this(description, null, category, tags);
    }

    public VEModule(@NotNull ModuleCategory category, ModuleTag... tags) {
        this(null, category, tags);
    }

    @NotNull
    public String getName() {
        return ModuleUtils.getNameFromKey(getModuleKey());
    }

    @NotNull
    public NamespacedKey getModuleKey() {
        return new NamespacedKey(VanillaEnhancements.getPlugin(), getKey());
    }

    public abstract @NotNull String getKey();

    /**
     * Called before onEnable() and onDisable()
     * Used to execute code before the module is enabled/disabled
     */
    public void initialize() {

    }

    /**
     * Called when the config is reloaded.
     * ConfigOptions may have changed.
     */
    public void reload() {

    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public abstract JavaPlugin getPlugin();

    /**
     * Warning: Do not use this in {@link #getPlugin()} if your module is not part of the VanillaEnhancements plugin!
     * @return The VanillaEnhancements instance
     */
    protected VanillaEnhancements getVEInstance() {
        return VanillaEnhancements.getPlugin();
    }

    public Logger getLogger() {
        return getPlugin().getLogger();
    }

    public Config getConfig() {
        return Config.getInstance();
    }

    public boolean shouldEnable() {
        return true;
    }

    public boolean isEnabled() {
        return enabled.get();
    }

    public void toggle() {
        ModuleRegistry moduleRegistry = getVEInstance().getModuleRegistry();
        enabled.set(!enabled.get());
        if (isEnabled()) {
            moduleRegistry.disableModule(this);
        } else {
            moduleRegistry.enableModule(this);
        }
    }

    public @Nullable String getSince() {
        return since;
    }

    public @Nullable String getDescription() {
        return description;
    }

    public @NotNull List<ModuleTag> getTags() {
        return tags;
    }

    public @NotNull ModuleCategory getCategory() {
        return category;
    }

    public void loopEntities(Consumer<Entity> consumer) {
        getPlugin().getServer().getWorlds().forEach(world -> world.getEntities().forEach(entity -> {
            if (entity.isValid()) consumer.accept(entity);
        }));
    }

    public void loopPlayers(Consumer<Player> consumer) {
        getPlugin().getServer().getOnlinePlayers().forEach(consumer);
    }

    public abstract Material getDisplayItem();

    @NotNull
    public ItemCreator buildIcon() {
        return new ItemCreator(getDisplayItem(), itemCreator -> {
            itemCreator.displayName(getName());
            return null;
        });
    }

}
