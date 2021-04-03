package hotlinecesena.controller.generator;

import java.util.Map;

import javafx.util.Pair;

public interface WorldGeneratorBuilder {

	WorldGeneratorBuilder setSimbols(char walkableSimbol, char enemySimbol, char playerSimbol, char doorSimbols,
			char voidSimbols, char wallSimbols);
	
	WorldGeneratorBuilder generateRooms(Integer... args);
	
	WorldGeneratorBuilder generatePlayer();

	WorldGeneratorBuilder generateEnemy(float rate, int maxEnemyRoom);

	WorldGeneratorBuilder generateDecorations();

	WorldGeneratorBuilder finalCheck();

	WorldGeneratorBuilder build();

	Map<Pair<Integer, Integer>, Character> getMap();

	int getMinX();

	int getMaxX();

	int getMinY();

	int getMaxY();
}