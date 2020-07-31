package is.symphony.qa.resources.convert;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public class Convert {
	
	public static <T> T fromJson(String json, Class<T> destination) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		return mapper.readValue(json, destination);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T fromXml(String xml, Class<T> destination) throws Exception {
		JAXBContext context = JAXBContext.newInstance(destination);
		Unmarshaller unmar = context.createUnmarshaller();
		return (T) unmar.unmarshal(new StringReader(xml));
	}
	
	public static <T> T from(String body, String format, Class<T> destination) throws Exception {
		if(format.equalsIgnoreCase("json")) return fromJson(body, destination);
		else if(format.equalsIgnoreCase("xml")) return fromXml(body, destination);
		else throw new IllegalArgumentException("Format not allowed");
	}

}
