package routines;

import actions.commands.Answer;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.MessageSenderImpl;
import messages.messages.WaifuStats;
import org.javacord.api.entity.channel.TextChannel;
import waifu.JikanFetcher;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Player;

public class RoutineShowWaifuById extends Routine {

  private final Player player;
  private final int index;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;
  private final TextChannel channel;

  public RoutineShowWaifuById(Player player, int index, PlayerLoader playerLoader,
      WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender,
      TextChannel channel) {
    this.player = player;
    this.index = index;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
    this.channel = channel;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    if (player.getWaifuList().size() >= index) {
      MessageSenderImpl result;
      synchronized (MessageSenderImpl.class) {
        result = new MessageSenderImpl();
      }
      result.send(new WaifuStats(player.getWaifuList().get(index), player, playerLoader,
          waifuLoader, jikanFetcher, messageSender), channel);
    } else {
      channel.sendMessage(
          "Die Zahl muss zwischen " + 0 + " und " + (player.getWaifuList().size() - 1) + " sein.");
    }

    return new Answer("Someone showed a Waifu.");
  }
}
