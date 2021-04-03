package hotlinecesena.view.loader;

public enum SceneType {
    
    MENU("GUI"),
    
    GAME("Game");
    
    private final String path;
    
    private SceneType(String path) {
        this.path = path;
    }    
    @Override
    public String toString() {
        return this.path;
    }

}
