package hotlinecesena.controller.input;

import java.util.Set;
import org.apache.commons.lang3.tuple.Triple;
import javafx.geometry.Point2D;

public interface InputListener<K extends Enum<K>, M extends Enum<M>> {

    Triple<Set<K>, Set<M>, Point2D> deliverInputs();
}
