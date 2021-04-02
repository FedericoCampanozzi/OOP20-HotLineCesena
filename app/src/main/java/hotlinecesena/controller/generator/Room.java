package hotlinecesena.controller.generator;

import java.util.Map;

import javafx.util.Pair;

public interface Room {
	
	public void generate(final long seed, final char walkableSimbol, final char doorSimbols, final char wallSimbols);
	public Pair<Integer, Integer> getCenter();
	public void setCenter(Pair<Integer, Integer> center);
	public Map<Pair<Integer, Integer>, Character> getMap();
	public Room deepCopy();
}
