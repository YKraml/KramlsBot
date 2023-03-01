package routines;

import actions.commands.Answer;
import exceptions.MyOwnException;
import exceptions.messages.CouldNotFindGroup;
import org.javacord.api.entity.channel.TextChannel;
import waifu.loader.PlayerLoader;
import waifu.model.Group;
import waifu.model.Player;
import waifu.model.Waifu;

public class RoutineAddWaifuToGroup extends Routine {

  private final Player player;
  private final String groupName;
  private final int waifuId;
  private final TextChannel channel;
  private final PlayerLoader playerLoader;

  public RoutineAddWaifuToGroup(Player player, String groupName, int waifuId, TextChannel channel,
      PlayerLoader playerLoader) {
    this.player = player;
    this.groupName = groupName;
    this.waifuId = waifuId;
    this.channel = channel;
    this.playerLoader = playerLoader;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    Group group = player.getGroupByName(groupName)
        .orElseThrow(() -> new MyOwnException(new CouldNotFindGroup(groupName), null));

    Waifu waifu = player.getWaifu(waifuId);

    group.addWaifu(waifu);
    playerLoader.savePlayer(player);
    channel.sendMessage(
        "'%s' der Gruppe '%s' hinzugefügt.".formatted(waifu.getName(), group.getName()));

    return new Answer("Added Waifu to group.");
  }
}
