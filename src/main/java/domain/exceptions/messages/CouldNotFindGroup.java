package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class CouldNotFindGroup implements ExceptionMessage {

    private final String groupName;

    public CouldNotFindGroup(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String getContent() {
        return "Konnte die Gruppe '%s' nicht finden.".formatted(groupName);
    }
}
