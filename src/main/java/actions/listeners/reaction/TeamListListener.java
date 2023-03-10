package actions.listeners.reaction;

import embeds.dungeon.TeamsListEmbed;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.MessageSenderImpl;
import messages.messages.TeamOverview;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import waifu.JikanFetcher;
import waifu.loader.DungeonLoader;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Player;
import waifu.model.dungeon.Team;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;

public class TeamListListener extends MyAbstractListListener<Team> {

    private final Player player;
    private final DungeonLoader dungeonLoader;
    private final PlayerLoader playerLoader   ;
    private final MessageSender messageSender;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;

    public TeamListListener(Player player, DungeonLoader dungeonLoader, PlayerLoader playerLoader,
        MessageSender messageSender, WaifuLoader waifuLoader, JikanFetcher jikanFetcher) {
        super(player.getTeamList());
        this.player = player;
        this.dungeonLoader = dungeonLoader;
        this.playerLoader = playerLoader;
        this.messageSender = messageSender;
        this.waifuLoader = waifuLoader;
        this.jikanFetcher = jikanFetcher;
    }

    @Override
    protected void updateMessage(Message message, int page) {
        message.edit(new TeamsListEmbed(player, page));
    }

    @Override
    protected void reactToCountEmoji(TextChannel textChannel, int listPosition, Server server,
        User user) throws MyOwnException {
        Team team = player.getTeamList().get(listPosition);
      MessageSenderImpl result;
      synchronized (MessageSenderImpl.class) {
        result = new MessageSenderImpl();
      }
      result.send(new TeamOverview(team, dungeonLoader, playerLoader, messageSender, waifuLoader,
          jikanFetcher), textChannel);
    }

    @Override
    protected void reactToTooHighCountEmoji(TextChannel textChannel, int listPosition) {
        textChannel.sendMessage(
            player.getNameTag() + ", konnte Team \"" + listPosition + "\" nicht finden.");
    }
}
