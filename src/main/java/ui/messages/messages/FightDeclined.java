package ui.messages.messages;

import domain.exceptions.MyOwnException;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import ui.messages.MyMessageAbs;

public class FightDeclined extends MyMessageAbs {

  private final User user;
  private final User enemy;

  public FightDeclined(User user, User enemy) {
    this.user = user;
    this.enemy = enemy;
  }

  @Override
  public void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  public EmbedBuilder getContent() throws MyOwnException {
    return convertStringToEmbed(
        "%s, %s hat deine Herausforderung abgelehnt.".formatted(user.getName(), enemy.getName()));
  }
}
