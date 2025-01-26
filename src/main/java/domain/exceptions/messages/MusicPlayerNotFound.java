package domain.exceptions.messages;

import domain.exceptions.ExceptionMessage;
import org.javacord.api.entity.server.Server;

public class MusicPlayerNotFound implements ExceptionMessage {

  private final Server server;

  public MusicPlayerNotFound(Server server) {
    this.server = server;
  }

  @Override
  public String getContent() {
    return "Konnte den Musicplayer vom Server \"" + server.getName() + "\" nicht finden.";
  }
}
