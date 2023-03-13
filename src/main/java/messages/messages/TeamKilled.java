package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.model.dungeon.Inventory;
import waifu.model.dungeon.Team;

public class TeamKilled extends MyMessage {

  private final Team team;
  private final int level;
  private final Inventory inventory;

  public TeamKilled(Team team, int level, Inventory inventory) {
    this.team = team;
    this.level = level;
    this.inventory = inventory;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed(
        "%s, das Team \"%s\" ist KO gegangen und hat Ebene %d erreicht.\nZudem hat es %d Euro, %d Stardust %d Cookies und %d Morphsteine verloren.".formatted(
            team.getPlayer().getNameTag(), team.getName(), level, inventory.getMoney(),
            inventory.getStardust(), inventory.getCookies(), inventory.getMorphStones()));
  }
}
