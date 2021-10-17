package yt.sehrschlecht.vanillaenhancements.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.entity.Player;

@CommandPermission("ve.command")
@CommandAlias("ve|vanillaenhancements")
public class VECommand extends BaseCommand {

    @Default
    @CommandPermission("ve.command.help")
    public void help(Player player, String[] args) {

    }

    @Subcommand("enable")
    @CommandCompletion("@modules")
    @CommandPermission("ve.command.enable")
    public void enable(Player player, String[] args) {

    }

    @Subcommand("disable")
    @CommandCompletion("@modules")
    @CommandPermission("ve.command.disable")
    public void disable(Player player, String[] args) {

    }
}
