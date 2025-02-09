package logic.routines;

import domain.Answer;
import domain.GroupLoader;
import domain.PlayerLoader;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotFindGroup;
import domain.waifu.Group;
import domain.waifu.Player;
import org.javacord.api.entity.message.Messageable;
import org.javacord.api.entity.user.User;

public class RoutineDeleteGroup extends Routine {

  private final User user;
  private final String groupName;
  private final Messageable channel;
  private final GroupLoader groupLoader;
  private final PlayerLoader playerLoader;

  public RoutineDeleteGroup(User user, String groupName, Messageable channel,
      GroupLoader groupLoader, PlayerLoader playerLoader) {
    this.user = user;
    this.groupName = groupName;
    this.channel = channel;
    this.groupLoader = groupLoader;
    this.playerLoader = playerLoader;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    Player player = playerLoader.getPlayerByUser(user);
    Group group = player.getGroupByName(groupName)
        .orElseThrow(() -> new MyOwnException(new CouldNotFindGroup(groupName), null));

    player.deleteGroup(group);
    channel.sendMessage("Gruppe '%s' gelöscht.".formatted(group.getName()));
    groupLoader.deleteGroup(group);

    return new Answer("Someone deleted a group");
  }
}
