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
import static java.util.stream.Collectors.*;

/**
 * Class that provides to memorization of each languages his transactions
 * @author Federico
 *
 */
public class DataJSONLanguages extends AbstractData {

	@JsonProperty("language")
	private List<String> language;
	@JsonProperty("decodifications")
	private List<decode> decodifications;
	
	/**
	 * Class that represent how translate a single 
	 * GUI object (label, button, message etc..)
	 * @author Federico
	 *
	 */
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
	
	/**
	 * Get a map that for each id_fxml map his translations
	 * @param lName
	 * @return
	 */
	public Map<String, String> getLanguageMap(String lName){
		final int index = this.language.indexOf(lName);
		return this.decodifications.stream()
				.collect(toMap(
						e -> e.getLanguagesTranslation().get(0),
						e -> e.getLanguagesTranslation().get(index)
					));
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
