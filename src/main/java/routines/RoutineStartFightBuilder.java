package routines;

import javax.inject.Inject;
import messages.MessageSender;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import waifu.loader.PlayerLoader;

public class RoutineStartFightBuilder {

  private final MessageSender messageSender;
  private final PlayerLoader playerLoader;

  @Inject
  public RoutineStartFightBuilder(MessageSender messageSender, PlayerLoader playerLoader){

    this.messageSender = messageSender;
    this.playerLoader = playerLoader;
  }

  public RoutineStartFight createStartFightRoutine(User user, User userEnemy, long money, long stardust,
      long morphStones, TextChannel textChannel) {
    return new RoutineStartFight(user, userEnemy, money, stardust, morphStones, messageSender,
        textChannel, playerLoader);
  }
}