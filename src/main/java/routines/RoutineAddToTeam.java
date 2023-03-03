package routines;

import actions.listeners.commands.Answer;
import exceptions.MyOwnException;
import exceptions.messages.TeamIsInDungeon;
import exceptions.messages.TeamNotAddedWaifu;
import exceptions.messages.TeamNotFound;
import java.util.Optional;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import waifu.loader.PlayerLoader;
import waifu.model.Player;
import waifu.model.Waifu;
import waifu.model.dungeon.Team;

public class RoutineAddToTeam extends Routine {

  private final User user;
  private final String teamName;
  private final int waifuIndex;
  private final TextChannel channel;
  private final PlayerLoader playerLoader;

  public RoutineAddToTeam(PlayerLoader playerLoader, TextChannel channel, User user, String teamName, int waifuIndex) {
    this.playerLoader = playerLoader;
    this.channel = channel;
    this.user = user;
    this.teamName = teamName;
    this.waifuIndex = waifuIndex;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    Player player = playerLoader.getPlayerByUser(user);
    Optional<Team> team = player.getTeamByName(teamName);
    Waifu waifu = player.getWaifuList().get(waifuIndex);

    if (team.isEmpty()) {
      throw new MyOwnException(new TeamNotFound(player, teamName), null);
    }

    if (team.get().isInDungeon()) {
      throw new MyOwnException(new TeamIsInDungeon(team.get()), null);
    }


    try {
      team.get().addWaifuToTeam(waifu, waifu.getHp());
      channel.sendMessage(
          player.getNameTag() + ", \"" + waifu.getName() + "\" ist nun im Team \"" + team.get().getName() + "\"");
      playerLoader.savePlayer(player);
    } catch (MyOwnException e) {
      throw new MyOwnException(new TeamNotAddedWaifu(player, waifu), e);
    }

    return new Answer("Someone added a Waifu to his team.");
  }
}
