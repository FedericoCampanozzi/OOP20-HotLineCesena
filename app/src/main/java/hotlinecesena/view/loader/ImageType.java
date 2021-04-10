package hotlinecesena.view.loader;

/**
 * Collections of all the possible images that can be displayed on the screen
 */
public enum ImageType {

    PLAYER("player.png"),

    PLAYER_DEAD("playerDead.png"),

    PLAYER_RIFLE("playerRifle.png"),

    PLAYER_PISTOL("playerRifle.png"),

    PLAYER_SHOTGUN("playerRifle.png"),

    ENEMY_1("enemy.png"),

    ENEMY_DEAD("enemyDead.png"),

    WALL("wall.png"),

    FLOOR("floor.png"),

    GRASS("grass.png"),

    MEDKIT("medkit.png"),

    BULLET("shoot.png"),

    BOX("box.png"),

    AMMO_PISTOL(""),
    
    BLANK("blank.png");

    private final String path;

    /**
     * @param path the relative path to each image file
     * @see String 
     */
    private ImageType(final String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return this.path;
    }
}
