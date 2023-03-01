package youtube;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.MyOwnException;
import exceptions.messages.CouldNotMap;
import de.kraml.Terminal;

import java.util.Optional;

public class MyObjectMapper {

    private final ObjectMapper objectMapper;

    public MyObjectMapper() {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    public <T> Optional<T> map(String data, Class<T> t) throws MyOwnException {

        try {
            T mappedObject = objectMapper.readValue(data, t);
            return Optional.ofNullable(mappedObject);
        } catch (JsonProcessingException e) {
            throw new MyOwnException(new CouldNotMap(data, t.getSimpleName(), e), null);
        }
    }

}
