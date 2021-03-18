package hotlinecesena.controller.physics;

import javafx.scene.transform.Transform;

public interface Movement {

	public Transform goForward(float deltaTime);
	public Transform goBackward(float deltaTime);
	public Transform goLeft(float deltaTime);
	public Transform goRight(float deltaTime);
	public Transform rotateLeft(float deltaTime);
	public Transform rotateRight(float deltaTime);
	
	public Transform getTransform();
}
