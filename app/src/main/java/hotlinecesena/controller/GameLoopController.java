package hotlinecesena.controller;

import java.util.*;
import java.util.function.Consumer;
import javafx.animation.AnimationTimer;

public class GameLoopController {
	private final Set<Consumer<Double>> methods = new HashSet<>();
	private long lastTime;
	private AnimationTimer animationTimer;

	public GameLoopController() {
		lastTime = System.currentTimeMillis();
	}

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
		for (Consumer<Double> m : this.methods) {
			m.accept((double) elapsed / 1000.0d);
		}
		this.lastTime = current;
	}

	public void addMethodToUpdate(final Consumer<Double> m) {
		methods.add(m);
	}

	public void stop() {
		this.animationTimer.stop();
	}
}
