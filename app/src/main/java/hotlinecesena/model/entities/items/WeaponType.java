package hotlinecesena.model.entities.items;

import java.util.function.Consumer;

import hotlinecesena.model.entities.actors.Actor;

public enum WeaponType {
	SHOTGUN(actor -> actor.attack(), 25, 7, AmmunitionType.SHOTGUN_AMMO, 1, 7, 8, 5),
	RIFLE(actor -> actor.attack(), 20, 5, AmmunitionType.RIFLE_AMMO, 1, 30, 5, 4),
	PISTOL(actor -> actor.attack(), 5, 5, AmmunitionType.RIFLE_AMMO, 1, 10, 3, 3);
	
	private Consumer<Actor> attackFunc;
	private double damage;
	private double projectileSpeed;
	private AmmunitionType compatibleAmmo;
	private int maxStacks;
	private int magazineSize;
	private int noise;
	private int reloadTime;
	
	WeaponType(Consumer<Actor> attackFunc, final double damage, final double projectileSpeed, final AmmunitionType compatibleAmmo, final int maxStacks, final int magazineSize, final int noise, final int reloadTime) {
		this.attackFunc = attackFunc;
		this.damage = damage;
		this.projectileSpeed = projectileSpeed;
		this.compatibleAmmo = compatibleAmmo;
		this.maxStacks = maxStacks;
		this.magazineSize = magazineSize;
		this.noise = noise;
		this.reloadTime = reloadTime;
	}
	
	public Consumer<Actor> getAttackFunc() {
		return this.attackFunc;
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
	
	public int getNoise() {
		return this.noise;
	}
	
	public int getReloadTime() {
		return this.reloadTime;
	}
}
