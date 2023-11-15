package logic.routines;

import ui.commands.Answer;
import domain.exceptions.MyOwnException;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import logic.waifu.PlayerLoader;
import domain.waifu.Player;

public class RoutineDoubleOrNothing extends Routine {

  private final User user;
  private final TextChannel channel;
  private final RoutineGamblingDoubleBuilder builder;
  private final PlayerLoader playerLoader;

  public RoutineDoubleOrNothing(User user, TextChannel channel,
      RoutineGamblingDoubleBuilder builder, PlayerLoader playerLoader) {
    this.user = user;
    this.channel = channel;
    this.builder = builder;
    this.playerLoader = playerLoader;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    Player player = playerLoader.getPlayerByUser(user);
    long wonMoney = player.getInventory().getMoney();
    return routineRunner.start(
        builder.createRoutineDoubleOrNothingWithParameter(user, wonMoney, channel));
  }
}