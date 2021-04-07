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
import hotlinecesena.model.events.DeathEvent;
import hotlinecesena.model.events.MovementEvent;
import hotlinecesena.model.events.RotationEvent;
import hotlinecesena.model.events.Subscriber;
import hotlinecesena.view.entities.Sprite;
import hotlinecesena.view.loader.ImageType;
import hotlinecesena.view.loader.ProxyImage;
import hotlinecesena.view.loader.SceneType;

public class EnemyController implements Updatable, Subscriber {

    private final ProxyImage loader;
    private final Map<Enemy, Sprite> enemyMap;
    private final Player player;

    public EnemyController(final List<Enemy> enemyList, final List<Sprite> sprite, final Player player) {
        this.loader = new ProxyImage();
        this.enemyMap = new HashMap<>();
        this.player = player;
        this.initialize(enemyList, sprite);
    }

    private void initialize(final List<Enemy> enemyList, final List<Sprite> sprite) {
        if(enemyList.size() != sprite.size()) {
            throw new IllegalArgumentException("Different lists size");
        }

        enemyList.stream().forEach(e -> {
            e.register(this);
            final Sprite enemySprite = sprite.get(0);
            enemySprite.updatePosition(e.getPosition());
            enemySprite.updateRotation(e.getAngle());
            this.enemyMap.put(e, enemySprite);
            sprite.remove(0);
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
    private void updateSpriteOnMoveEvent(final MovementEvent<Enemy> e) {
        this.enemyMap.get(e.getSource()).updatePosition(e.getPosition());
    }

    @Subscribe
    private void updateSpriteOnRotationEvent(final RotationEvent<Enemy> e) {
        this.enemyMap.get(e.getSource()).updateRotation(e.getNewAngle());
    }

    @Subscribe
    private void onDeathEvent(final DeathEvent<Enemy> e) {
        this.enemyMap.get(e.getSource()).updateImage(this.loader.getImage(SceneType.GAME, ImageType.ENEMY_1));
        e.getSource().unregister(this);
    }
}
