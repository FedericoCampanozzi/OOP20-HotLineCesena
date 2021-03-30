package hotlinecesena.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.io.IOException;
import java.io.File;
import javafx.util.Pair;
import org.apache.commons.io.FileUtils;

import hotlinecesena.controller.generator.WorldGeneratorImpl;

import static java.util.stream.Collectors.*;

public class DALImpl {
	
	private static DALImpl singleton = null;
	
	private final Map<String, Pair<String,String>> settings;
	private final Map<Character, String> simbols;
	//private final Map<Pair<Integer, Integer>, Character> gameMap;
	private final Map<String, Pair<Integer, Integer>> ranking;
	private final Map<String, List<String>> messages;
	private final String resFileFolder = System.getProperty("user.dir") + File.separator + "src" + File.separator +
			"main" + File.separator +"resources" + File.separator + "File";
	private final Map<String, String> guiPath = new HashMap<String, String>();
	private final WorldGeneratorImpl wgi;
	
	private DALImpl() throws IOException {
		this.simbols = mapFileTo(resFileFolder + File.separator + "simbols.txt",
				(String[] splitted) -> splitted[0].charAt(0), (String[] splitted) -> splitted[1]);
		this.ranking = mapFileTo(resFileFolder + File.separator + "ranking.txt", (String[] splitted) -> splitted[0],
				(String[] splitted) -> new Pair<>(Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2])));
		this.messages = mapFileTo(resFileFolder + File.separator + "messages.txt", (String[] splitted) -> splitted[0],
				(String[] splitted) -> Arrays.asList(splitted).stream().skip(0).collect(toList()));
		this.settings = mapFileTo(resFileFolder + File.separator + "settings.txt", (String[] splitted) -> splitted[0],
				(String[] splitted) -> new Pair<>(splitted[1], splitted[2]));
		
		readGuiFile();
		wgi = new WorldGeneratorImpl(1231,5,5,10,10,10,5).build();
	}

	public WorldGeneratorImpl getGenerator() {
		return this.wgi;
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
		@SuppressWarnings("unchecked")
		List<String> contents = FileUtils.readLines(new File(filePath), "UTF-8");
		for (String line : contents) {
			if (line.charAt(0) == '#')
				continue;
			splitted = line.split(";");
			map.put(getKey.apply(splitted), getValue.apply(splitted));
		}

		return map;
	}
	
	public Map<Character, String> getSimbols(){
		return this.simbols;
	}
	
	public Map<String, Pair<Integer, Integer>> getRanking(){
		return this.ranking;
	}
	
	public Map<String, List<String>> getAllMessage(){
		return this.messages;
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
	/*
	public Map<Pair<Integer, Integer>, Character> getGameMap(){
		return this.gameMap;
	}
	*/
	public Boolean getBooleanSetting(String prop){
		if(settings.get(prop).getKey().equals("java.lang.Boolean")) {
			return Boolean.parseBoolean(settings.get(prop).getValue());
		} else {
			throw new IllegalStateException("Cast non riuscito");
		}
	}
	
	public Integer getIntegerSetting(String prop) {
		if(settings.get(prop).getKey().equals("java.lang.Integer")) {
			return Integer.parseInt(settings.get(prop).getValue());
		} else {
			throw new IllegalStateException("Cast non riuscito");
		}
	}
	
}
