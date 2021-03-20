package hotlinecesena.model.entities.items;

import hotlinecesena.model.items.Ammunition;

public interface Firearm extends Weapon {
    
    void reload(int bullets);

    Ammunition getCompatibleAmmo();

    int getMagazineSize();
    
    int getCurrentAmmo();
}
