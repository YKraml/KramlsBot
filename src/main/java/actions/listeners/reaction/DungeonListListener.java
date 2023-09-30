package actions.listeners.reaction;

import exceptions.MyOwnException;
import java.util.List;
import messages.MessageSender;
import messages.messages.DungeonNotFound;
import messages.messages.TeamEnteredDungeon;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import waifu.loader.PlayerLoader;
import waifu.model.dungeon.Dungeon;
import waifu.model.dungeon.Team;

public class DungeonListListener extends MyAbstractListListener<Dungeon> {

  private final List<Dungeon> dungeonList;
  private final Team team;
  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;

  public DungeonListListener(List<Dungeon> dungeonList, Team team, PlayerLoader playerLoader,
      MessageSender messageSender) {
    super(dungeonList);
    this.dungeonList = dungeonList;
    this.team = team;
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
  }

  @Override
  protected void updateMessage(Message message, int page) {
    //Just ignore.
  }

  @Override
  protected void reactToCountEmoji(Server server, TextChannel textChannel, User user,
      int listPosition)
      throws MyOwnException {
    Dungeon dungeon = dungeonList.get(listPosition);
    team.entersDungeon(dungeon, 1);
    playerLoader.savePlayer(team.getPlayer());
    messageSender.send(new TeamEnteredDungeon(team, dungeon), textChannel);
  }

  @Override
  protected void reactToTooHighCountEmoji(TextChannel textChannel, int emojiNumber)
      throws MyOwnException {
    messageSender.send(new DungeonNotFound(emojiNumber), textChannel);
  }
}
