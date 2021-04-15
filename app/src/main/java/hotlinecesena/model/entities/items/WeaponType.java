package hotlinecesena.model.entities.items;

import hotlinecesena.model.entities.items.attack.AttackStrategy;
import hotlinecesena.model.entities.items.attack.SingleStraightShot;
import hotlinecesena.model.entities.items.attack.SpreadShot;

public enum WeaponType {

    SHOTGUN(50.0, 7.0, CollectibleType.SHOTGUN_AMMO, 1, 7, 80.0, 4.0, 1250.0, new SpreadShot()),

    RIFLE(20.0, 9.0, CollectibleType.RIFLE_AMMO, 1, 30, 50.0, 3.5, 100.0, new SingleStraightShot()),

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

    AttackStrategy getStrategy() {
        return strategy;
    }

    public double getDamage() {
        return damage;
    }

    public double getProjectileSpeed() {
        return projectileSpeed;
    }

    public CollectibleType getCompatibleAmmo() {
        return compatibleAmmo;
    }

    public int getMaxStacks() {
        return maxStacks;
    }

    public int getMagazineSize() {
        return magazineSize;
    }

    public double getNoise() {
        return noise;
    }

    public double getReloadTime() {
        return reloadTime;
    }

    public double getRateOfFire() {
        return rateOfFire;
    }
}