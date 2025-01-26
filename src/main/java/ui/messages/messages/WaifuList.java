package ui.messages.messages;

import domain.Emojis;
import domain.PlayerLoader;
import domain.WaifuLoader;
import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.waifu.WaifuListEmbed;
import ui.messages.MyMessageAbs;
import ui.reaction.WaifuListListener;
import util.Terminal;

public class WaifuList extends MyMessageAbs {

  private final Player player;
  private final MessageSender messageSender;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final Terminal terminal;

  public WaifuList(Player player, MessageSender messageSender, PlayerLoader playerLoader,
      WaifuLoader waifuLoader, JikanFetcher jikanFetcher, Terminal terminal) {
    this.player = player;
    this.messageSender = messageSender;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.terminal = terminal;
  }

  @Override
  public void startRoutine(Message message) throws MyOwnException {
    this.addCountEmojis(message, player.getWaifuList().size());
    message.addReaction(Emojis.ARROWS_COUNTERCLOCKWISE.getEmoji());
    message.addReaction(Emojis.COOKIE.getEmoji());
    message.addReaction(Emojis.STAR2.getEmoji());
    message.addReaction(Emojis.ABC.getEmoji());
    message.addReactionAddListener(new WaifuListListener(player, messageSender, playerLoader,
        waifuLoader, jikanFetcher, terminal));
  }

  @Override
  public EmbedBuilder getContent() throws MyOwnException {
    return new WaifuListEmbed(player, 0);
  }
}
