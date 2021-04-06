package hotlinecesena.model.dataccesslayer.datastructure;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hotlinecesena.model.dataccesslayer.AbstractData;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;

public class DataJSONSettings  extends AbstractData  {
	
	@JsonProperty("monitorX")
	private int monitorX;
    @JsonProperty("monitorY")
	private int monitorY;
    @JsonProperty("isFullScreen")
	private boolean isFullScreen;
    @JsonProperty("volume")
	private int volume;
    @JsonProperty("tileSize")
	private int tileSize;
    
	public int getMonitorX() {
		return monitorX;
	}

	public void setMonitorX(int monitorX) {
		this.monitorX = monitorX;
	}

	public int getMonitorY() {
		return monitorY;
	}

	public void setMonitorY(int monitorY) {
		this.monitorY = monitorY;
	}

	public boolean isFullScreen() {
		return isFullScreen;
	}

	public void setFullScreen(boolean isFullScreen) {
		this.isFullScreen = isFullScreen;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getTileSize() {
		return tileSize;
	}

	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}

	@Override
	public void write() throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.writeValue(new File(JSONDataAccessLayer.FILE_FOLDER_PATH + "settings.json"), this);
	}
}
