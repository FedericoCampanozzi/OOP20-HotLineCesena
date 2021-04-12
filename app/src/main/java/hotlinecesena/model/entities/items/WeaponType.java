package hotlinecesena.model.entities.items;

public enum WeaponType {

    SHOTGUN(50, 6, AmmunitionType.SHOTGUN_AMMO, 1, 7, 8, 5, 1000.0),

    RIFLE(25, 8, AmmunitionType.RIFLE_AMMO, 1, 30, 5, 4, 100.0),

    PISTOL(15, 6, AmmunitionType.PISTOL_AMMO, 1, 10, 3, 3, 500.0);

    private double damage;
    private double projectileSpeed;
    private AmmunitionType compatibleAmmo;
    private int maxStacks;
    private int magazineSize;
    private double noise;
    private double reloadTime;
    private double rateOfFire;

    WeaponType(final double damage, final double projectileSpeed,
            final AmmunitionType compatibleAmmo, final int maxStacks, final int magazineSize,
            final double noise, final double reloadTime, final double rateOfFire) {
        this.damage = damage;
        this.projectileSpeed = projectileSpeed;
        this.compatibleAmmo = compatibleAmmo;
        this.maxStacks = maxStacks;
        this.magazineSize = magazineSize;
        this.noise = noise;
        this.reloadTime = reloadTime;
        this.rateOfFire = rateOfFire;
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