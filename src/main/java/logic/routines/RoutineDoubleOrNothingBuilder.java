package logic.routines;

import com.google.inject.Inject;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import logic.waifu.PlayerLoader;

public class RoutineDoubleOrNothingBuilder {

  private final RoutineGamblingDoubleBuilder routineGamblingDoubleBuilder;
  private final PlayerLoader playerLoader;

  @Inject
  public RoutineDoubleOrNothingBuilder(RoutineGamblingDoubleBuilder routineGamblingDoubleBuilder,
      PlayerLoader playerLoader) {
    this.routineGamblingDoubleBuilder = routineGamblingDoubleBuilder;
    this.playerLoader = playerLoader;
  }

  public RoutineDoubleOrNothing createRoutineDoubleOrNothing(User user, TextChannel channel) {
    return new RoutineDoubleOrNothing(user, channel, routineGamblingDoubleBuilder, playerLoader);
  }
}