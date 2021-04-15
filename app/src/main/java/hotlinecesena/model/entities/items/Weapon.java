package hotlinecesena.model.entities.items;

public interface Weapon extends Item {

    void reload(int bullets);

    CollectibleType getCompatibleAmmunition();

    double getReloadTime();

    double getNoise();

    int getMagazineSize();

    int getCurrentAmmo();

    WeaponType getWeaponType();

    double getRateOfFire();
}