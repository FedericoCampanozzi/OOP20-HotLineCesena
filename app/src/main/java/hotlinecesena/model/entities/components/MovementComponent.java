package hotlinecesena.model.entities.components;

import hotlinecesena.util.Point2D;

public interface MovementComponent extends Component {

    void move(Point2D<Double, Double> direction, double intensity);

    void rotate(Point2D<Double, Double> v);

    Point2D<Double, Double> getPosition();

    Point2D<Double, Double> getFacingAngle();

}
