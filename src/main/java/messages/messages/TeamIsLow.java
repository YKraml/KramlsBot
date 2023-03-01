package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.model.dungeon.Team;

public class TeamIsLow extends MyMessage {

  private final Team team;

  public TeamIsLow(Team team) {
    super();
    this.team = team;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {

  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    String pattern = "%s, dein Team \"%s\" hat nur noch %d%% HP.";
    String message = pattern.formatted(team.getPlayer().getNameTag(),
        team.getName(), team.getHpPercentage());
    return this.convertStringToEmbed(message);
  }
}
