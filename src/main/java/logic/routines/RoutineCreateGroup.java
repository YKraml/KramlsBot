package logic.routines;

import domain.exceptions.MyOwnException;
import domain.waifu.Group;
import domain.waifu.Player;
import logic.waifu.PlayerLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;
import domain.Answer;
import ui.messages.MessageSender;
import ui.messages.messages.GroupCreated;

import java.util.UUID;

public class RoutineCreateGroup extends Routine {

    private final String groupName;
    private final User user;
    private final PlayerLoader playerLoader;
    private final MessageSender messageSender;
    private final TextChannel channel;

    public RoutineCreateGroup(String groupName, User user, PlayerLoader playerLoader,
                              MessageSender messageSender, TextChannel channel) {
        this.groupName = groupName;
        this.user = user;
        this.playerLoader = playerLoader;
        this.messageSender = messageSender;
        this.channel = channel;
    }

    @Override
    Answer start(RoutineRunner routineRunner) throws MyOwnException {
        Player player = playerLoader.getPlayerByUser(user);
        String id = UUID.randomUUID().toString();
        Group group = new Group(id, groupName);

        player.addGroup(group);
        messageSender.send(new GroupCreated(groupName), channel);
        playerLoader.savePlayer(player);

        return new Answer("Someone created a group");
    }
}
