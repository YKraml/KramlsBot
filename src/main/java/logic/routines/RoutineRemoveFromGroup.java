package logic.routines;

import domain.Answer;
import domain.GroupLoader;
import domain.PlayerLoader;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotFindGroup;
import domain.waifu.Group;
import domain.waifu.Player;
import domain.waifu.Waifu;
import logic.messages.MessageSender;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

import java.text.MessageFormat;

public class RoutineRemoveFromGroup extends Routine {

    private final User user;
    private final String groupName;
    private final int waifuNumber;
    private final TextChannel channel;
    private final GroupLoader groupLoader;
    private final MessageSender messageSender;
    private final PlayerLoader playerLoader;

    public RoutineRemoveFromGroup(User user, String groupName, int waifuNumber, TextChannel channel, GroupLoader groupLoader, MessageSender messageSender, PlayerLoader playerLoader) {
        this.user = user;
        this.groupName = groupName;
        this.waifuNumber = waifuNumber;
        this.channel = channel;
        this.groupLoader = groupLoader;
        this.messageSender = messageSender;
        this.playerLoader = playerLoader;
    }

    @Override
    Answer start(RoutineRunner routineRunner) throws MyOwnException {
        Player player = playerLoader.getPlayerByUser(user);
        Group group = player.getGroupByName(groupName).orElseThrow(() -> new MyOwnException(new CouldNotFindGroup(groupName), null));

        boolean removed = false;
        int i = 0;
        for (Waifu waifu : group.getWaifuList()) {

            if (i == waifuNumber) {
                removed = group.removeWaifu(waifu);
                channel.sendMessage(MessageFormat.format("Waifu \"{0}\" aus der Gruppe \"{1}\" entfernt.", waifu.getName(), group.getName()));
                groupLoader.deleteWaifuFromGroup(group, waifu);
                break;
            }

            i++;
        }

        if (!removed) {
            messageSender.sendWaifuNotFound(channel, waifuNumber);
        }

        return new Answer("Someone deleted a Waifu from his a list.");
    }
}
