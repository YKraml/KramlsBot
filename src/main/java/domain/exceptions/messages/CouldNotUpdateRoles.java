package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class CouldNotUpdateRoles implements ExceptionMessage {

  private final String userID;

  public CouldNotUpdateRoles(String userID) {
    this.userID = userID;
  }

  @Override
  public String getContent() {
    return "Konnte die Rollen von " + userID + " nicht updaten.";
  }
}
