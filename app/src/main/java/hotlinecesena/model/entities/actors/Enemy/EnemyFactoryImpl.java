package hotlinecesena.model.entities.actors.enemy;

import javafx.geometry.Point2D;

public class EnemyFactoryImpl implements EnemyFactory {

    @Override
    public Enemy getEnemy(Point2D pos, EnemyType type) {
        Enemy retval;
        
        switch(type) {
            case BOSS:
                retval = null;
                break;
            default:
                retval = new EnemyImpl(pos, null, type);
                break;
        }

        return retval;
    }

}
