package actions.listeners.reaction;

import embeds.dungeon.DungeonsDeletionListEmbed;
import exceptions.MyOwnException;
import java.util.List;
import messages.MessageSender;
import messages.messages.DungeonDeleted;
import messages.messages.DungeonNotFound;
import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import waifu.loader.DungeonLoader;
import waifu.model.dungeon.Dungeon;

public class DungeonDeletionListListener extends MyAbstractListListener<Dungeon> {

  private final DungeonLoader dungeonLoader;
  private final MessageSender messageSender;
  private final String serverId;

  public DungeonDeletionListListener(List<Dungeon> displayableElementList,
      DungeonLoader dungeonLoader, MessageSender messageSender, String serverId) {
    super(displayableElementList);
    this.dungeonLoader = dungeonLoader;
    this.messageSender = messageSender;
    this.serverId = serverId;
  }

  @Override
  protected void updateMessage(Message message, int page) throws MyOwnException {
    List<Dungeon> dungeons = dungeonLoader.getAllDungeonsFromServer(serverId);
    message.edit(new DungeonsDeletionListEmbed(dungeons, page));
  }

  @Override
  protected void reactToCountEmoji(TextChannel textChannel, int listPosition, Server server,
      User user)
      throws MyOwnException {
    List<Dungeon> dungeons = dungeonLoader.getAllDungeonsFromServer(serverId);
    Dungeon dungeonToDelete = dungeons.get(listPosition);

    dungeonLoader.deleteDungeon(dungeonToDelete);
    messageSender.send(new DungeonDeleted(dungeonToDelete), textChannel);
    textChannel.getApi().getServerChannelById(dungeonToDelete.getChannelId()).ifPresent(ServerChannel::delete);
  }

  @Override
  protected void reactToTooHighCountEmoji(TextChannel textChannel, int listPosition)
      throws MyOwnException {
    messageSender.send(new DungeonNotFound(listPosition),textChannel);
  }
}
