package hotlinecesena.view.loader;

import hotlinecesena.model.entities.actors.Actor;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.entities.items.Weapon;

/**
 * Collections of all the possible sounds that can be reproduced
 */
public enum AudioType {

    /**
     * Relative path to the walk audio file.
     * To be reproduce every time an actor moves
     * @see Actor
     */
    WALK("walk.wav"),

    /**
     * Relative path to the shoot audio file
     * To be reproduce every time an actor shoots
     * with a rifle
     * @see Actor
     */
    SHOOT("shoot.wav"),

    /**
     * Relative path to the pistol shoot audio file
     * To be reproduce every time an actor shoots
     * with a pistol
     * @see Actor
     */
    SHOOT_PISTOL("shootPistol.wav"),

    /**
     * Relative path to the reload audio file
     * To be reproduce every time an actor reload
     * its weapon
     * @see Actor
     * @see Weapon
     */
    RELOAD("reload.wav"),

    /**
     * Relative path to the death audio file
     * To be reproduce every time an actor dies
     * @see Actor
     */
    DEATH("death.wav"),

    /**
     * Relative path to the pickup audio file
     * To be reproduce every time the player picks
     * up a weapon
     * @see Player
     * @see Weapon
     */
    PIUCKUP("pickUpWeapon.wav"),

    /**
     * Relative path to the background music for the game
     */
    BACKGROUND("music.mp3");

    private final String path;

    /**
     * @param path the relative path to each audio file
     * @see String 
     */
    private AudioType(final String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return this.path;
    }
}
