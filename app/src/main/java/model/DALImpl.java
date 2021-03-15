package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javafx.util.Pair;
import java.io.File;
import java.io.IOException;
import static java.util.stream.Collectors.*;

public class DALImpl {

	private Map<Character, String> simbols = new HashMap<>();
	private Map<Pair<Integer, Integer>, Character> gameMap = new HashMap<>();
	private Map<String, Pair<Integer, Integer>> ranking = new HashMap<>();
	private Map<String, List<String>> messages = new HashMap<>();

	public DALImpl() throws IOException {
		/*
		readGameMap();
		
		simbols = mapFileTo(System.getProperty("user.dir") + File.separator + "assets" + File.separator + "File"
				+ File.separator + "simbols.md", () -> '1', () -> "2");
		
		ranking = mapFileTo(System.getProperty("user.dir") + File.separator + "assets" + File.separator + "File"
				+ File.separator + "simbols.md", () -> splitted[0], () -> Pair.of(Integer.parseInt(splitted[1]),Integer.parseInt(splitted[2])));

		messages = mapFileTo(System.getProperty("user.dir") + File.separator + "assets" + File.separator + "File"
				+ File.separator + "simbols.md", () -> splitted[0], () -> Arrays.asList(splitted).stream().skip(0).collect(toList()));
		*/
	}

	private <Key, Value> Map<Key, Value> mapFileTo (String filePath, Function<String[], Key> getKey,
			Function<String[], Value> getValue) throws IOException {
		
		final String[] splitted;
		final Map<Key, Value> map = new HashMap<Key, Value>();
		/*
		for (String line : FileUtils.readLines(filePath, "UTF-8")) {
			System.out.println(line);
			if (line.charAt(0) == '#')
				continue;
			splitted = line.split(";");
			map.put(getKey.apply(splitted), getValue.apply(splitted));
		}
		*/
		return map;
	}
	
	//this method will be removed when implements map_generator
	private void readGameMap() {
		/*
		File file;
		List<String> contents;
		String[] splitted;
		int iLine = 0;
		int iCar = 0;
		
		System.out.println("\nREAD map.md");
		file = new File(System.getProperty("user.dir") + File.separator + "assets" + File.separator + "File"
				+ File.separator + "map.md");
		contents = FileUtils.readLines(file, "UTF-8");
		for (String line : contents) {
			System.out.println(line);
			if (line.charAt(0) == '#')
				continue;
			iCar = 0;
			for (Character c : line.toCharArray()) {
				if (simbols.containsKey(c)) {
					gameMap.put(new Pair<Integer, Integer>(iCar, iLine), c);
					iCar ++;
				} else {
					throw new IllegalStateException("Carattere non codificato");
				}

			}
			iLine ++;
		}
		*/
	}
}
