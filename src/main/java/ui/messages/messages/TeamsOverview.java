package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import logic.messages.MessageSender;
import domain.DungeonLoader;
import logic.waifu.JikanFetcher;
import domain.PlayerLoader;
import domain.WaifuLoader;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import ui.embeds.dungeon.TeamsListEmbed;
import ui.messages.MyMessageAbs;
import ui.reaction.TeamListListener;

public class TeamsOverview extends MyMessageAbs {

    private final User user;
    private final DungeonLoader dungeonLoader;
    private final PlayerLoader playerLoader;
    private final MessageSender messageSender;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;

    public TeamsOverview(User user, DungeonLoader dungeonLoader, PlayerLoader playerLoader,
                         MessageSender messageSender, WaifuLoader waifuLoader, JikanFetcher jikanFetcher) {
        this.user = user;
        this.dungeonLoader = dungeonLoader;
        this.playerLoader = playerLoader;
        this.messageSender = messageSender;
        this.waifuLoader = waifuLoader;
        this.jikanFetcher = jikanFetcher;
    }

    @Override
    public void startRoutine(Message message) throws MyOwnException {
        Player player = playerLoader.getPlayerByUser(user);
        addCountEmojis(message, player.getTeamList().size());
        message.addReactionAddListener(new TeamListListener(player, dungeonLoader, playerLoader,
                messageSender, waifuLoader, jikanFetcher));
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        Player player = playerLoader.getPlayerByUser(user);
        return new TeamsListEmbed(player, 0);
    }
}
