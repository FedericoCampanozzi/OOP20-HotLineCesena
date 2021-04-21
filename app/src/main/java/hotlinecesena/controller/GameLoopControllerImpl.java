package hotlinecesena.controller;

import java.util.*;
import java.util.function.Consumer;
import javafx.animation.AnimationTimer;

public class GameLoopControllerImpl implements GameLoopController {
	private final Set<Updatable> methods = new HashSet<>();
	private long lastTime;
	private AnimationTimer animationTimer;

	public GameLoopControllerImpl() {
		lastTime = System.currentTimeMillis();
	}

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

	private void loopMethod() {
		final long current = System.currentTimeMillis();
		final int elapsed = (int) (current - lastTime);
		/*
		for (Consumer<Double> m : this.methods) {
			m.accept((double) elapsed / 1000.0d);
		}*/
		for (Updatable m : this.methods) {
			m.getUpdateMethod().accept((double) elapsed / 1000.0d);
		}
		this.lastTime = current;
	}

	@Override
	public void addMethodToUpdate(final Updatable m) {
		methods.add(m);
	}

	@Override
	public void stop() {
		this.animationTimer.stop();
	}
	
	@Override
	public void restart() {
		this.animationTimer.start();
	}
}
