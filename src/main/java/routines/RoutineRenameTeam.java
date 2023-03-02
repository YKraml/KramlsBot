package routines;

import actions.listeners.commands.Answer;
import exceptions.MyOwnException;
import java.util.Optional;
import messages.MessageSenderImpl;
import messages.messages.TeamRenamedMessage;
import org.javacord.api.entity.channel.TextChannel;
import waifu.loader.PlayerLoader;
import waifu.model.Player;
import waifu.model.dungeon.Team;

public class RoutineRenameTeam extends Routine {

  private final Player player;
  private final PlayerLoader playerLoader;
  private final TextChannel channel;
  private final String newName;
  private final String oldName;

  public RoutineRenameTeam(Player player, PlayerLoader playerLoader, TextChannel channel,
      String newName, String oldName) {
    this.player = player;
    this.playerLoader = playerLoader;
    this.channel = channel;
    this.newName = newName;
    this.oldName = oldName;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    Optional<Team> team = player.getTeamByName(oldName);
    if (team.isPresent()) {
      team.get().setName(newName);
      MessageSenderImpl result;
      synchronized (MessageSenderImpl.class) {
        result = new MessageSenderImpl();
      }
      result
          .send(new TeamRenamedMessage(player, oldName, newName), channel);
      playerLoader.savePlayer(player);
    } else {
      channel.sendMessage(player.getNameTag() + ", konnte Team \"" + oldName + "\" nicht finden.");
    }

    return new Answer("Someone renamed a team.");
  }
}
