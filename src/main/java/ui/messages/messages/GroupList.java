package ui.messages.messages;

import domain.PlayerLoader;
import domain.WaifuLoader;
import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.group.GroupsListEmbed;
import ui.messages.MyMessageAbs;
import ui.reaction.GroupListListener;
import util.Terminal;

public class GroupList extends MyMessageAbs {

  private final Player player;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;
  private final Terminal terminal;

  public GroupList(Player player, PlayerLoader playerLoader, WaifuLoader waifuLoader,
      JikanFetcher jikanFetcher, MessageSender messageSender, Terminal terminal) {
    this.player = player;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
    this.terminal = terminal;
  }

  @Override
  public void startRoutine(Message message) throws MyOwnException {
    this.addCountEmojis(message, player.getGroupList().size());
    message.addReactionAddListener(new GroupListListener(player, playerLoader, waifuLoader,
        jikanFetcher, messageSender, terminal));
  }

  @Override
  public EmbedBuilder getContent() throws MyOwnException {
    return new GroupsListEmbed(player, 0);
  }
}
