package exceptions.messages;

import exceptions.ExceptionMessage;

public class CouldNotGetDataFromApi implements ExceptionMessage {

    private final String urlString;

    public CouldNotGetDataFromApi(String urlString) {
        this.urlString = urlString;
    }

    @Override
    public String getContent() {
        return "Konnte die Daten von der API nicht bekommen. URL: \"" + urlString + "\"";
    }
}
