package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.dungeon.Inventory;
import domain.waifu.dungeon.Team;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class TeamKilled extends MyMessageAbs {

  private final Team team;
  private final int level;
  private final Inventory inventory;

  public TeamKilled(Team team, int level, Inventory inventory) {
    this.team = team;
    this.level = level;
    this.inventory = inventory;
  }

  @Override
  public void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  public EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed(
        "%s, das Team \"%s\" ist KO gegangen und hat Ebene %d erreicht.\nZudem hat es %d Euro, %d Stardust %d Cookies und %d Morphsteine verloren.".formatted(
            team.getPlayer().getNameTag(), team.getName(), level, inventory.getMoney(),
            inventory.getStardust(), inventory.getCookies(), inventory.getMorphStones()));
  }
}
