package ui.reaction;

import domain.DungeonLoader;
import domain.PlayerLoader;
import domain.WaifuLoader;
import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.dungeon.Team;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import ui.embeds.dungeon.TeamsListEmbed;
import ui.messages.messages.TeamOverview;
import util.Terminal;

public class TeamListListener extends MyAbstractListListener<Team> {

  private final Player player;
  private final DungeonLoader dungeonLoader;
  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final Terminal terminal;

  public TeamListListener(Player player, DungeonLoader dungeonLoader, PlayerLoader playerLoader,
      MessageSender messageSender, WaifuLoader waifuLoader, JikanFetcher jikanFetcher,
      Terminal terminal) {
    super(player.getTeamList());
    this.player = player;
    this.dungeonLoader = dungeonLoader;
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.terminal = terminal;
  }

  @Override
  protected void updateMessage(Message message, int page) {
    message.edit(new TeamsListEmbed(player, page));
  }

  @Override
  protected void reactToCountEmoji(Server server, TextChannel textChannel, User user,
      int listPosition) throws MyOwnException {
    Team team = player.getTeamList().get(listPosition);
    messageSender.send(
        new TeamOverview(team, dungeonLoader, playerLoader, messageSender, waifuLoader,
            jikanFetcher, terminal), textChannel);
  }

  @Override
  protected void reactToTooHighCountEmoji(TextChannel textChannel, int listPosition) {
    textChannel.sendMessage(
        player.getNameTag() + ", konnte Team \"" + listPosition + "\" nicht finden.");
  }
}
