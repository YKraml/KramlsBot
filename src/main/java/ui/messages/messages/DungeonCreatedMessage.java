package ui.messages.messages;

import ui.embeds.dungeon.DungeonCreatedEmbed;
import domain.exceptions.MyOwnException;
import ui.messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import domain.waifu.dungeon.Dungeon;

public class DungeonCreatedMessage extends MyMessage {

  private final Dungeon dungeon;

  public DungeonCreatedMessage(Dungeon dungeon) {
    this.dungeon = dungeon;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return new DungeonCreatedEmbed(dungeon);
  }
}
