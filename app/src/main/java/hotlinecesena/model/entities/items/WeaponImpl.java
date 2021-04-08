package hotlinecesena.model.entities.items;

import java.util.function.Consumer;

import hotlinecesena.model.entities.actors.Actor;

public class WeaponImpl implements Weapon {
	
	private int currentAmmo;
	private WeaponType weaponType;
	private double lastTime = System.currentTimeMillis();
	
	public WeaponImpl(WeaponType weaponType) {
		this.weaponType = weaponType;
		this.currentAmmo = weaponType.getMagazineSize();
	}

	@Override
    public Consumer<Actor> usage() {
        return actor -> {
            final double currentTime = System.currentTimeMillis();
            if (currentAmmo > 0 && (currentTime - lastTime > this.getRateOfFire())) {
                weaponType.usage().accept(actor);
                currentAmmo -= 1;
            }
            lastTime = currentTime;
        };
    }

    public double getRateOfFire() {
        return weaponType.getRateOfFire();
    }

	@Override
	public int getMaxStacks() {
		return weaponType.getMaxStacks();
	}

	@Override
	public void reload(int bullets) {
		if (currentAmmo + bullets > weaponType.getMagazineSize()) {
			throw new IllegalArgumentException();
		}
		currentAmmo = currentAmmo + bullets;
	}

	@Override
	public Item getCompatibleAmmunition() {
		return weaponType.getCompatibleAmmo();
	}

	@Override
	public double getReloadTime() {
		return weaponType.getReloadTime();
	}

	@Override
	public double getNoise() {
		return weaponType.getNoise();
	}

	@Override
	public int getMagazineSize() {
		return weaponType.getMagazineSize();
	}

	@Override
	public int getCurrentAmmo() {
		return this.currentAmmo;
	}

	@Override
	public WeaponType getWeaponType() {
		return this.weaponType;
	}

}
