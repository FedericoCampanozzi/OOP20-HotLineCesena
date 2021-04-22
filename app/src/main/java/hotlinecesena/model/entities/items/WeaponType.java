package hotlinecesena.model.entities.items;

import hotlinecesena.model.entities.items.attack.AttackStrategy;
import hotlinecesena.model.entities.items.attack.SingleStraightShot;
import hotlinecesena.model.entities.items.attack.SpreadShot;

/**
 * Collection of different types of weapon.
 */
public enum WeaponType {

	/**
	 * Shotgun type. Shoots SpreadShots. High damage. Low rate of fire.
	 */
    SHOTGUN(50.0, 7.0, CollectibleType.SHOTGUN_AMMO, 1, 7, 80.0, 4.0, 1250.0, new SpreadShot()),

    /**
     * Rifle type. Shoots SingleStraightShot. High damage. High rate of fire.
     */
    RIFLE(20.0, 9.0, CollectibleType.RIFLE_AMMO, 1, 30, 50.0, 3.5, 100.0, new SingleStraightShot()),

    /**
     * Pistol type. Shoots SingleStraightShot. Low damage. Medium rate of fire.
     */
    PISTOL(15.0, 7.0, CollectibleType.PISTOL_AMMO, 1, 10, 30.0, 2.5, 500.0, new SingleStraightShot());

    private double damage;
    private double projectileSpeed;
    private CollectibleType compatibleAmmo;
    private int maxStacks;
    private int magazineSize;
    private double noise;
    private double reloadTime;
    private double rateOfFire;
    private AttackStrategy strategy;

    /**
     * Class constructor.
     * @param damage
     * @param projectileSpeed
     * @param compatibleAmmo
     * @param maxStacks
     * @param magazineSize
     * @param noise
     * @param reloadTime
     * @param rateOfFire
     * @param strategy
     */
    WeaponType(final double damage, final double projectileSpeed,
            final CollectibleType compatibleAmmo, final int maxStacks, final int magazineSize,
            final double noise, final double reloadTime, final double rateOfFire, final AttackStrategy strategy) {
        this.damage = damage;
        this.projectileSpeed = projectileSpeed;
        this.compatibleAmmo = compatibleAmmo;
        this.maxStacks = maxStacks;
        this.magazineSize = magazineSize;
        this.noise = noise;
        this.reloadTime = reloadTime;
        this.rateOfFire = rateOfFire;
        this.strategy = strategy;
    }

    /**
     * @return the type of attack (Spread or SingleStraight).
     */
    AttackStrategy getStrategy() {
        return strategy;
    }

    /**
     * @return the damage.
     */
    public double getDamage() {
        return damage;
    }

    /**
     * @return the speed of the projectile.
     */
    public double getProjectileSpeed() {
        return projectileSpeed;
    }

    /**
     * @return the type of projectile compatible with the weapon.
     */
    public CollectibleType getCompatibleAmmo() {
        return compatibleAmmo;
    }

    /**
     * @return the max amount of weapons in the inventory (1 for weapons).
     */
    public int getMaxStacks() {
        return maxStacks;
    }

    /**
     * @return the size of the magazine.
     */
    public int getMagazineSize() {
        return magazineSize;
    }

    /**
     * @return the noise of the weapon.
     */
    public double getNoise() {
        return noise;
    }

    /**
     * @return the time to wait for reloading.
     */
    public double getReloadTime() {
        return reloadTime;
    }

    /**
     * @return the rate of fire of the weapon.
     */
    public double getRateOfFire() {
        return rateOfFire;
    }
}