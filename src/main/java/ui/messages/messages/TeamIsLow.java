package ui.messages.messages;

import domain.exceptions.MyOwnException;
import ui.messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import domain.waifu.dungeon.Team;

public class TeamIsLow extends MyMessage {

  private final Team team;

  public TeamIsLow(Team team) {
    this.team = team;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    String pattern = "%s, dein Team \"%s\" hat nur noch %d%% HP.";
    String message = pattern.formatted(team.getPlayer().getNameTag(),
        team.getName(), team.getHpPercentage());
    return this.convertStringToEmbed(message);
  }
}
