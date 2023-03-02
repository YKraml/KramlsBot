package messages.messages;

import actions.listeners.reaction.DungeonDeletionListListener;
import embeds.dungeon.DungeonsDeletionListEmbed;
import exceptions.MyOwnException;
import java.util.List;
import messages.MessageSender;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.loader.DungeonLoader;
import waifu.model.dungeon.Dungeon;

public class DeleteDungeonsMessage extends MyMessage {

  private final DungeonLoader dungeonLoader;
  private final MessageSender messageSender;
  private final String serverId;

  public DeleteDungeonsMessage(DungeonLoader dungeonLoader, MessageSender messageSender,
      String serverId) {
    super();
    this.dungeonLoader = dungeonLoader;
    this.messageSender = messageSender;
    this.serverId = serverId;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    List<Dungeon> dungeonList = dungeonLoader.getAllDungeonsFromServer(serverId);
    this.addCountEmojis(message, dungeonList.size());
    message.addReactionAddListener(new DungeonDeletionListListener(dungeonList, dungeonLoader, messageSender,
        serverId));
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    List<Dungeon> dungeonList = dungeonLoader.getAllDungeonsFromServer(serverId);
    return new DungeonsDeletionListEmbed(dungeonList, 0);
  }
}
