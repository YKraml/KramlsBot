package ui.reaction;

import domain.exceptions.MyOwnException;
import domain.waifu.dungeon.Dungeon;
import domain.waifu.dungeon.Team;
import logic.MessageSender;
import logic.waifu.PlayerLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import ui.messages.messages.DungeonNotFound;
import ui.messages.messages.TeamEnteredDungeon;

import java.util.List;

public class DungeonListListener extends MyAbstractListListener<Dungeon> {

    private final List<Dungeon> dungeonList;
    private final Team team;
    private final PlayerLoader playerLoader;
    private final MessageSender messageSender;

    public DungeonListListener(List<Dungeon> dungeonList, Team team, PlayerLoader playerLoader,
                               MessageSender messageSender) {
        super(dungeonList);
        this.dungeonList = dungeonList;
        this.team = team;
        this.playerLoader = playerLoader;
        this.messageSender = messageSender;
    }

    @Override
    protected void updateMessage(Message message, int page) {
        //Just ignore.
    }

    @Override
    protected void reactToCountEmoji(Server server, TextChannel textChannel, User user,
                                     int listPosition)
            throws MyOwnException {
        Dungeon dungeon = dungeonList.get(listPosition);
        team.entersDungeon(dungeon, 1);
        playerLoader.savePlayer(team.getPlayer());
        messageSender.send(new TeamEnteredDungeon(team, dungeon), textChannel);
    }

    @Override
    protected void reactToTooHighCountEmoji(TextChannel textChannel, int emojiNumber)
            throws MyOwnException {
        messageSender.send(new DungeonNotFound(emojiNumber), textChannel);
    }
}
