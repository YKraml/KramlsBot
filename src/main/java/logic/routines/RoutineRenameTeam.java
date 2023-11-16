package logic.routines;

import domain.Answer;
import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.dungeon.Team;
import logic.waifu.PlayerLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import ui.messages.MessageSenderImpl;
import ui.messages.messages.TeamRenamedMessage;

import java.util.Optional;

public class RoutineRenameTeam extends Routine {

    private final User user;
    private final PlayerLoader playerLoader;
    private final TextChannel channel;
    private final String newName;
    private final String oldName;

    public RoutineRenameTeam(User user, PlayerLoader playerLoader, TextChannel channel,
                             String newName, String oldName) {
        this.user = user;
        this.playerLoader = playerLoader;
        this.channel = channel;
        this.newName = newName;
        this.oldName = oldName;
    }

    @Override
    Answer start(RoutineRunner routineRunner) throws MyOwnException {
        Player player = playerLoader.getPlayerByUser(user);
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
