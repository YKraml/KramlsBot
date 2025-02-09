package ui.messages.messages;

import domain.PlayerLoader;
import domain.exceptions.MyOwnException;
import domain.waifu.dungeon.Dungeon;
import domain.waifu.dungeon.Team;
import java.util.List;
import logic.messages.MessageSender;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.dungeon.DungeonListEmbed;
import ui.messages.MyMessageAbs;
import ui.reaction.DungeonListListener;

public class DungeonList extends MyMessageAbs {

  private final Team team;
  private final List<Dungeon> dungeonList;
  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;

  public DungeonList(Team team, List<Dungeon> dungeonList, PlayerLoader playerLoader,
      MessageSender messageSender) {
    this.team = team;
    this.dungeonList = dungeonList;
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
  }

  @Override
  public void startRoutine(Message message) throws MyOwnException {
    this.addCountEmojis(message, dungeonList.size());
    message.addReactionAddListener(new DungeonListListener(dungeonList, team, playerLoader,
        messageSender));
  }

  @Override
  public EmbedBuilder getContent() throws MyOwnException {
    return new DungeonListEmbed(dungeonList, 0);
  }

}
