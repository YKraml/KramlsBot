package logic.routines;

import domain.Answer;
import domain.PlayerLoader;
import domain.WaifuLoader;
import domain.exceptions.MyOwnException;
import domain.waifu.Group;
import domain.waifu.Player;
import java.util.Optional;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import org.javacord.api.entity.channel.TextChannel;
import util.Terminal;

public class RoutineShowGroup extends Routine {

  private final Player player;
  private final String groupName;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;
  private final TextChannel channel;
  private final Terminal terminal;

  public RoutineShowGroup(Player player, String groupName, PlayerLoader playerLoader,
      WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender,
      TextChannel channel, Terminal terminal) {
    this.player = player;
    this.groupName = groupName;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
    this.channel = channel;
    this.terminal = terminal;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    Optional<Group> group = player.getGroupByName(groupName);
    if (group.isPresent()) {
      messageSender.sendGroupOverview(channel, group.get(), player, playerLoader, waifuLoader,
          jikanFetcher, messageSender, terminal);
    } else {
      channel.sendMessage("Konnte die Gruppe \"" + groupName + "\" nicht finden.");
    }

    return new Answer("Someone showed a group");
  }
}
