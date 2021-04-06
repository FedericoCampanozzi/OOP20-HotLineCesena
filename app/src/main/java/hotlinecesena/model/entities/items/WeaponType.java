package hotlinecesena.model.entities.items;

public enum WeaponType{
	SHOTGUN(25, 7, AmmunitionType.SHOTGUN_AMMO, 1, 7, 8, 5),
	RIFLE(20, 5, AmmunitionType.RIFLE_AMMO, 1, 30, 5, 4),
	PISTOL(5, 5, AmmunitionType.RIFLE_AMMO, 1, 10, 3, 3);
	
	private double damage;
	private double projectileSpeed;
	private AmmunitionType compatibleAmmo;
	private int maxStacks;
	private int magazineSize;
	private double noise;
	private double reloadTime;
	
	WeaponType(final double damage, final double projectileSpeed, final AmmunitionType compatibleAmmo, final int maxStacks, final int magazineSize, final double noise, final double reloadTime) {
		this.damage = damage;
		this.projectileSpeed = projectileSpeed;
		this.compatibleAmmo = compatibleAmmo;
		this.maxStacks = maxStacks;
		this.magazineSize = magazineSize;
		this.noise = noise;
		this.reloadTime = reloadTime;
	}
	
	public double getDamage() {
		return this.damage;
	}
	
	public double getProjectileSpeed() {
		return this.projectileSpeed;
	}
	
	public AmmunitionType getCompatibleAmmo() {
		return this.compatibleAmmo;
	}
	
	public int getMaxStacks() {
		return this.maxStacks;
	}
	
	public int getMagazineSize() {
		return this.magazineSize;
	}
	
	public double getNoise() {
		return this.noise;
	}
	
	public double getReloadTime() {
		return this.reloadTime;
	}
}
