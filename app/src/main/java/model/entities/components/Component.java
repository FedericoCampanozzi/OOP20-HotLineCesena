package model.entities.components;

/**
 * <p>
 * Heavily inspired by https://github.com/divotkey/ecs
 * </p>
 * <p>
 * Encapsulates logic pertaining to a specific subset of the actors' possible actions.
 * <br>
 * In our case, components will store data and methods relative to movement, combat, etc.
 * </p>
 * <p>
 * When creating an interface for a new component, extend this one.
 * </p>
 */
public interface Component {

    void update();

}
