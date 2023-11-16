package logic.routines;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.dungeon.Team;
import domain.waifu.fighting.Fighter;
import logic.waifu.PlayerLoader;
import logic.waifu.TeamLoader;
import org.javacord.api.entity.message.Messageable;
import org.javacord.api.entity.user.User;
import ui.commands.Answer;

import java.util.Optional;

public class RoutineRemoveFromTeam extends Routine {

    private final User user;
    private final String teamName;
    private final int waifuNumber;
    private final Messageable channel;
    private final PlayerLoader playerLoader;
    private final TeamLoader teamLoader;

    public RoutineRemoveFromTeam(User user, String teamName, int waifuNumber, Messageable channel,
                                 PlayerLoader playerLoader, TeamLoader teamLoader) {
        this.user = user;
        this.teamName = teamName;
        this.waifuNumber = waifuNumber;
        this.channel = channel;
        this.playerLoader = playerLoader;
        this.teamLoader = teamLoader;
    }

    @Override
    Answer start(RoutineRunner routineRunner) throws MyOwnException {
        Player player = playerLoader.getPlayerByUser(user);
        Optional<Team> team = player.getTeamByName(teamName);
        if (team.isPresent()) {
            if (waifuNumber >= team.get().getFighters().size()) {
                channel.sendMessage("Die Zahl muss zwischen " + 0 + " und " + (
                        team.get().getFighters().size() - 1) + " sein.");
                return new Answer("Someone tried to remove a Waifu from his Team, but the Number was to large");
            }

            if (team.get().isInDungeon()) {
                channel.sendMessage(player.getNameTag() + ", das Team \"" + team.get().getName() + "\" ist im Moment in einem Dungeon");
                return new Answer("Someone tried to remove a Waifu from his Team, but the team is in a dungeon");
            }

            Fighter fighter = team.get().getFighters().get(waifuNumber);
            team.get().removeWaifu(fighter.getWaifu());
            channel.sendMessage(player.getNameTag() + ", \"" + fighter.getWaifu().getName() + "\" wurde aus deinem Team entfernt");
            playerLoader.savePlayer(player);
            teamLoader.deleteTeamFighter(fighter);

        } else {
            channel.sendMessage(player.getNameTag() + ", konnte Team \"" + teamName + "\" nicht finden.");
        }

        return new Answer("Someone removed a Waifu from his Team");
    }
}
