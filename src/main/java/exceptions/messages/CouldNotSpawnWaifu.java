package exceptions.messages;

import exceptions.ExceptionMessage;

public class CouldNotSpawnWaifu implements ExceptionMessage {

  private final String serverName;

  public CouldNotSpawnWaifu(String serverName) {
    this.serverName = serverName;
  }

  public String getContent() {
    return "Konnte keine neue Waifu spawnen auf dem Server '%s'.".formatted(serverName);
  }
}
