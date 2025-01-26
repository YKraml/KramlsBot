package ui.join;

import com.google.inject.Inject;
import domain.PlayerLoader;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotGetJoinedPlayer;
import domain.waifu.Player;
import logic.discord.RoleChecker;
import org.javacord.api.event.channel.server.voice.ServerVoiceChannelMemberJoinEvent;
import org.javacord.api.listener.channel.server.voice.ServerVoiceChannelMemberJoinListener;
import util.Terminal;

public class JoinListener implements ServerVoiceChannelMemberJoinListener {


  private final PlayerLoader playerLoader;
  private final RoleChecker roleChecker;
  private final Terminal terminal;

  @Inject
  public JoinListener(PlayerLoader playerLoader, RoleChecker roleChecker, Terminal terminal) {
    this.playerLoader = playerLoader;
    this.roleChecker = roleChecker;
    this.terminal = terminal;
  }

  @Override
  public void onServerVoiceChannelMemberJoin(ServerVoiceChannelMemberJoinEvent event) {

    if (event.getUser().isBot()) {
      return;
    }

    try {
      Player player = playerLoader.getPlayerById(event.getUser().getIdAsString());
      player.setName(event.getUser().getDisplayName(event.getServer()));
      playerLoader.savePlayer(player);
      roleChecker.updateRoles(event.getServer(), event.getUser());
    } catch (MyOwnException e) {
      terminal.printError(
          new MyOwnException(new CouldNotGetJoinedPlayer(event.getUser().getIdAsString()), e));
    }


  }
}
