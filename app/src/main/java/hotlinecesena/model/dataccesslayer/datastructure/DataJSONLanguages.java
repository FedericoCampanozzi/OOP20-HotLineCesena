package hotlinecesena.model.dataccesslayer.datastructure;

import hotlinecesena.model.dataccesslayer.AbstractData;
import hotlinecesena.model.dataccesslayer.JSONDataAccessLayer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataJSONLanguages extends AbstractData {

	@JsonProperty("description")
	private Map<String,String> description;
	@JsonProperty("language_name_map")
	private List<String> language_name_map;
	@JsonProperty("language_map")
	private Map<String, List<String>> language_map;
	
	public Map<String, String> getDescription() {
		return description;
	}
	public List<String> getLanguage_name_map() {
		return language_name_map;
	}
	public Map<String, List<String>> getLanguage_map() {
		return language_map;
	}
}
