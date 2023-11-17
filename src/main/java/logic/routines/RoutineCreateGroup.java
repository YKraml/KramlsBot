package logic.routines;

import domain.Answer;
import domain.exceptions.MyOwnException;
import domain.waifu.Group;
import domain.waifu.Player;
import logic.MessageSender;
import logic.waifu.PlayerLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

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
        messageSender.sendGroupCreated(channel, groupName);
        playerLoader.savePlayer(player);

        return new Answer("Someone created a group");
    }
}
