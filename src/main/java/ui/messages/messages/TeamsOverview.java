package ui.messages.messages;

import ui.reaction.TeamListListener;
import ui.embeds.dungeon.TeamsListEmbed;
import domain.exceptions.MyOwnException;
import ui.messages.MessageSender;
import ui.messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import logic.waifu.JikanFetcher;
import logic.waifu.DungeonLoader;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;
import domain.waifu.Player;

public class TeamsOverview extends MyMessage {

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
  protected void startRoutine(Message message) throws MyOwnException {
    Player player = playerLoader.getPlayerByUser(user);
    addCountEmojis(message, player.getTeamList().size());
    message.addReactionAddListener(new TeamListListener(player, dungeonLoader, playerLoader,
        messageSender, waifuLoader, jikanFetcher));
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    Player player = playerLoader.getPlayerByUser(user);
    return new TeamsListEmbed(player, 0);
  }
}
