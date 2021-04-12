package hotlinecesena.model.entities.actors.enemy;

import java.util.Map;
import java.util.Random;
import java.util.Set;

import hotlinecesena.model.entities.items.WeaponImpl;
import hotlinecesena.model.entities.items.WeaponType;
import hotlinecesena.model.inventory.NaiveInventoryImpl;
import javafx.geometry.Point2D;

/**
 * Class that represent the generic factory implementation.
 */
public final class EnemyFactoryImpl implements EnemyFactory {

    @Override
    public Enemy getEnemy(final Point2D pos, final EnemyType type,
            final Set<Point2D> walkable, final Set<Point2D> walls) {

        switch (type) {
            case BOSS:
                return new EnemyImpl(pos, this.randomInventory(), this.randomRotation(), EnemyType.PATROLLING, walkable, walls);
            default:
                return new EnemyImpl(pos, this.randomInventory(), this.randomRotation(), type, walkable, walls);
        }
    }

    /**
     * Returns an inventory with a random
     * weapon within.
     * @return a new Inventory
     */
    private NaiveInventoryImpl randomInventory() {
        final int pick = new Random().nextInt(WeaponType.values().length);
        final WeaponType weapon = WeaponType.values()[pick];
        NaiveInventoryImpl retval = new NaiveInventoryImpl(new WeaponImpl(weapon),
                Map.of(weapon.getCompatibleAmmo(), 100));

        return retval;
    }
    
    private double randomRotation() {
        return new Random().nextInt(360);
    }
}
