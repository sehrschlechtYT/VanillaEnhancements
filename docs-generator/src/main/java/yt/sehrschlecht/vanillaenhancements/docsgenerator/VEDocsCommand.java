package yt.sehrschlecht.vanillaenhancements.docsgenerator;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.schlechteutils.commands.CommandUtils;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.modules.VEModule;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class VEDocsCommand implements CommandExecutor, TabExecutor {

    private final VEDocsGeneratorPlugin plugin;

    public VEDocsCommand(VEDocsGeneratorPlugin plugin) {
        this.plugin = plugin;
    }

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
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("generate")) {
                sender.sendMessage("§aGenerating docs...");
                try {
                    generateDocs();
                    sender.sendMessage("§aSuccessfully generated docs!");
                    sender.sendMessage("§aOutput: /plugins/VanillaEnhancements/docs");
                } catch (IOException e) {
                    sender.sendMessage("§cFailed to generate docs!");
                    throw new RuntimeException(e);
                }
                return true;
            }
        }
        sender.sendMessage("§cCommand usage: /ve-docs <generate>");
        return true;
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
        if (args.length != 1) return Collections.emptyList();
        return CommandUtils.tabComplete(
                List.of("generate"),
                args[0]
        );
    }

    public void generateDocs() throws IOException {
        List<VEModule> modules = VanillaEnhancements.getPlugin().getModuleRegistry().getRegisteredModules();
        logMessage("Generating documentation for " + modules.size() + " modules...");
        new VEDocsGenerator(modules).generate();
        logMessage("Successfully generated documentation! Output: /plugins/VanillaEnhancements/docs");
    }

    private void logMessage(String message) {
        plugin.getLogger().log(Level.INFO, message);
    }

}
