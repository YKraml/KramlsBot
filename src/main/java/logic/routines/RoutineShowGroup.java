package logic.routines;

import domain.Answer;
import domain.exceptions.MyOwnException;
import domain.waifu.Group;
import domain.waifu.Player;
import logic.MessageSender;
import logic.waifu.JikanFetcher;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;
import org.javacord.api.entity.channel.TextChannel;
import ui.messages.messages.GroupOverview;

import java.util.Optional;

public class RoutineShowGroup extends Routine {

    private final Player player;
    private final String groupName;
    private final PlayerLoader playerLoader;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;
    private final MessageSender messageSender;
    private final TextChannel channel;

    public RoutineShowGroup(Player player, String groupName, PlayerLoader playerLoader,
                            WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender,
                            TextChannel channel) {
        this.player = player;
        this.groupName = groupName;
        this.playerLoader = playerLoader;
        this.waifuLoader = waifuLoader;
        this.jikanFetcher = jikanFetcher;
        this.messageSender = messageSender;
        this.channel = channel;
    }

    @Override
    Answer start(RoutineRunner routineRunner) throws MyOwnException {
        Optional<Group> group = player.getGroupByName(groupName);
        if (group.isPresent()) {
            messageSender.send(new GroupOverview(group.get(), player, playerLoader, waifuLoader, jikanFetcher,
                    messageSender), channel);
        } else {
            channel.sendMessage("Konnte die Gruppe \"" + groupName + "\" nicht finden.");
        }

        return new Answer("Someone showed a group");
    }
}
