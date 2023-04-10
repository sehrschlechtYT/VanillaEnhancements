package yt.sehrschlecht.vanillaenhancements.modules;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.config.options.BooleanOption;
import yt.sehrschlecht.vanillaenhancements.utils.ModuleUtils;

import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public abstract class VEModule implements Listener {

    private final @Nullable String description;
    private final @Nullable String since;

    public final BooleanOption enabled = new BooleanOption(false, null);

    /**
     * @param description A <b>short</b> description of the module.
     * @param since The version since the module is available.
     */
    public VEModule(@Nullable String description, @Nullable String since) {
        this.description = description;
        this.since = since;
    }

    /**
     * @param description A <b>short</b> description of the module. Best practice is to describe the functionality of the module in one sentence.
     */
    public VEModule(@Nullable String description) {
        this(description, null);
    }

    public VEModule() {
        this(null, null);
    }

    @NotNull
    public String getName() {
        return ModuleUtils.getNameFromKey(getModuleKey());
    }

    @NotNull
    public NamespacedKey getModuleKey() {
        return new NamespacedKey(VanillaEnhancements.getPlugin(), getKey());
    }

    public abstract @NotNull String getKey(); //ToDo maybe use SimpleClassName as key

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

    public VanillaEnhancements getPlugin() { // ToDo this does not support other plugins - move this in a class like "InbuiltModule" and make this abstract
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

    public @Nullable String getSince() {
        return since;
    }

    public @Nullable String getDescription() {
        return description;
    }

    public void loopEntities(Consumer<Entity> consumer) {
        getPlugin().getServer().getWorlds().forEach(world -> world.getEntities().forEach(entity -> {
            if (entity.isValid()) consumer.accept(entity);
        }));
    }

    public void loopPlayers(Consumer<Player> consumer) {
        getPlugin().getServer().getOnlinePlayers().forEach(consumer);
    }

}
