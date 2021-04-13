package hotlinecesena.view.loader;

/**
 * Collections of all the possible images that can be displayed on the screen.
 */
public enum ImageType {

    /**
     * Relative path to the player image file.
     * Used when the player has a nothing equipped
     */
    PLAYER("player.png"),

    /**
     * Relative path to the player with rifle image file.
     * Used when the player has a rifle equipped
     */
    PLAYER_RIFLE("playerRifle.png"),

    /**
     * Relative path to the player with pistol image file.
     * Used when the player has a pistol equipped
     */
    PLAYER_PISTOL("playerPistol.png"),

    /**
     * Relative path to the player with shotgun image file.
     * Used when the player has a shotgun equipped
     */
    PLAYER_SHOTGUN("playerShotgun.png"),

    /**
     * Relative path to the enemy image file.
     * Used as the main image to display an enemy
     */
    ENEMY_1("enemy.png"),

    /**
     * Relative path to the tombstone image file.
     * Used as the image to display when an Actor
     * dies
     * @see Actor
     */
    TOMBSTONE("tombstone.png"),

    /**
     * Relative path to the wall image file.
     * Used as environment element
     */
    WALL("wall.png"),

    /**
     * Relative path to the floor image file.
     * Used as environment element
     */
    FLOOR("floor.png"),

    /**
     * Relative path to the grass image file.
     * Used as environment element
     */
    GRASS("grass.png"),

    /**
     * Relative path to the medkit image file.
     * Used as environment element that can be
     * interact with and that will give health
     * to the player
     */
    MEDKIT("medkit.png"),

    /**
     * Relative path to the ammo box image file.
     * Used as environment element that can be
     * interact with and that will give ammo
     * to the player
     */
    AMMO_BOX("ammoBox.png"),

    /**
     * Relative path to the bullet image file.
     * Used to display the bullet shoot from a gun
     */
    BULLET("shoot.png"),

    /**
     * Relative path to the box image file.
     * Used as environment element
     */
    BOX("box.png"),

    /**
     * Relative path to the ammo for pistol image file.
     * Used in the HUD to display the amount of pistol
     * ammo that the player has
     */
    AMMO_PISTOL("ammoPistol.png"),

    /**
     * Relative path to the ammo for rifle image file.
     * Used in the HUD to display the amount of rifle
     * ammo that the player has
     */
    AMMO_RIFLE("ammoRifle.png"),

    /**
     * Relative path to the pistol image file.
     * Used in the HUD to display the weapon the
     * player is currently holding
     */
    PISTOL("pistol.png"),

    /**
     * Relative path to the rifle image file.
     * Used in the HUD to display the weapon the
     * player is currently holding
     */
    RIFLE("rifle.png"),

    /**
     * Relative path to the shotgun image file.
     * Used in the HUD to display the weapon the
     * player is currently holding
     */
    SHOTGUN("shotgun.png"),

    /**
     * Relative path to the scope image file.
     * Used to replace the cursor icon
     */
    SCOPE("scope.png"),

    /**
     * Relative path to the player image file.
     * Used as the icon for the application
     */
    ICON("icon.png"),

    /**
     * Relative path to the menu image file.
     * Used as background image
     */
    MENU("menu.png"),

    /**
     * Relative path to the starting screen image file.
     * Used as background image
     */
    STARTING_SCREEN("startingScreen.png"),

    /**
     * Relative path to the victory image file.
     * Used if the player wins
     */
    VICTORY("victory.png"),

    /**
     * Relative path to the you are dead image file.
     * Used if the player dies
     */
    YOU_DIED("youAreDead.png"),

    /**
     * Relative path to the blank image file.
     * Used to hide objects or enemies once they despawn
     */
    BLANK("blank.png");

    private final String path;

    /**
     * @param path the relative path to each image file
     * @see String 
     */
    ImageType(final String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return this.path;
    }
}
