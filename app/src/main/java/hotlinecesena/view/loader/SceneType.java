package hotlinecesena.view.loader;

/**
 * Collections of all the possible differentiation of paths based of
 * where the file needed is used
 */
public enum SceneType {

    /**
     * Relative path to collections of file that is used on GUI
     */
    MENU("GUI"),

    /**
     * Relative path to collections of file that is used on GUI
     */
    GAME("Game");

    private final String path;

    /**
     * @param path the relative path to each file type
     */
    private SceneType(String path) {
        this.path = path;
    } 

    @Override
    public String toString() {
        return this.path;
    }
}
