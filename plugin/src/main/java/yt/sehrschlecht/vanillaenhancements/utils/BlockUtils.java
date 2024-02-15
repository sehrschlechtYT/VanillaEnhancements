package yt.sehrschlecht.vanillaenhancements.utils;

import org.bukkit.block.Block;

import java.util.Locale;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class BlockUtils {
    public static boolean checkName(Block block, String name) {
        return block.getType().name().contains(name.toUpperCase(Locale.ROOT));
    }
}
