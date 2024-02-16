package yt.sehrschlecht.vanillaenhancements.utils;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Sound constants that are used by the VE GUI.
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public enum VESound {
    CONFIG_RESET(Sound.BLOCK_NOTE_BLOCK_BANJO, 1, 1),
    CONFIG_ENABLE(Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2),
    CONFIG_DISABLE(Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0),
    CONFIG_PROMPT_ASK(Sound.BLOCK_NOTE_BLOCK_BELL, 2, 0),
    CONFIG_PROMPT_ACCEPT(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1),
    CONFIG_PROMPT_CANCEL(Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1),

    GIVE_ITEM(Sound.ENTITY_ITEM_PICKUP, 1, 1),
    SUCCESS(Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

    private final Sound sound;
    private final float volume;
    private final float pitch;

    VESound(Sound sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public void play(Player player, float volume, float pitch) {
        player.playSound(player.getLocation(), this.sound, volume, pitch);
    }

    public void play(Player player) {
        this.play(player, this.volume, this.pitch);
    }

}
