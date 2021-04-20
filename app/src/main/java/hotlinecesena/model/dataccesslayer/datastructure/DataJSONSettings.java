package hotlinecesena.model.dataccesslayer.datastructure;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hotlinecesena.model.dataccesslayer.AbstractData;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;

public class DataJSONSettings  extends AbstractData  {
	//GUI SETTINGS
    @JsonProperty("monitorX")
	private int monitorX;
    @JsonProperty("monitorY")
    private int monitorY;
    @JsonProperty("fullScreen")
    private boolean fullScreen;
    @JsonProperty("volume")
    private int volume;
    @JsonProperty("isMusicActive")
    private boolean isMusicActive;
    @JsonProperty("isEffectActive")
    private boolean isEffectActive;
    @JsonProperty("defaultLanguage")
    private int defaultLanguage;
    @JsonProperty("defaultWidth")
    private int defaultWidth;
    @JsonProperty("defaultHeight")
    private int defaultHeight;
    @JsonProperty("resolutions")
    private Map<String, String> resolutions;
    //GENERATORE
    @JsonProperty("niceseeds")
    private List<Long> niceseeds;
    @JsonProperty("minEnemyForRoom")
    private int minEnemyForRoom;
    @JsonProperty("maxEnemyForRoom")
    private int maxEnemyForRoom;
    @JsonProperty("minObstaclesForRoom")
    private int minObstaclesForRoom;
    @JsonProperty("maxObstaclesForRoom")
    private int maxObstaclesForRoom;
    @JsonProperty("minItemForRoom")
    private int minItemForRoom;
    @JsonProperty("maxItemForRoom")
    private int maxItemForRoom;
    @JsonProperty("minRoomWidth")
    private int minRoomWidth;
    @JsonProperty("maxRoomWidth")
    private int maxRoomWidth;
    @JsonProperty("minRoomHeight")
    private int minRoomHeight;
    @JsonProperty("maxRoomHeight")
    private int maxRoomHeight;
    @JsonProperty("minRoomDoor")
    private int minRoomDoor;
    @JsonProperty("maxRoomDoor")
    private int maxRoomDoor;
    @JsonProperty("minBaseRoom")
    private int minBaseRoom;
    @JsonProperty("maxBaseRoom")
    private int maxBaseRoom;
    @JsonProperty("minRoom")
    private int minRoom;
    @JsonProperty("maxRoom")
    private int maxRoom;
    @JsonProperty("minRoomWeapons")
    private int minRoomWeapons;
    @JsonProperty("maxRoomWeapons")
    private int maxRoomWeapons;
    //ALTRO
    @JsonProperty("obstaclesEdge")
    private float obstaclesEdge;
    
    
    public void setDefaultWidth(int defaultWidth) {
		this.defaultWidth = defaultWidth;
	}

	public void setDefaultLanguage(int defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	public void setDefaultHeight(int defaultHeight) {
		this.defaultHeight = defaultHeight;
	}

	public Map<String, String> getResolutions(){
    	return this.resolutions;
    }
	
    public int getDefaultWidth() {
		return this.defaultWidth;
	}

	public int getDefaultHeight() {
		return this.defaultHeight;
	}

	public int getDefaultLanguage() {
		return this.defaultLanguage;
	}
    
    public int getMinRoomWeapons() {
		return this.minRoomWeapons;
	}

	public int getMaxRoomWeapons() {
		return this.maxRoomWeapons;
	}

	public float getObstaclesEdge() {
		return this.obstaclesEdge;
	}

	public int getMinEnemyForRoom() {
		return this.minEnemyForRoom;
	}

	public int getMaxEnemyForRoom() {
		return this.maxEnemyForRoom;
	}

	public int getMinObstaclesForRoom() {
		return this.minObstaclesForRoom;
	}

	public int getMaxObstaclesForRoom() {
		return this.maxObstaclesForRoom;
	}

	public int getMinItemForRoom() {
		return this.minItemForRoom;
	}

	public int getMaxItemForRoom() {
		return this.maxItemForRoom;
	}

	public int getMinRoomWidth() {
		return this.minRoomWidth;
	}

	public int getMaxRoomWidth() {
		return this.maxRoomWidth;
	}

	public int getMinRoomHeight() {
		return this.minRoomHeight;
	}

	public int getMaxRoomHeight() {
		return this.maxRoomHeight;
	}

	public int getMinRoomDoor() {
		return this.minRoomDoor;
	}

	public int getMaxRoomDoor() {
		return this.maxRoomDoor;
	}

	public int getMinBaseRoom() {
		return this.minBaseRoom;
	}

	public int getMaxBaseRoom() {
		return this.maxBaseRoom;
	}

	public int getMinRoom() {
		return this.minRoom;
	}

	public int getMaxRoom() {
		return this.maxRoom;
	}

	public void setMusicActive(final boolean isMusicActive) {
		this.isMusicActive = isMusicActive;
	}

	public void setEffectActive(final boolean isEffectActive) {
		this.isEffectActive = isEffectActive;
	}

	public boolean isMusicActive() {
		return this.isMusicActive;
	}

	public boolean isEffectActive() {
		return this.isEffectActive;
	}

	public List<Long> getNiceseeds() {
		return this.niceseeds;
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

	public boolean getFullScreen() {
		return this.fullScreen;
	}

	public void setFullScreen(final boolean fullScreen) {
		this.fullScreen = fullScreen;
	}

	public int getVolume() {
		return this.volume;
	}

	public void setVolume(final int volume) {
		this.volume = volume;
	}
	
	@Override
	public void write() throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.writeValue(new File(JSONDataAccessLayer.FILE_FOLDER_PATH + "settings.json"), this);
	}
}
