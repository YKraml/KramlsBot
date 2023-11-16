package ui.join;

import com.google.inject.Inject;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotGetJoinedPlayer;
import domain.waifu.Player;
import logic.waifu.PlayerLoader;
import org.javacord.api.event.channel.server.voice.ServerVoiceChannelMemberJoinEvent;
import org.javacord.api.listener.channel.server.voice.ServerVoiceChannelMemberJoinListener;
import logic.RoleChecker;
import ui.Terminal;

public class JoinListener implements ServerVoiceChannelMemberJoinListener {


    private final PlayerLoader playerLoader;
    private final RoleChecker roleChecker;

    @Inject
    public JoinListener(PlayerLoader playerLoader, RoleChecker roleChecker) {
        this.playerLoader = playerLoader;
        this.roleChecker = roleChecker;
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
            Terminal.printError(
                    new MyOwnException(new CouldNotGetJoinedPlayer(event.getUser().getIdAsString()), e));
        }


    }
}
