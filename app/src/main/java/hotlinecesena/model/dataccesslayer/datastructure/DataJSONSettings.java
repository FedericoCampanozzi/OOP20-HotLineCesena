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
    
	public int getTileSize() {
		return tileSize;
	}
	public int getVolume() {
		return volume;
	}
	public int getMonitorX() {
    	return this.monitorX;
    }
    public void setMonitorX(final int monitorX) {
    	this.monitorX = monitorX;
    }
    public int getMonitorY() {
    	return this.monitorY;
    }
    public void setMonitorY(final int monitorY) {
    	this.monitorY = monitorY;
    }
	public boolean getIsFullScreen() {
		return this.isFullScreen;
	}
	public void setIsFullScreen(final boolean isFullScreen) {
		this.isFullScreen = isFullScreen;
	}
	
	public DataJSONSettings() throws JsonGenerationException, JsonMappingException, IOException {
		read();
	}
	
	@Override
	public void write() throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.writeValue(new File(JSONDataAccessLayer.FILE_FOLDER_PATH + "settings.json"), this);
	}
	
	@Override
	public void read() throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.readValue(new File(JSONDataAccessLayer.FILE_FOLDER_PATH + "settings.json"), DataJSONSettings.class);
	}

}
