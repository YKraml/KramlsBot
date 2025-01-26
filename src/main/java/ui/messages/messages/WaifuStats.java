package ui.messages.messages;

import domain.Emojis;
import domain.PlayerLoader;
import domain.WaifuLoader;
import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.Waifu;
import logic.messages.MessageSender;
import logic.waifu.JikanFetcher;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.waifu.WaifuEmbed;
import ui.messages.MyMessageAbs;
import ui.reaction.WaifuEditListener;
import util.Terminal;

public class WaifuStats extends MyMessageAbs {

  private final Player player;
  private final Waifu waifu;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;
  private final MessageSender messageSender;
  private final Terminal terminal;

  public WaifuStats(Waifu waifu, Player player, PlayerLoader playerLoader, WaifuLoader waifuLoader,
      JikanFetcher jikanFetcher, MessageSender messageSender, Terminal terminal) {
    this.player = player;
    this.waifu = waifu;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
    this.messageSender = messageSender;
    this.terminal = terminal;
  }

  @Override
  public void startRoutine(Message message) throws MyOwnException {
    message.addReaction(Emojis.COOKIE.getEmoji());
    message.addReaction(Emojis.CUPCAKE.getEmoji());
    message.addReaction(Emojis.STAR2.getEmoji());
    message.addReaction(Emojis.HAT.getEmoji());
    message.addReaction(Emojis.X.getEmoji());
    message.addReactionAddListener(new WaifuEditListener(waifu, player, playerLoader, waifuLoader,
        jikanFetcher, messageSender, terminal));
  }

  @Override
  public EmbedBuilder getContent() throws MyOwnException {
    return new WaifuEmbed(waifu);
  }
}
