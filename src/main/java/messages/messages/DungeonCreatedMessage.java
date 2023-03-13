package messages.messages;

import embeds.dungeon.DungeonCreatedEmbed;
import embeds.dungeon.DungeonEmbed;
import exceptions.MyOwnException;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.model.dungeon.Dungeon;

public class DungeonCreatedMessage extends MyMessage {

  private final Dungeon dungeon;

  public DungeonCreatedMessage(Dungeon dungeon) {
    super();
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
