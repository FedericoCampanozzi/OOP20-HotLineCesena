package hotlinecesena.view.loader;

/**
 * Collections of all the possible images that can be displayed on the screen
 */
public enum ImageType {

    PLAYER("player.png"),

    PLAYER_PISTOL("playerPistol.png"),

    PLAYER_RIFLE("playerRifle.png"),

    ENEMY_1("enemy1.png"),

    ENEMY_2("enemy2.png"),

    ENEMY_3("enemy3.png"),

    BULLET("shoot.png"),

    MEDKIT("medkit.png"),

    WALL("wall.png"),

    FLOOR("floor.png"),

    BOX("box.png"),

    GRASS("grass.png"),

    TABLE("table.png"),

    CHAIR("chair.png"),

    KITCHEN("kitchen.png"),

    OVEN("oven.png"),

    AMMO_PISTOL("ammoPistol.png"),

    AMMO_RIFLE("ammoRifle.png"),

    PISTOL("pistol.png"),

    RIFLE("rifle.png"),

    ICON("icon.png"),

    TITLE("title.png"),
    
    MENU("menu.png"),
    
    CREDITS("startingScreen.png"),
    ;

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
