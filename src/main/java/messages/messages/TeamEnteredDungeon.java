package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.model.Player;
import waifu.model.Waifu;
import waifu.model.dungeon.Dungeon;
import waifu.model.dungeon.Team;

public class TeamEnteredDungeon extends MyMessage {

  private final Team team;
  private final Dungeon dungeon;

  public TeamEnteredDungeon(Team team, Dungeon dungeon) {
    super();
    this.dungeon = dungeon;
    this.team = team;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {

  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed(
        "%s, das Team '%s' hat den Dungeon '%s' betreten.".formatted(team.getPlayer().getNameTag(),
            team.getName(), dungeon.getName()));
  }

}

