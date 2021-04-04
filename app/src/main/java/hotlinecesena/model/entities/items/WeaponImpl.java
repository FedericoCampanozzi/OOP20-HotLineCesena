package hotlinecesena.model.entities.items;

import java.util.Optional;
import java.util.function.Consumer;

import hotlinecesena.model.entities.actors.Actor;

public class WeaponImpl implements Weapon{
	
	// Player starts with this specific weapon
	private WeaponType weapon = WeaponType.PISTOL;
	private int currentAmmo = weapon.getMagazineSize();

	@Override
	public Optional<Consumer<Actor>> usage() {
		return Optional.empty();
	}

	@Override
	public int getMaxStacks() {
		return weapon.getMaxStacks();
	}

	@Override
	public void reload(int bullets) {
		currentAmmo = currentAmmo + bullets;
		if (currentAmmo > weapon.getMagazineSize()) {
			currentAmmo = weapon.getMagazineSize();
		}
	}

	@Override
	public Item getCompatibleAmmunition() {
		return weapon.getCompatibleAmmo();
	}

	@Override
	public double getReloadTime() {
		return weapon.getReloadTime();
	}

	@Override
	public double getNoise() {
		return weapon.getNoise();
	}

	@Override
	public int getMagazineSize() {
		return weapon.getMagazineSize();
	}

	@Override
	public int getCurrentAmmo() {
		return this.currentAmmo;
	}

}
