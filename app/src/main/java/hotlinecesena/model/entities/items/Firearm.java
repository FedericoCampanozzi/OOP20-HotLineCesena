package hotlinecesena.model.entities.items;

public interface Firearm extends Weapon {
    
    void reload(int bullets);

    Ammunition getCompatibleAmmo();

    int getMagazineSize();
    
    int getCurrentAmmo();
}
