package hotlinecesena.model.entities.items;

import java.util.function.Consumer;

import hotlinecesena.model.entities.actors.Actor;

public enum WeaponType {

    SHOTGUN(actor -> {
        Projectile shotgunProjectile = new Projectile(actor.getPosition(), actor.getAngle(), 1, 1, 50, 40);
    }, 25, 7, AmmunitionType.SHOTGUN_AMMO, 1, 7, 8, 5, 0.5),

    RIFLE(actor -> {
    	Projectile rifleProjectile = new Projectile(actor.getPosition(), actor.getAngle(), 1, 1, 50, 10);
    }, 20, 5, AmmunitionType.RIFLE_AMMO, 1, 30, 5, 4, 0.1),

    PISTOL(actor -> {
    	Projectile pistolProjectile = new Projectile(actor.getPosition(), actor.getAngle(), 1, 1, 50, 30);
    }, 5, 5, AmmunitionType.PISTOL_AMMO, 1, 10, 3, 3, 0.5);

    private Consumer<Actor> attackFunc;
    private double damage;
    private double projectileSpeed;
    private AmmunitionType compatibleAmmo;
    private int maxStacks;
    private int magazineSize;
    private double noise;
    private double reloadTime;
    private double rateOfFire;

    WeaponType(final Consumer<Actor> attackFunc, final double damage, final double projectileSpeed,
            final AmmunitionType compatibleAmmo, final int maxStacks, final int magazineSize,
            final double noise, final double reloadTime, final double rateOfFire) {
        this.attackFunc = attackFunc;
        this.damage = damage;
        this.projectileSpeed = projectileSpeed;
        this.compatibleAmmo = compatibleAmmo;
        this.maxStacks = maxStacks;
        this.magazineSize = magazineSize;
        this.noise = noise;
        this.reloadTime = reloadTime;
        this.rateOfFire = rateOfFire;
    }

    public Consumer<Actor> usage() {
        return attackFunc;
    }

    public double getDamage() {
        return damage;
    }

    public double getProjectileSpeed() {
        return projectileSpeed;
    }

    public AmmunitionType getCompatibleAmmo() {
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