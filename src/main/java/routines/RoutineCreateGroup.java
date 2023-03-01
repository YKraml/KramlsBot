package routines;

import actions.commands.Answer;
import exceptions.MyOwnException;
import java.util.UUID;
import messages.MessageSender;
import messages.messages.GroupCreated;
import org.javacord.api.entity.channel.TextChannel;
import waifu.loader.PlayerLoader;
import waifu.model.Group;
import waifu.model.Player;

public class RoutineCreateGroup extends Routine {

  private final String groupName;
  private final Player player;
  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;
  private final TextChannel channel;

  public RoutineCreateGroup(String groupName, Player player, PlayerLoader playerLoader,
      MessageSender messageSender, TextChannel channel) {
    this.groupName = groupName;
    this.player = player;
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
    this.channel = channel;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    String id = UUID.randomUUID().toString();
    Group group = new Group(id, groupName);

    player.addGroup(group);
    messageSender.send(new GroupCreated(groupName), channel);
    playerLoader.savePlayer(player);

    return new Answer("Someone created a group");
  }
}
