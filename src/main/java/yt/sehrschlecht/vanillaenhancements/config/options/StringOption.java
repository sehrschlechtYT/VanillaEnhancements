package yt.sehrschlecht.vanillaenhancements.config.options;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.Sound;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yt.sehrschlecht.vanillaenhancements.VanillaEnhancements;
import yt.sehrschlecht.vanillaenhancements.config.Config;
import yt.sehrschlecht.vanillaenhancements.config.ConfigOption;
import yt.sehrschlecht.vanillaenhancements.utils.ItemCreator;

import java.util.function.BiConsumer;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class StringOption extends ConfigOption<String> {
    private final boolean allowEmpty;

    /**
     * @param defaultValue The default value of the option.
     * @param description  A markdown formatted description of the option.
     * @param allowEmpty
     */
    public StringOption(String defaultValue, @Nullable String description, boolean allowEmpty) {
        super(defaultValue, description);
        this.allowEmpty = allowEmpty;
    }

    public StringOption(String defaultValue, String description) {
        this(defaultValue, description, false);
    }

    /**
     * @param defaultValue  The default value of the option.
     * @param description   A markdown formatted description of the option.
     * @param updateHandler A consumer that takes the old and the new value of the option after an update (e.g. through the UI)
     */
    public StringOption(String defaultValue, @Nullable String description, @Nullable BiConsumer<String, String> updateHandler, boolean allowEmpty) {
        super(defaultValue, description, updateHandler);
        this.allowEmpty = allowEmpty;
    }

    /**
     * @param defaultValue  The default value of the option.
     * @param description   A markdown formatted description of the option.
     * @param updateHandler A consumer that takes the old and the new value of the option after an update (e.g. through the UI)
     */
    public StringOption(String defaultValue, @Nullable String description, @Nullable BiConsumer<String, String> updateHandler) {
        this(defaultValue, description, updateHandler, false);
    }

    @Override
    public String getFromConfig() {
        return Config.getInstance().getDocument().getString(toPath());
    }

    @Override
    public String getPossibleValues() {
        return "Any string";
    }

    @Override
    public String valueToDisplayString(String value) {
        return "\"" + value + "\"";
    }

    /**
     * @param value The value to check
     * @return Null if the value is valid, otherwise the error message
     */
    @Override
    public @Nullable String validate(String value) {
        if (!allowEmpty && value.isEmpty()) return "The value can't be empty";
        return null;
    }

    @Override
    public ClickableItem buildClickableItem(ItemCreator creator, SmartInventory origin) {
        // todo find a solution for inputting colored messages
        creator.addLore("§9§oLeft click §r§9to change the value");
        creator.addLore("§9Use §oshift + right click §r§9to reset the value.");
        return ClickableItem.of(creator.build(), event -> {
            Player player = (Player) event.getWhoClicked();

            if (event.getClick().equals(ClickType.SHIFT_RIGHT)) {
                reset();
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1, 1);
                return;
            }

            ConversationFactory conversationFactory = new ConversationFactory(VanillaEnhancements.getPlugin());
            Prompt input = new ValidatingPrompt() {
                @NotNull
                @Override
                public String getPromptText(@NotNull ConversationContext context) {
                    return "§lPlease input the new option value in the chat. Type §r§ocancel §r§f§lto cancel the process. §r§oThis prompt will time out after 30 seconds.";
                }

                /**
                 * Override this method to check the validity of the player's input.
                 *
                 * @param context Context information about the conversation.
                 * @param input   The player's raw console input.
                 * @return True or false depending on the validity of the input.
                 */
                @Override
                protected boolean isInputValid(@NotNull ConversationContext context, @NotNull String input) {
                    return allowEmpty || !input.isBlank();
                }

                /**
                 * Override this method to accept and processes the validated input from
                 * the user. Using the input, the next Prompt in the prompt graph should
                 * be returned.
                 *
                 * @param context Context information about the conversation.
                 * @param input   The validated input text from the user.
                 * @return The next Prompt in the prompt graph.
                 */
                @Nullable
                @Override
                protected Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
                    set(input);
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    origin.open(player);
                    return null;
                }
            };
            conversationFactory.withFirstPrompt(input);
            conversationFactory.withTimeout(30);
            conversationFactory.withEscapeSequence("cancel");
            conversationFactory.addConversationAbandonedListener(abandonedEvent -> {
                origin.open(player);
                if (!abandonedEvent.gracefulExit()) {
                    player.sendMessage("§cCancelled input.");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                } else {
                    player.sendMessage("§aSaved value.");
                }
            });

            player.closeInventory();

            Conversation conversation = conversationFactory
                    .withLocalEcho(true)
                    .buildConversation(player);
            conversation.begin();

            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 2, 0);
            player.sendTitle("§lUpdate option", "Please enter a value in the chat.", 5, 100, 20);
        });
    }
}
