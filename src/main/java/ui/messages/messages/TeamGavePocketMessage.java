package ui.messages.messages;

import domain.exceptions.MyOwnException;
import ui.messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import domain.waifu.dungeon.Team;

public class TeamGavePocketMessage extends MyMessage {

  private final Team team;

  public TeamGavePocketMessage(Team team) {
    this.team = team;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    String playerName = team.getPlayer().getNameTag();
    long money = team.getInventory().getMoney();
    long stardust = team.getInventory().getStardust();
    long cookies = team.getInventory().getCookies();
    long morphStones = team.getInventory().getMorphStones();

    return this.convertStringToEmbed(
        "%s, du hast %d Euro, %d Stardust; %d Cookies und %d Morphsteine erhalten".formatted(
            playerName, money,
            stardust, cookies, morphStones));
  }
}
