package hotlinecesena.model.dataccesslayer.datastructure;

import hotlinecesena.model.dataccesslayer.AbstractData;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class DataJSONLanguages extends AbstractData {

	@JsonProperty("description")
	private Map<String,String> description;
	@JsonProperty("language_name_map")
	private List<String> language_name_map;
	@JsonProperty("language_map")
	private Map<String, List<String>> language_map;
	
	public DataJSONLanguages() throws JsonGenerationException, JsonMappingException, IOException {
		read();
	}
	
	@Override
	public void read() throws JsonGenerationException, JsonMappingException, IOException {
		
	}
}
