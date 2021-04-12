package hotlinecesena.model.entities.items;

import java.util.function.Consumer;

import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.model.entities.actors.Actor;
import javafx.geometry.Point2D;

public class WeaponImpl implements Weapon {

    private static final double PROJECTILE_SIZE = 0.1;
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
                final Projectile proj = new Projectile(
                        this.computeStartingPoint(actor),
                        actor.getAngle(),
                        PROJECTILE_SIZE,
                        PROJECTILE_SIZE,
                        weaponType.getProjectileSpeed(),
                        weaponType.getDamage());
                currentAmmo -= 1;
                lastTime = currentTime;
                JSONDataAccessLayer.getInstance().getBullets().getProjectile().add(proj);
            }
        };
    }

    private Point2D computeStartingPoint(final Actor actor) {
        final double centerX = actor.getPosition().getX() + actor.getWidth() / 2;
        final double centerY = actor.getPosition().getY() + actor.getHeight() / 2;
        return new Point2D(centerX + actor.getWidth() * Math.cos(Math.toRadians(actor.getAngle())),
                centerY + actor.getHeight() * Math.sin(Math.toRadians(actor.getAngle())));
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
        return currentAmmo;
    }

    @Override
    public WeaponType getWeaponType() {
        return weaponType;
    }
}