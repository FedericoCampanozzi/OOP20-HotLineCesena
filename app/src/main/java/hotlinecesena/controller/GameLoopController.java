package hotlinecesena.controller;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import javafx.animation.AnimationTimer;

public class GameLoopController {
	private final Set<Consumer<Double>> methods = new HashSet<>();
	private long lastTime;
	
	public GameLoopController() {
		lastTime = System.currentTimeMillis();
	}

	public void loop() {
		new AnimationTimer() {
			@Override
			public void handle(final long now) {
				loopMethod();
			}
		}.start();
	}

	private void loopMethod() {
		final long current = System.currentTimeMillis();
		final int elapsed = (int)(current - lastTime);		
		for(final Consumer<Double> m : methods) {
			m.accept((double)elapsed / 1000.0d);
		}
		this.lastTime = current;
	}
	
	public void addMethodToUpdate(final Consumer<Double> m) {
		methods.add(m);
	}

}
