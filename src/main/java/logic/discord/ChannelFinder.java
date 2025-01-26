package logic.discord;

import com.google.inject.Inject;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotFindVoiceChannel;
import java.util.Optional;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class ChannelFinder {

  private final DiscordApi api;

  @Inject
  public ChannelFinder(DiscordApi api) {
    this.api = api;
  }

  public ServerVoiceChannel getServerVoiceChannelByMember(Server server, User user)
      throws MyOwnException {

    for (ServerVoiceChannel voiceChannel : server.getVoiceChannels()) {
      if (voiceChannel.isConnected(user)) {
        return voiceChannel;
      }
    }

    throw new MyOwnException(new CouldNotFindVoiceChannel(server.getName(), user.getName()), null);
  }

  public Optional<TextChannel> getTextChannelById(String id) {
    return api.getTextChannelById(id);
  }

}
