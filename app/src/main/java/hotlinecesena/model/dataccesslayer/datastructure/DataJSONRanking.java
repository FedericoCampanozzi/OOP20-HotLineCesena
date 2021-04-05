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

public class DataJSONRanking extends AbstractData {
	
	public static class Row {
		@JsonProperty("name")
		private String name;
		@JsonProperty("points")
		private int points;
		@JsonProperty("time")
		private int time;
		@JsonProperty("enemy_killed")
		private int enemy_killed;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getPoints() {
			return points;
		}
		public void setPoints(int points) {
			this.points = points;
		}
		public int getTime() {
			return time;
		}
		public void setTime(int time) {
			this.time = time;
		}
		public int getEnemy_killed() {
			return enemy_killed;
		}
		public void setEnemy_killed(int enemy_killed) {
			this.enemy_killed = enemy_killed;
		}
	}
	
	@JsonProperty("description")
	private Map<String,String> description;
	
	@JsonProperty("records")
	private List<Row> records;
	
	public List<Row> getRecords() {
		return records;
	}

	public void setRecords(List<Row> records) {
		this.records = records;
	}

	public Map<String, String> getDescription() {
		return description;
	}
	
	public DataJSONRanking() throws JsonGenerationException, JsonMappingException, IOException {
		read();
	}
	
	@Override
	public void write() throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.writeValue(new File(JSONDataAccessLayer.FILE_FOLDER_PATH + "ranking.json"), this);
	}
	
	@Override
	public void read() throws JsonGenerationException, JsonMappingException, IOException {
		 
	}

}