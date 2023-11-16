package logic.routines;

import domain.Answer;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotFindGroup;
import domain.waifu.Group;
import domain.waifu.Player;
import domain.waifu.Waifu;
import logic.waifu.PlayerLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

public class RoutineAddWaifuToGroup extends Routine {

    private final User user;
    private final String groupName;
    private final int waifuId;
    private final TextChannel channel;
    private final PlayerLoader playerLoader;

    public RoutineAddWaifuToGroup(User user, String groupName, int waifuId, TextChannel channel,
                                  PlayerLoader playerLoader) {
        this.user = user;
        this.groupName = groupName;
        this.waifuId = waifuId;
        this.channel = channel;
        this.playerLoader = playerLoader;
    }

    @Override
    Answer start(RoutineRunner routineRunner) throws MyOwnException {
        Player player = playerLoader.getPlayerByUser(user);
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
