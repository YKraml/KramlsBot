package ui.messages.messages;

import ui.reaction.WaifuListListener;
import util.Emojis;
import ui.embeds.waifu.WaifuListEmbed;
import domain.exceptions.MyOwnException;
import ui.messages.MessageSender;
import ui.messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import logic.waifu.JikanFetcher;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;
import domain.waifu.Player;

public class WaifuList extends MyMessage {

  private final Player player;
  private final MessageSender messageSender;
  private final PlayerLoader playerLoader;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;

  public WaifuList(Player player, MessageSender messageSender, PlayerLoader playerLoader,
      WaifuLoader waifuLoader, JikanFetcher jikanFetcher) {
    this.player = player;
    this.messageSender = messageSender;
    this.playerLoader = playerLoader;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    this.addCountEmojis(message, player.getWaifuList().size());
    message.addReaction(Emojis.ARROWS_COUNTERCLOCKWISE.getEmoji());
    message.addReaction(Emojis.COOKIE.getEmoji());
    message.addReaction(Emojis.STAR2.getEmoji());
    message.addReaction(Emojis.ABC.getEmoji());
    message.addReactionAddListener(new WaifuListListener(player, messageSender, playerLoader,
        waifuLoader, jikanFetcher));
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return new WaifuListEmbed(player, 0);
  }
}
