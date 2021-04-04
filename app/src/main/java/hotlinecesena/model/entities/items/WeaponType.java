package hotlinecesena.model.entities.items;

import java.util.Optional;
import java.util.function.Consumer;

import hotlinecesena.model.entities.actors.Actor;

public enum WeaponType {
	RIFLE(actor -> {}, 20, 5, 1, 30);
	
	private Consumer<Actor> attackFunc;
	private double damage;
	private double projectileSpeed;
	// private AmmunitionType compatibleAmmo;
	private int maxStacks;
	private int magazineSize;
	private int currentAmmo;
	private int noise;
	private int reloadTime;
	
	WeaponType(Consumer<Actor> attackFunc, final double damage, final double projectileSpeed, final int maxStacks, final int magazineSize) {
		this.attackFunc = attackFunc;
		this.damage = damage;
		this.projectileSpeed = projectileSpeed;
		this.maxStacks = maxStacks;
		this.magazineSize = magazineSize;
		this.currentAmmo = magazineSize;
	}
	
	public Optional<Item> getCompatibleAmmunition() {
		return null;
	}
	
	public double getReloadTime() {
		return this.reloadTime;
	}
	
	public double getNoise() {
		return this.noise;
	}
	
	public int getMagazineSize() {
		return this.magazineSize;
	}
	
	public int getCurrentAmmo() {
		return this.currentAmmo;
	}
}
