package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class CouldNotMapRecord implements ExceptionMessage {

  private final String recordType;
  private final String dungeonId;

  public CouldNotMapRecord(String recordType, String dungeonId) {
    this.recordType = recordType;
    this.dungeonId = dungeonId;
  }

  @Override
  public String getContent() {
    return "Konnte " + recordType + " | " + dungeonId + " nicht mappen.";
  }
}
