package routines;

import com.google.inject.Inject;
import org.javacord.api.entity.channel.TextChannel;
import waifu.loader.PlayerLoader;
import waifu.model.Player;

public class RoutineDoubleOrNothingWithParameterBuilder {


  private final PlayerLoader playerLoader;

  @Inject
  public RoutineDoubleOrNothingWithParameterBuilder(PlayerLoader playerLoader) {
    this.playerLoader = playerLoader;
  }

  public RoutineDoubleOrNothingWithParameter createRoutineDoubleOrNothingWithParameter(
      Player player, int bettedMoney, TextChannel channel) {
    return new RoutineDoubleOrNothingWithParameter(player, bettedMoney, channel, playerLoader);
  }
}