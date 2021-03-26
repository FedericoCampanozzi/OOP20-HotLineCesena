package hotlinecesena.controller.input;

import java.util.Set;

import javafx.geometry.Point2D;
import javafx.util.Pair;

public interface InputInterpreter<C extends Enum<C>, K extends Enum<K>, M extends Enum<M>> {

    Pair<Set<C>, Point2D> interpret();
}
