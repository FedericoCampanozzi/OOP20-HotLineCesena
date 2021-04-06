package hotlinecesena.controller.entities.enemy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.google.common.eventbus.Subscribe;

import hotlinecesena.controller.Updatable;
import hotlinecesena.model.entities.actors.ActorStatus;
import hotlinecesena.model.entities.actors.enemy.Enemy;
import hotlinecesena.model.entities.actors.player.Player;
import hotlinecesena.model.events.MovementEvent;
import hotlinecesena.model.events.RotationEvent;
import hotlinecesena.model.events.Subscriber;
import hotlinecesena.view.entities.Sprite;

public class EnemyController implements Updatable, Subscriber {

    private final Map<Enemy, Sprite> enemyMap;
    private final Player player;

    public EnemyController(final List<Enemy> enemyList, final Sprite sprite, final Player player) {
        this.enemyMap = new HashMap<>();
        this.player = player;
        this.initialize(enemyList, sprite);
    }

    private void initialize(final List<Enemy> enemyList, final Sprite sprite) {
        enemyList.stream().forEach(e -> {
            e.register(this);
            final Sprite enemySprite = sprite;
            enemySprite.updatePosition(e.getPosition());
            enemySprite.updateRotation(e.getAngle());
            this.enemyMap.put(e, enemySprite);
        });
    }

    @Override
    public Consumer<Double> getUpdateMethod() {
        return deltaTime -> {
            this.enemyMap.forEach((e, s) -> {
                // TODO to relief performance check if enemy is in range (es. cameraDimension) from the player to start beeing animated
                if(!e.getActorStatus().equals(ActorStatus.DEAD)) {
                    e.setIsInPursuit(e.getAI().isInPursuit(this.player.getPosition(), this.player.getNoiseRadius()));

                    if(e.getAI().isShooting(this.player.getPosition())) {
                        e.attack();
                    } else {
                        e.move(e.getAI().getNextMove(
                                this.player.getPosition(),
                                e.isChasingTarget(),
                                e.getWalkable()));
                    }

                    e.setAngle(e.getAI().getRotation(this.player.getPosition(), e.isChasingTarget()));
                }
            });
        };
    }

    @Subscribe
    private void updateSpriteOnMoveEvent(final MovementEvent e) {
        this.enemyMap.get((Enemy) e.getSource()).updatePosition(e.getPosition());
    }

    @Subscribe
    private void updateSpriteOnRotationEvent(final RotationEvent e) {
        this.enemyMap.get((Enemy) e.getSource()).updateRotation(e.getNewAngle());
    }
}
