package hotlinecesena.view.loader;

public enum AudioType {
    
    WALK("walk.wav"),
    
    SHOOT("shoot.wav"),
    
    SHOOT_PISTOL("shootPistol.wav"),
    
    RELOAD("reload.wav"),
    
    DEATH("death.wav"),
    
    PIUCKUP("pickUpWeapon.wav"),
    
    WEAPON_PISTOL("weaponPistol.wav"),
    
    WEAPON_RIFLE("weaponRifle.wav"),
    
    BACKGROUND("music.mp3");

    private final String path;
    
    private AudioType(String path) {
        this.path = path;
    }
    
    @Override
    public String toString() {
        return this.path;
    }    
}
