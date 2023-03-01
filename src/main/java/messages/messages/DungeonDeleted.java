package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.model.dungeon.Dungeon;

public class DungeonDeleted extends MyMessage {

  private final Dungeon dungeon;
  public DungeonDeleted(Dungeon dungeonToDelete) {
    super();
    dungeon = dungeonToDelete;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {

  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed("Dungeon '%s' wurde gelöscht.".formatted(dungeon.getName()));
  }
}
