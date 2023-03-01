package routines;

import actions.commands.Answer;
import exceptions.MyOwnException;
import exceptions.messages.CouldNotFindGroup;
import org.javacord.api.entity.message.Messageable;
import waifu.loader.GroupLoader;
import waifu.model.Group;
import waifu.model.Player;

public class RoutineDeleteGroup extends Routine {

  private final Player player;
  private final String groupName;
  private final Messageable channel;
  private final GroupLoader groupLoader;

  public RoutineDeleteGroup(Player player, String groupName, Messageable channel,
      GroupLoader groupLoader) {
    this.player = player;
    this.groupName = groupName;
    this.channel = channel;
    this.groupLoader = groupLoader;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    Group group = player.getGroupByName(groupName)
        .orElseThrow(() -> new MyOwnException(new CouldNotFindGroup(groupName), null));

    player.deleteGroup(group);
    channel.sendMessage("Gruppe '%s' gelöscht.".formatted(group.getName()));
    groupLoader.deleteGroup(group);

    return new Answer("Someone deleted a group");
  }
}
