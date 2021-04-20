package hotlinecesena.model.entities.items;

import java.util.function.Consumer;

import hotlinecesena.model.entities.actors.Actor;

/**
 * Implement Weapon. Describes every characteristics of the weapon.
 */
public class WeaponImpl implements Weapon {

    private static final double PROJECTILE_SIZE = 0.2;
    
    private final WeaponType weaponType;
    
    private int currentAmmo;
    private double lastTime = System.currentTimeMillis();

    public WeaponImpl(final WeaponType weaponType) {
        this.weaponType = weaponType;
        currentAmmo = weaponType.getMagazineSize();
    }

    /**
     * The shooting action of the weapon. 
     */
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

    /**
     * @return the rate of fire of the specific weapon type.
     */
    @Override
    public double getRateOfFire() {
        return weaponType.getRateOfFire();
    }

    /**
     * @return the max amount the specific weapon type (1 for weapons).
     */
    @Override
    public int getMaxStacks() {
        return weaponType.getMaxStacks();
    }

    /**
     * Reload the magazine of the specific weapon type.
     */
    @Override
    public void reload(final int bullets) {
        if (currentAmmo + bullets > weaponType.getMagazineSize()) {
            throw new IllegalArgumentException();
        }
        currentAmmo = currentAmmo + bullets;
    }

    /**
     * @return the compatible projectile type for the specific weapon type.
     */
    @Override
    public CollectibleType getCompatibleAmmunition() {
        return weaponType.getCompatibleAmmo();
    }

    /**
     * @return the time to wait to reload the specific weapon type.
     */
    @Override
    public double getReloadTime() {
        return weaponType.getReloadTime();
    }

    /**
     * @return the noise of the specific weapon type.
     */
    @Override
    public double getNoise() {
        return weaponType.getNoise();
    }

    /**
     * @return the magazine size of the specific weapon type.
     */
    @Override
    public int getMagazineSize() {
        return weaponType.getMagazineSize();
    }

    /**
     * @return the current amount of projectiles in the magazine of the specific weapon type.
     */
    @Override
    public int getCurrentAmmo() {
        return currentAmmo;
    }

    /**
     * @return the weapon type.
     */
    @Override
    public WeaponType getWeaponType() {
        return weaponType;
    }
}