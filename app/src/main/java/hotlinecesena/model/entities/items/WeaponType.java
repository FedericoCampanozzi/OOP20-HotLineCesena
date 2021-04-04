package hotlinecesena.model.entities.items;

import java.util.function.Consumer;

import hotlinecesena.model.entities.actors.Actor;

public enum WeaponType implements Weapon{
	SHOTGUN(actor -> actor.attack(), 25, 7, AmmunitionType.SHOTGUN_AMMO, 1, 7, 8, 5),
	RIFLE(actor -> actor.attack(), 20, 5, AmmunitionType.RIFLE_AMMO, 1, 30, 5, 4),
	PISTOL(actor -> actor.attack(), 5, 5, AmmunitionType.RIFLE_AMMO, 1, 10, 3, 3);
	
	private Consumer<Actor> attackFunc;
	private double damage;
	private double projectileSpeed;
	private AmmunitionType compatibleAmmo;
	private int maxStacks;
	private int magazineSize;
	private double noise;
	private double reloadTime;
	private int currentAmmo;
	
	WeaponType(Consumer<Actor> attackFunc, final double damage, final double projectileSpeed, final AmmunitionType compatibleAmmo, final int maxStacks, final int magazineSize, final double noise, final double reloadTime) {
		this.attackFunc = attackFunc;
		this.damage = damage;
		this.projectileSpeed = projectileSpeed;
		this.compatibleAmmo = compatibleAmmo;
		this.maxStacks = maxStacks;
		this.magazineSize = magazineSize;
		this.noise = noise;
		this.reloadTime = reloadTime;
		this.currentAmmo = magazineSize;
	}
	
	public double getDamage() {
		return this.damage;
	}
	
	public double getProjectileSpeed() {
		return this.projectileSpeed;
	}
	
	@Override
	public int getMaxStacks() {
		return this.maxStacks;
	}
	
	@Override
	public int getMagazineSize() {
		return this.magazineSize;
	}
	
	@Override
	public double getNoise() {
		return this.noise;
	}
	
	@Override
	public double getReloadTime() {
		return this.reloadTime;
	}

	@Override
	public Consumer<Actor> usage() {
		return this.attackFunc;
	}

	@Override
	public void reload(int bullets) {
		currentAmmo = currentAmmo + bullets;
		if (currentAmmo > getMagazineSize()) {
			currentAmmo = getMagazineSize();
		}
	}

	@Override
	public Item getCompatibleAmmunition() {
		return this.compatibleAmmo;
	}

	@Override
	public int getCurrentAmmo() {
		return this.currentAmmo;
	}
}
