package yt.sehrschlecht.vanillaenhancements.items.resourcepack;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class ResourcePackBuildCompletionEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final @Nullable File outputFile;

    public ResourcePackBuildCompletionEvent(@Nullable File outputFile) {
        this.outputFile = outputFile;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public @Nullable File getOutputFile() {
        return outputFile;
    }

}
