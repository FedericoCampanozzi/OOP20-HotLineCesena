package hotlinecesena.model.entities.actors;

import hotlinecesena.model.entities.items.Item;
import hotlinecesena.model.entities.items.Weapon;

public interface Player extends Actor {

    void pickUpItem(Item item, int index);
    
    void pickUpWeapon(Weapon w, int index);
    
    void useItem(int index);
    
    void equipWeapon(int index);

    void dropItem(int index);
    
    void dropWeapon(int index);

    double getNoiseLevelByState();
}
