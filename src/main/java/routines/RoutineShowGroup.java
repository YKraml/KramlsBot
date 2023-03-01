package routines;

import actions.commands.Answer;
import exceptions.MyOwnException;
import java.util.Optional;
import messages.MessageSender;
import messages.messages.GroupOverview;
import org.javacord.api.entity.channel.TextChannel;
import waifu.JikanFetcher;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Group;
import waifu.model.Player;

public class RoutineShowGroup extends Routine {

  private final Player player;
  private final String groupName;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;
  private final TextChannel channel;

  public RoutineShowGroup(Player player, String groupName, PlayerLoader playerLoader,
      WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender,
      TextChannel channel) {
    this.player = player;
    this.groupName = groupName;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
    this.channel = channel;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    Optional<Group> group = player.getGroupByName(groupName);
    if (group.isPresent()) {
      messageSender.send(new GroupOverview(group.get(), player, playerLoader, waifuLoader, jikanFetcher,
          messageSender), channel);
    } else {
      channel.sendMessage("Konnte die Gruppe \"" + groupName + "\" nicht finden.");
    }

    return new Answer("Someone showed a group");
  }
}
