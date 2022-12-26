package yt.sehrschlecht.vanillaenhancements.utils.debugging;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class DebugCommand implements CommandExecutor, TabExecutor {
    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!debug().isEnabled()) {
            sender.sendMessage("§cDebugging is disabled!");
            return true;
        }
        sender.sendMessage("§7§oWarning: This command can have unexpected side effects!" +
                " Restart your server if you want to be sure that everything is working as expected.");
        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("reload")) {
                sender.sendMessage("§aReloading the config...");
                debug().reload();
                sender.sendMessage("§aSuccessfully reloaded the config!");
                return true;
            } else if(args[0].equalsIgnoreCase("generate-docs")) {
                sender.sendMessage("§aGenerating docs...");
                try {
                    debug().generateDocs();
                    sender.sendMessage("§aSuccessfully generated docs!");
                } catch (IOException e) {
                    sender.sendMessage("§cFailed to generate docs!");
                    throw new RuntimeException(e);
                }
                return true;
            }
        }
        sender.sendMessage("§cUsage: /debug <reload>");
        return true;
    }

    public Debug debug() {
        return VanillaEnhancements.getPlugin().getDebug();
    }

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender  Source of the command.  For players tab-completing a
     *                command inside of a command block, this will be the player, not
     *                the command block.
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    The arguments passed to the command, including final
     *                partial argument to be completed
     * @return A List of possible completions for the final argument, or null
     * to default to the command executor
     */
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!debug().isEnabled()) return null;
        if(args.length == 1) {
            return Stream.of("reload", "generate-docs").filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase())).toList();
        }
        return null;
    }
}
