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

public class DataJSONRanking extends AbstractData {
	
	public static class Row {
		@JsonProperty("name")
		private String name;
		@JsonProperty("points")
		private int points;
		@JsonProperty("time")
		private int time;
		@JsonProperty("enemyKilled")
		private int enemyKilled;
		@JsonProperty("cunning")
		private double cunning;
		
		public double getCunning() {
			return cunning;
		}
		public void setCunning(double cunning) {
			this.cunning = cunning;
		}
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
		public String getTime() {
			long second = (time / 1000) % 60;
			long minute = (time / (1000 * 60)) % 60;
			long hour = (time / (1000 * 60 * 60)) % 24;
			return String.format("%02d:%02d:%02d", hour, minute, second);
		}
		public void setTime(int time) {
			this.time = time;
		}
		public int getEnemyKilled() {
			return enemyKilled;
		}
		public void setEnemyKilled(int enemyKilled) {
			this.enemyKilled = enemyKilled;
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
