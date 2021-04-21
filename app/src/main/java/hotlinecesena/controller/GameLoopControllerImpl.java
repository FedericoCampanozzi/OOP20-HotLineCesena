package hotlinecesena.controller;

import java.util.HashSet;
import java.util.Set;
import javafx.animation.AnimationTimer;

/**
 * An implementation on {@code GameLoopController}
 * @author Federico
 */
public class GameLoopControllerImpl implements GameLoopController {
	private final Set<Updatable> methods = new HashSet<>();
	private long lastTime;
	private AnimationTimer animationTimer;

	public GameLoopControllerImpl() {
		lastTime = System.currentTimeMillis();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void loop() {
		animationTimer = new AnimationTimer() {
			@Override
			public void handle(final long now) {
				loopMethod();
			}
		};

		animationTimer.start();
	}
	/**
	 * The effective loop method
	 */
	private void loopMethod() {
		final long current = System.currentTimeMillis();
		final int elapsed = (int) (current - lastTime);
		for (Updatable m : this.methods) {
			m.getUpdateMethod().accept((double) elapsed / 1000.0d);
		}
		this.lastTime = current;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addMethodToUpdate(final Updatable m) {
		methods.add(m);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {
		this.animationTimer.stop();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void restart() {
		this.animationTimer.start();
	}
}
