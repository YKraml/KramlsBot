package logic.routines;

import domain.Answer;
import domain.PlayerLoader;
import domain.exceptions.MyOwnException;
import logic.messages.MessageSender;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.user.User;

public class RoutineStartFight extends Routine {

  private final User user;
  private final User userEnemy;
  private final long money;
  private final long stardust;
  private final long morphStones;
  private final MessageSender messageSender;
  private final TextChannel textChannel;
  private final PlayerLoader playerLoader;


  public RoutineStartFight(User user, User userEnemy, long money, long stardust, long morphStones,
      MessageSender messageSender, TextChannel textChannel, PlayerLoader playerLoader) {
    this.user = user;
    this.userEnemy = userEnemy;
    this.money = money;
    this.stardust = stardust;
    this.morphStones = morphStones;
    this.messageSender = messageSender;
    this.textChannel = textChannel;
    this.playerLoader = playerLoader;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {

    textChannel.sendMessage(userEnemy.getMentionTag());
    Message fightRequestMessage = messageSender.sendFightRequest(textChannel, user, userEnemy,
        money, stardust, morphStones);

    // TODO: 17.11.2023 Kampf neu machen. Idee: Angreifer wählt Waifu und möglicher Gewinn im voraus aus. Verteidiger akzektiert nur mit Waifu.

    return new Answer(
        "%s hat %s zum Kampf herausgefordert.".formatted(user.getName(), userEnemy.getName()));
  }


}
