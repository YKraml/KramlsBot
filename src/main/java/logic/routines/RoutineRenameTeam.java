package logic.routines;

import domain.Answer;
import domain.PlayerLoader;
import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.dungeon.Team;
import java.util.Optional;
import logic.messages.MessageSender;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

public class RoutineRenameTeam extends Routine {

  private final User user;
  private final PlayerLoader playerLoader;
  private final TextChannel channel;
  private final String newName;
  private final String oldName;
  private final MessageSender messageSender;

  public RoutineRenameTeam(User user, PlayerLoader playerLoader, TextChannel channel,
      String newName, String oldName, MessageSender messageSender) {
    this.user = user;
    this.playerLoader = playerLoader;
    this.channel = channel;
    this.newName = newName;
    this.oldName = oldName;
    this.messageSender = messageSender;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    Player player = playerLoader.getPlayerByUser(user);
    Optional<Team> team = player.getTeamByName(oldName);
    if (team.isPresent()) {
      team.get().setName(newName);
      messageSender.sendTeamRenamedMessage(channel, player, oldName, newName);
      playerLoader.savePlayer(player);
    } else {
      channel.sendMessage(player.getNameTag() + ", konnte Team \"" + oldName + "\" nicht finden.");
    }

    return new Answer("Someone renamed a team.");
  }
}
