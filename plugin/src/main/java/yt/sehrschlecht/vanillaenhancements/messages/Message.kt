package yt.sehrschlecht.vanillaenhancements.messages

import yt.sehrschlecht.vanillaenhancements.utils.SpigotExtensions.Companion.removeNewlines

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
enum class Message(
    val key: String,
    val defaultValue: String?,
) {
    PREFIX("prefix", "<gray>[<red>VE</red>] "), // todo append the prefix automatically in a method

    COMMAND_DISABLED("commands.disabled", "<red>This command is disabled!"),
    COMMAND_NOT_A_PLAYER("commands.not_a_player", "<red>You must be a player to execute this command!"),

    COMMAND_KNOCKBACK_USAGE("command.knockback.usage", "<red>Usage: /knockback \\<percentage>"),
    COMMAND_KNOCKBACK_INVALID_INPUT("command.knockback.invalid_input", "<red>The percentage must be between {} and {}!"),
    COMMAND_KNOCKBACK_SUCCESS("command.knockback.success", "Set the knockback multiplier to {}%!"),

    COMMAND_VE_MODULE_TAG_NOT_FOUND("command.ve.module_tag.not_found", "<red>The given module tag was not found!"),
    COMMAND_VE_MODULE_TAG_MALFORMATTED("command.ve.module_tag.malformatted", "<red>The given module key is not formatted correctly!"),
    COMMAND_VE_MODULE_NOT_FOUND("command.ve.module.not_found", "<red>Could not find a module for the given module key!"),
    COMMAND_VE_USAGE("command.ve.usage", """
        <red>Command usage: /ve
        <hover:show_text:'Use this parameter to open a list of all modules that have the given tag. <i>(Optional)</i>'>[module tag]</hover>
        <hover:show_text:'Use this parameter to open the menu for a specific module. <i>(Optional)</i>'>[module key]</hover>
    """.trimIndent().removeNewlines()),

    MENU_MAIN_MANAGE_MODULES_DISPLAYNAME("menu.main.manage_modules.displayname", "<white><b>Manage Modules"),
    MENU_MAIN_MANAGE_MODULES_LORE("menu.main.manage_modules.lore", "Control the settings of the many modules in VanillaEnhancements."),
    MENU_MAIN_SETTINGS_DISPLAYNAME("menu.main.settings.displayname", "<red><b>VE Settings"),
    MENU_MAIN_SETTINGS_LORE("menu.main.settings.lore", "Manage various settings that affect the plugin and its modules."),

    MENU_SETTINGS_DISABLE_ALL_DISPLAYNAME("menu.settings.disable_all.displayname", "<red><b>Disable all modules"),
    MENU_SETTINGS_DISABLE_ALL_LORE("menu.settings.disable_all.lore", "Disables every single module that is currently active. This cannot be undone."),

    MENU_TAGS_TAG_ENABLED("menu.tags.tag_enabled", "{}/{} enabled"),
    MENU_TAGS_NONE_DISPLAYNAME("menu.tags.none.displayname", "<red><b>No modules found"),
    MENU_TAGS_NONE_LORE("menu.tags.none.lore", "This should not be the case. Please report this bug to the discord server (see SpigotMC page of the VanillaEnhancements plugin)!")

    ;
}