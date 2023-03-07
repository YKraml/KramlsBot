package messages.messages;

import discord.Emojis;
import exceptions.MyOwnException;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

public class FightRequest extends MyMessage {

  private final User user;
  private final User enemy;
  private final long money;
  private final long stardust;
  private final long morphStones;

  public FightRequest(User user, User enemy, long money, long stardust, long morphStones) {
    this.user = user;
    this.enemy = enemy;
    this.money = money;
    this.stardust = stardust;
    this.morphStones = morphStones;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    message.addReaction(Emojis.WHITE_CHECK_MARK.getEmoji());
    message.addReaction(Emojis.X.getEmoji());
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    EmbedBuilder embed = new EmbedBuilder();
    embed.setTitle("Kampfherausforderung!");
    embed.setThumbnail("https://img.freepik.com/freie-ikonen/schwerter_318-781172.jpg");
    embed.setDescription("%s, du wurdest von %s zum Kampf herausgefordert. Möchtest du annehmen?".formatted(enemy.getName(), user.getName()));
    embed.addField("Auf dem Spiel stehen: ", "%s Geld, %s Stardust, %s Morphstein(e)".formatted(money,stardust,morphStones));
    return embed;
  }
}
