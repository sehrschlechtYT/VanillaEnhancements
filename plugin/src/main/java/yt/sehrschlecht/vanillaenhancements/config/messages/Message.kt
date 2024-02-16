package yt.sehrschlecht.vanillaenhancements.config.messages

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
enum class Message(
    val key: String,
    val defaultValue: String?,
) {
    PREFIX("prefix", "<gray>[<red>VE</red>] "), // todo append the prefix automatically in a method
    COMMAND_DISABLED("commandDisabled", "<red>This command is disabled!"),
    COMMAND_KNOCKBACK_USAGE("command.knockback.usage", "<red>Usage: /knockback \\<percentage>"),
    COMMAND_KNOCKBACK_INVALID_INPUT("command.knockback.invalid_input", "<red>The percentage must be between {} and {}!"),
    COMMAND_KNOCKBACK_SUCCESS("command.knockback.success", "Set the knockback multiplier to {}%!");
}