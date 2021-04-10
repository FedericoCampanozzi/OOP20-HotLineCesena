package hotlinecesena.controller.entities.enemy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.google.common.eventbus.Subscribe;

import hotlinecesena.controller.Updatable;
import hotlinecesena.controller.physics.EnemyPhysics;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
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
import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;

/**
 * Controls the actions that each enemy
 * will perform during the game loop and
 * updates their respective image
 */
public class EnemyController implements Updatable, Subscriber {

    private final ProxyImage loader;
    private final Enemy enemy;
    private final Sprite sprite;
    private final Player player;
    private long lastTime;
    private long timeToDeath;

    /**
     * Class constructor
     * @param enemyList the collection of all the enemies
     * @param sprite the collection of all the enemies images
     * @param player the target of our enemies
     * @see Player
     * @see Sprite
     */
    public EnemyController(final Enemy enemy, final Sprite sprite,
            final Player player) {

        this.loader = new ProxyImage();
        this.enemy = enemy;
        this.enemy.register(this);
        this.sprite = sprite;
        this.player = player;
        //this.initialize(enemyList, sprite);
        this.lastTime = System.currentTimeMillis();
    }

    /**
     * Initialize {@code enemyMap} by associating
     * an enemy to an image
     * @param enemyList the collection of all the enemies
     * @param sprite the collection of all the enemies images
     */
    private void initialize(final List<Enemy> enemyList, final List<Sprite> sprite) {
//        if(enemyList.size() != sprite.size()) {
//            throw new IllegalArgumentException("Different lists size");
//        }
//
//        enemyList.stream().forEach(e -> {
//            e.register(this);
//            final Sprite enemySprite = sprite.get(0);
//            enemySprite.updatePosition(e.getPosition());
//            enemySprite.updateRotation(e.getAngle());
//            this.enemyMap.put(e, enemySprite);
//            sprite.remove(0);
//        });
    }

    @Override
    public Consumer<Double> getUpdateMethod() {
        return deltaTime -> {
            long current = System.currentTimeMillis();
                // TODO to relief performance check if enemy is in range (es. cameraDimension) from the player to start beeing animated
                if(!this.enemy.getActorStatus().equals(ActorStatus.DEAD) && current - lastTime > 350) {
                    this.timeToDeath = current;
                    this.enemy.setIsInPursuit(this.enemy.getAI().isInPursuit(this.player.getPosition(), this.player.getNoiseRadius()));

                    if(this.enemy.getAI().isShooting(this.player.getPosition())) {
                        this.enemy.attack();
                    } else {
                        this.enemy.move(this.enemy.getAI().getNextMove(
                                this.player.getPosition(),
                                this.enemy.isChasingTarget(),
                                this.enemy.getWalkable()));
                    }

                    this.enemy.setIsInPursuit(this.enemy.getAI().isInPursuit(this.player.getPosition(), this.player.getNoiseRadius()));
                    this.enemy.setAngle(this.enemy.getAI().getRotation(this.player.getPosition(), this.enemy.isChasingTarget()));
                    lastTime = System.currentTimeMillis();
                }
                
                if(this.enemy.getActorStatus().equals(ActorStatus.DEAD) && current - timeToDeath > 5000) {
                    this.sprite.updateImage(this.loader.getImage(SceneType.GAME, ImageType.BLANK));
                }
        };
    }

    /**
     * Event triggered every time an enemy moves
     * and updates its image
     * @param e the entity that produce the event
     */
    @Subscribe
    private void updateSpriteOnMoveEvent(final MovementEvent<Enemy> e) {
        this.sprite.updatePosition(e.getPosition());
    }

    /**
     * Event triggered every time an enemy rotates
     * and updates its image
     * @param e the entity that produce the event
     */
    @Subscribe
    private void updateSpriteOnRotationEvent(final RotationEvent<Enemy> e) {
        this.sprite.updateRotation(e.getNewAngle());
    }

    /**
     * Event triggered once an enemy dies and
     * updates its image
     * @param e the entity that produce the event
     */
    @Subscribe
    private void onDeathEvent(final DeathEvent<Enemy> e) {
        this.sprite.updateImage(this.loader.getImage(SceneType.GAME, ImageType.ENEMY_DEAD));
        e.getSource().unregister(this);
        this.timeToDeath = System.currentTimeMillis();
        //JSONDataAccessLayer.getInstance().getEnemy().getEnemies().remove(this.enemy);
    }
}
