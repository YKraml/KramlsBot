package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.model.dungeon.Team;

public class TeamGavePocketMessage extends MyMessage {

  private final Team team;

  public TeamGavePocketMessage(Team team) {
    super();
    this.team = team;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {

  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    String playerName = team.getPlayer().getNameTag();
    long money = team.getInventory().getMoney();
    long stardust = team.getInventory().getStardust();
    long cookies = team.getInventory().getCookies();

    return this.convertStringToEmbed(
        "%s, du hast %d Euro, %d Stardust und %d Cookies erhalten".formatted(playerName, money,
            stardust, cookies));
  }
}
