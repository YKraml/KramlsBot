package ui.messages.messages;

import domain.exceptions.MyOwnException;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import ui.messages.MyMessageAbs;

public class FightAcceptet extends MyMessageAbs {

  private final User user;
  private final User enemy;
  private final long money;
  private final long stardust;
  private final long morphStones;

  public FightAcceptet(User user, User enemy, long money, long stardust, long morphStones) {
    this.user = user;
    this.enemy = enemy;
    this.money = money;
    this.stardust = stardust;
    this.morphStones = morphStones;
  }

  @Override
  public void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  public EmbedBuilder getContent() throws MyOwnException {
    EmbedBuilder embed = new EmbedBuilder();
    embed.setTitle("Herausforderung angenommen!");
    embed.setThumbnail("https://img.freepik.com/freie-ikonen/schwerter_318-781172.jpg");
    embed.setDescription(
        "%s hat die Herausforderung von %s angenommen. Wählt nun eure Teams aus.".formatted(
            enemy.getName(), user.getName()));
    embed.addField("Auf dem Spiel stehen: ",
        "%s Geld, %s Stardust, %s Morphstein(e)".formatted(money, stardust, morphStones));
    return embed;
  }
}
