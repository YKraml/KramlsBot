package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;

public class CouldNotSaveTime implements ExceptionMessage {

  private final String userId;
  private final String serverId;

  public CouldNotSaveTime(String serverId, String userId) {
    this.userId = userId;
    this.serverId = serverId;
  }

  @Override
  public String getContent() {
    return "Cold not save time from player \"" + userId + "\" on server \"" + serverId + "\".";
  }
}
