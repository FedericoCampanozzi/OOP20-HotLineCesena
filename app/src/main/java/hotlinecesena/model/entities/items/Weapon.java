package hotlinecesena.model.entities.items;

/**
 * A kind of item, generates projectiles.
 */
public interface Weapon extends Item {

	/**
	 * Recharge the magazine.
	 * @param bullets
	 */
    void reload(int bullets);

    /**
     * @return the ammunition corresponding to the weapon type.
     */
    CollectibleType getCompatibleAmmunition();

    /**
     * @return the reload time of the weapon.
     */
    double getReloadTime();

    /**
     * @return the noise of the weapon.
     */
    double getNoise();

    /**
     * @return the size of the magazine of the weapon.
     */
    int getMagazineSize();

    /**
     * @return the current amount of ammunitions in the magazine.
     */
    int getCurrentAmmo();

    /**
     * @return the type of the weapon.
     */
    WeaponType getWeaponType();

    /**
     * @return the time to wait between two shots.
     */
    double getRateOfFire();
}