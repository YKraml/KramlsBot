package domain.exceptions.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import domain.exceptions.ExceptionMessage;

public class CouldNotMap implements ExceptionMessage {

    private final String dataToBeMapped;
    private final String className;
    private final JsonProcessingException exceptionThatCausedError;

    public CouldNotMap(String data, String className, JsonProcessingException e) {
        this.dataToBeMapped = data;
        this.className = className;
        this.exceptionThatCausedError = e;
    }

    @Override
    public String getContent() {
        return "Konnte keine Daten mappen. Klasse: \""
                + className
                + "\" | Daten: \""
                + dataToBeMapped
                + "\" | Exception: \""
                + exceptionThatCausedError
                + "\"";
    }
}
