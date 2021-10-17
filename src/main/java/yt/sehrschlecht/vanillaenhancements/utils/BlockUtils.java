package yt.sehrschlecht.vanillaenhancements.utils;

import org.bukkit.block.Block;

import java.util.Locale;

public class BlockUtils {
    public static boolean checkName(Block block, String name) {
        return block.getType().name().contains(name.toUpperCase(Locale.ROOT));
    }
}
