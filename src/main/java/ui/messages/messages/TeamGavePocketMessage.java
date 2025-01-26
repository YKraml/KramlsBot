package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.dungeon.Team;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class TeamGavePocketMessage extends MyMessageAbs {

  private final Team team;

  public TeamGavePocketMessage(Team team) {
    this.team = team;
  }

  @Override
  public void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  public EmbedBuilder getContent() throws MyOwnException {
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
