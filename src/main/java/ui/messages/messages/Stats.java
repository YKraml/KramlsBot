package ui.messages.messages;

import ui.embeds.StatEmbed;
import domain.exceptions.MyOwnException;
import ui.messages.MyMessage;
import logic.waifu.PlayerLoader;
import domain.waifu.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class Stats extends MyMessage {


  private final User user;
  private final Server server;
  private final PlayerLoader playerLoader;

  public Stats(User user, Server server, PlayerLoader playerLoader) {
    this.user = user;
    this.server = server;
    this.playerLoader = playerLoader;
  }


  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    Player player = playerLoader.getPlayerByUser(user);
    return new StatEmbed(player, user, server);
  }
}
