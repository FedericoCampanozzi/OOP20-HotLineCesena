package hotlinecesena.model.entities.items;

import java.util.function.Consumer;

import hotlinecesena.model.entities.actors.Actor;

public class WeaponImpl implements Weapon {

    private static final double PROJECTILE_SIZE = 0.2;
    private int currentAmmo;
    private final WeaponType weaponType;
    private double lastTime = System.currentTimeMillis();

    public WeaponImpl(final WeaponType weaponType) {
        this.weaponType = weaponType;
        currentAmmo = weaponType.getMagazineSize();
    }

    @Override
    public Consumer<Actor> usage() {
        return actor -> {
            final double currentTime = System.currentTimeMillis();
            if (currentAmmo > 0 && currentTime - lastTime >= this.getRateOfFire()) {
                weaponType.getStrategy().shoot(
                        weaponType, actor.getPosition(), actor.getAngle(), actor.getWidth(),
                        actor.getHeight(), PROJECTILE_SIZE);
                currentAmmo -= 1;
                lastTime = currentTime;
            }
        };
    }

    @Override
    public double getRateOfFire() {
        return weaponType.getRateOfFire();
    }

    @Override
    public int getMaxStacks() {
        return weaponType.getMaxStacks();
    }

    @Override
    public void reload(final int bullets) {
        if (currentAmmo + bullets > weaponType.getMagazineSize()) {
            throw new IllegalArgumentException();
        }
        currentAmmo = currentAmmo + bullets;
    }

    @Override
    public CollectibleType getCompatibleAmmunition() {
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
        return currentAmmo;
    }

    @Override
    public WeaponType getWeaponType() {
        return weaponType;
    }
}