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

public class DataJSONLanguages extends AbstractData {

	@JsonProperty("languagesMapped")
	private List<decode> languagesMapped;
	@JsonProperty("decodifications")
	private List<decode> decodifications;
	
	public static class decode {
		@JsonProperty("idFxml")
		private String idFxml;
		@JsonProperty("fileName")
		private String fileName;
		@JsonProperty("languagesTranslation")
		private List<String> languagesTranslation;
		public String getIdFxml() {
			return idFxml;
		}
		public void setIdFxml(String idFxml) {
			this.idFxml = idFxml;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public List<String> getLanguagesTranslation() {
			return languagesTranslation;
		}
		public void setLanguagesTranslation(List<String> languagesTranslation) {
			this.languagesTranslation = languagesTranslation;
		}
	}
	
	public List<decode> getDecodifications() {
		return decodifications;
	}
	
	@Override
	public void write() throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.writeValue(new File(JSONDataAccessLayer.FILE_FOLDER_PATH + "languages.json"), this);
	}
}
