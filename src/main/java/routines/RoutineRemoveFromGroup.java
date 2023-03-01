package routines;

import actions.commands.Answer;
import exceptions.MyOwnException;
import exceptions.messages.CouldNotFindGroup;
import java.text.MessageFormat;
import messages.MessageSender;
import messages.messages.WaifuNotFound;
import org.javacord.api.entity.channel.TextChannel;
import waifu.loader.GroupLoader;
import waifu.model.Group;
import waifu.model.Player;
import waifu.model.Waifu;

public class RoutineRemoveFromGroup extends Routine {

  private final Player player;
  private final String groupName;
  private final int waifuNumber;
  private final TextChannel channel;
  private final GroupLoader groupLoader;
  private final MessageSender messageSender;

  public RoutineRemoveFromGroup(Player player, String groupName, int waifuNumber,
      TextChannel channel, GroupLoader groupLoader, MessageSender messageSender) {
    this.player = player;
    this.groupName = groupName;
    this.waifuNumber = waifuNumber;
    this.channel = channel;
    this.groupLoader = groupLoader;
    this.messageSender = messageSender;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    Group group = player.getGroupByName(groupName)
        .orElseThrow(() -> new MyOwnException(new CouldNotFindGroup(groupName), null));

    boolean removed = false;
    int i = 0;
    for (Waifu waifu : group.getWaifuSet()) {

      if (i == waifuNumber) {
        removed = group.removeWaifu(waifu);
        channel.sendMessage(
            MessageFormat.format("Waifu \"{0}\" aus der Gruppe \"{1}\" entfernt.", waifu.getName(),
                group.getName()));
        groupLoader.deleteWaifuFromGroup(group, waifu);
        break;
      }

      i++;
    }

    if (!removed) {
      messageSender.send(new WaifuNotFound(waifuNumber), channel);
    }

    return new Answer("Someone deleted a Waifu from his a list.");
  }
}
