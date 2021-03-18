package hotlinecesena.controller.physics;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

public class SimpleMovementImpl implements Movement {

	private final Node obj;
	private final float speed_rotation;
	private final float speed_movement;

	public SimpleMovementImpl(final Node obj, final float speed_rotation, final float speed_movement) {
		this.obj = obj;
		this.speed_rotation = speed_rotation;
		this.speed_movement = speed_movement;
	}

	@Override
	public Transform goForward(float deltaTime) {
		return move(new Point2D(0, 1).multiply(this.speed_movement * deltaTime));
	}

	@Override
	public Transform goBackward(float deltaTime) {
		return move(new Point2D(0, 1).multiply(-this.speed_movement * deltaTime));
	}

	@Override
	public Transform goLeft(float deltaTime) {
		return move(new Point2D(1, 0).multiply(-this.speed_movement * deltaTime));
	}

	@Override
	public Transform goRight(float deltaTime) {
		return move(new Point2D(1, 0).multiply(-this.speed_movement * deltaTime));
	}

	@Override
	public Transform rotateLeft(float deltaTime) {
		return rotate(new Point2D(1, 0).multiply(-this.speed_rotation * deltaTime));
	}

	@Override
	public Transform rotateRight(float deltaTime) {
		return rotate(new Point2D(1, 0).multiply(-this.speed_rotation * deltaTime));
	}

	@Override
	public Transform getTransform() {
		return this.obj.getTransforms().get(0);
	}

	private Transform rotate(final Point2D delta) {
		this.obj.getTransforms().add(new Rotate(0, delta.getX(), delta.getY()));
		return getTransform();
	}

	private Transform move(final Point2D delta) {
		getTransform().transform(delta);
		return getTransform();
	}
}
