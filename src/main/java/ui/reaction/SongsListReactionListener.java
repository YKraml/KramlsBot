package ui.reaction;

import domain.PlayerLoader;
import domain.exceptions.MyOwnException;
import domain.queue.QueueElement;
import domain.waifu.Player;
import logic.MusicPlayerManager;
import logic.discord.ChannelFinder;
import logic.messages.MessageSender;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import ui.embeds.music.LikedSongsEmbed;
import ui.messages.messages.SongAdded;

public class SongsListReactionListener extends MyAbstractListListener<QueueElement> {

  private final Server server;
  private final Player player;
  private final User user;
  private final MusicPlayerManager musicPlayerManager;
  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;

  public SongsListReactionListener(Player player, Server server, User user,
      MusicPlayerManager musicPlayerManager, PlayerLoader playerLoader,
      MessageSender messageSender) {
    super(player.getLikedSongs());
    this.server = server;
    this.player = player;
    this.user = user;
    this.musicPlayerManager = musicPlayerManager;
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
  }

  @Override
  protected void updateMessage(Message message, int page) {
    message.edit(new LikedSongsEmbed(player, page));
  }

  @Override
  protected void reactToCountEmoji(Server server, TextChannel textChannel, User user,
      int listPosition) throws MyOwnException {

    ChannelFinder channelFinder = new ChannelFinder(textChannel.getApi());
    ServerVoiceChannel voiceChannel = channelFinder.getServerVoiceChannelByMember(this.server,
        this.user);

    musicPlayerManager.addToQueue(
        this.server, voiceChannel, textChannel, player.getLikedSongs().get(listPosition));
    musicPlayerManager.startPlaying(
        new ChannelFinder(textChannel.getApi()).getServerVoiceChannelByMember(
            this.server, this.user), textChannel);
    messageSender.send(new SongAdded(player.getLikedSongs().get(listPosition),
        musicPlayerManager, playerLoader, messageSender), textChannel);
  }

  @Override
  protected void reactToTooHighCountEmoji(TextChannel textChannel, int listPosition) {
    textChannel.sendMessage("Du hast keinen Song an dieser Position");
  }
}
