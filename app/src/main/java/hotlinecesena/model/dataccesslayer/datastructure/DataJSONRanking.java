package hotlinecesena.model.dataccesslayer.datastructure;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hotlinecesena.model.dataccesslayer.AbstractData;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;
import hotlinecesena.utilities.Utilities;

public class DataJSONRanking extends AbstractData {
	
	public static class Row {
		@JsonProperty("name")
		private String name;
		@JsonProperty("points")
		private int points;
		@JsonProperty("time")
		private String time;
		@JsonProperty("enemyKilled")
		private int enemyKilled;
		@JsonProperty("cunning")
		private int cunning;
		
		public Row() {
			
		}

		public Row(String name, int points, int time, int enemyKilled, int cunning) {
			this.name = name;
			this.points = points;
			this.time = Utilities.convertSecondsToTimeString(time, "%02d:%02d:%02d");
			this.enemyKilled = enemyKilled;
			this.cunning = cunning;
		}
		
		public double getCunning() {
			return cunning;
		}
		
		public String getName() {
			return name;
		}
		
		public int getPoints() {
			return points;
		}
		
		public String getTime() {
			return this.time;
		}
		
		public int getEnemyKilled() {
			return enemyKilled;
		}
	}
	
	@JsonProperty("records")
	private List<Row> records;
	
	public List<Row> getRecords() {
		this.records.sort(Comparator.comparing(Row::getPoints).reversed());
		return records;
	}

	public void setRecords(List<Row> records) {
		this.records = records;
	}

	@Override
	public void write() throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.writeValue(new File(JSONDataAccessLayer.FILE_FOLDER_PATH + "ranking.json"), this);
	}
}
