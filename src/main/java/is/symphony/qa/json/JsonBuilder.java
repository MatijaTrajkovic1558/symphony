package is.symphony.qa.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonBuilder {

    private Object sourceObject;
    private ObjectMapper mapper;
    private boolean serializeNull;
    private boolean serializeEmpty;

    public JsonBuilder ignoreNull() {
        this.serializeNull = false;
        return this;
    }

    public JsonBuilder includeNull() {
        this.serializeNull = true;
        return this;
    }

    public JsonBuilder ignoreEmpty() {
        this.serializeEmpty = false;
        return this;
    }

    public JsonBuilder(Object source) {
        this.sourceObject = source;
        this.serializeNull = false;
        this.serializeEmpty = true;
        this.mapper = new ObjectMapper();
    }

    public String build() throws Exception {
        try {
            if(!serializeEmpty) this.mapper.setSerializationInclusion(Include.NON_EMPTY);
            if(!serializeNull) this.mapper.setSerializationInclusion(Include.NON_NULL);
            return this.mapper.writeValueAsString(this.sourceObject);
        }
        catch(JsonProcessingException ex) {
            throw new Exception("Json processing error. " + ex.getMessage());
        }
    }
}