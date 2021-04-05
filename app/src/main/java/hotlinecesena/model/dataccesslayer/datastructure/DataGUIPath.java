package hotlinecesena.model.dataccesslayer.datastructure;

import hotlinecesena.model.dataccesslayer.AbstractData;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataGUIPath extends AbstractData {
	public static final String GUI_XML_FOLDER_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator +
			"main" + File.separator +"resources" + File.separator + "GUI";
	
	private final Map<String, String> paths = new HashMap<>();
	
	public DataGUIPath() throws IOException {
		read();
	}
	
	@Override
	public void read() throws IOException {
		for(final File f : new File(GUI_XML_FOLDER_PATH).listFiles()) {
			this.paths.put(f.getName(), f.getAbsolutePath());
		}
	}

	public String getPath(String key) {
		return paths.get(key);
	}
	
	
}
