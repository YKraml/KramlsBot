package actions.listeners.join;

import de.kraml.Terminal;
import discord.RoleChecker;
import exceptions.MyOwnException;
import exceptions.messages.CouldNotGetJoinedPlayer;
import org.javacord.api.event.channel.server.voice.ServerVoiceChannelMemberJoinEvent;
import org.javacord.api.listener.channel.server.voice.ServerVoiceChannelMemberJoinListener;
import waifu.loader.PlayerLoader;
import waifu.model.Player;

public class JoinListener implements ServerVoiceChannelMemberJoinListener {


    private final PlayerLoader playerLoader;
    private final RoleChecker roleChecker;

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
            MyOwnException exception = new MyOwnException(new CouldNotGetJoinedPlayer(event.getUser().getIdAsString()), e);
            Terminal.printError(exception);
        }


    }
}
