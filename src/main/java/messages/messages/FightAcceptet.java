package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

public class FightAcceptet extends MyMessage {

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
  protected void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
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
