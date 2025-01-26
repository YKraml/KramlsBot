package logic.youtube;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotMap;

public class MyObjectMapper {

  private final ObjectMapper objectMapper;

  public MyObjectMapper() {
    this.objectMapper = JsonMapper.builder()
        .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).build();
  }


  public <T> T map(String data, Class<T> t) throws MyOwnException {

    try {
      return objectMapper.readValue(data, t);
    } catch (JsonProcessingException e) {
      throw new MyOwnException(new CouldNotMap(data, t.getSimpleName(), e), null);
    }
  }

}
