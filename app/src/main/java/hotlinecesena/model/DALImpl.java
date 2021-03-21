package hotlinecesena.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.io.IOException;
import java.io.File;
import javafx.util.Pair;
import org.apache.commons.io.FileUtils;

import static java.util.stream.Collectors.*;

public class DALImpl {
	
	private static DALImpl singleton = null;

	private final Map<Character, String> simbols;
	private final Map<Pair<Integer, Integer>, Character> gameMap = new HashMap<>();
	private final Map<String, Pair<Integer, Integer>> ranking;
	private final Map<String, List<String>> messages;
	private final String resFileFolder = System.getProperty("user.dir") + File.separator + "src" + File.separator +
			"main" + File.separator +"resources" + File.separator + "File";
	private final Map<String, String> guiPath = new HashMap<String, String>();

	private DALImpl() throws IOException {
		System.out.println("\nStart DAL reading \n");
		this.simbols = mapFileTo(resFileFolder + File.separator + "simbols.txt",
				(String[] splitted) -> splitted[0].charAt(0), (String[] splitted) -> splitted[1]);
		this.ranking = mapFileTo(resFileFolder + File.separator + "ranking.txt", (String[] splitted) -> splitted[0],
				(String[] splitted) -> new Pair<>(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2])));
		this.messages = mapFileTo(resFileFolder + File.separator + "messages.txt", (String[] splitted) -> splitted[0],
				(String[] splitted) -> Arrays.asList(splitted).stream().skip(0).collect(toList()));
		readGameMap();
		readGuiFile();
		System.out.println("\nEnd DAL reading \n");
	}

	public static DALImpl getInstance() throws IOException {
		if (singleton == null) {
			singleton = new DALImpl();
		}
		
		return singleton;
	}

	private <Key, Value> Map<Key, Value> mapFileTo(String filePath, Function<String[], Key> getKey,
			Function<String[], Value> getValue) throws IOException {

		String[] splitted;
		final Map<Key, Value> map = new HashMap<Key, Value>();
		final List<String> cnt = FileUtils.readLines(new File(filePath));
		for (String line : cnt) {
			if (line.charAt(0) == '#')
				continue;
			splitted = line.split(";");
			map.put(getKey.apply(splitted), getValue.apply(splitted));
		}

		return map;
	}

	
	public Map<String, Pair<Integer, Integer>> getRanking(){
		return this.ranking;
	}
	public Map<String, List<String>> getAllMessage(){
		return this.messages;
	}
	// this method will be removed when implements map_generator
	private void readGameMap() throws IOException {

		int iLine = 0;
		int iCar = 0;

		System.out.println("\nREAD map.txt");
		Collection<String> cnt = FileUtils.readLines(new File(resFileFolder + File.separator + "map.txt"));
		for (String line : cnt) {
			System.out.println(line);
			if (line.charAt(0) == '#')
				continue;
			iCar = 0;
			for (Character c : line.toCharArray()) {
				if (simbols.containsKey(c)) {
					gameMap.put(new Pair<>(iCar, iLine), c);
					iCar++;
				} else {
					throw new IllegalStateException("Carattere non codificato");
				}

			}
			iLine++;
		}
	}
	
	private void readGuiFile() throws IOException{
		
		System.out.println("\nREAD FXML");
		for(final File f : new File(System.getProperty("user.dir") + File.separator + "src" + File.separator +
				"main" + File.separator + "resources" + File.separator + "GUI").listFiles()) {
			final String relPath = new File(f.getParent()).getName() + File.separator + f.getName(); 
			System.out.println(f.getName() + " -> " + relPath);
			this.guiPath.put(f.getName(), relPath);
		}
	}
	
	public Map<String,String> getGuiPath(){
		return this.guiPath;
	}
}
