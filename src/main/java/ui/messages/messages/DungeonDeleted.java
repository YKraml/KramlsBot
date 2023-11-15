package ui.messages.messages;

import domain.exceptions.MyOwnException;
import ui.messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import domain.waifu.dungeon.Dungeon;

public class DungeonDeleted extends MyMessage {

  private final Dungeon dungeon;

  public DungeonDeleted(Dungeon dungeonToDelete) {
    dungeon = dungeonToDelete;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed("Dungeon '%s' wurde gelöscht.".formatted(dungeon.getName()));
  }
}
