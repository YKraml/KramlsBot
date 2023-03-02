package actions.listeners.reaction;

import discord.ChannelFinder;
import embeds.music.QueueEmbed;
import exceptions.MyOwnException;
import waifu.loader.PlayerLoader;
import music.audio.MusicPlayerManager;
import music.audio.Queue;
import discord.Emojis;
import waifu.model.Player;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class QueueListener extends MyAbstractReactionListener {

  private final Queue queue;
  private final PlayerLoader playerLoader;
  private final MusicPlayerManager musicPlayerManager;

  public QueueListener(Queue queue, PlayerLoader playerLoader,
      MusicPlayerManager musicPlayerManager) {
    this.queue = queue;
    this.playerLoader = playerLoader;
    this.musicPlayerManager = musicPlayerManager;
    ;
  }

  @Override
  protected void startRoutine(DiscordApi discordApi, Server server, TextChannel textChannel,
      Message message, User user, Emoji emoji) throws MyOwnException {

    ServerVoiceChannel serverVoiceChannel = new ChannelFinder(
        discordApi).getServerVoiceChannelByMember(server, user);

    if (emoji.equalsEmoji(Emojis.FAST_FORWARD.getEmoji())) {
      musicPlayerManager.playNextSong(serverVoiceChannel, textChannel);
    }

    if (emoji.equalsEmoji(Emojis.REWIND.getEmoji())) {
      musicPlayerManager.playPreviousSong(serverVoiceChannel, textChannel);
    }

    if (emoji.equalsEmoji(Emojis.ARROWS_COUNTERCLOCKWISE.getEmoji())) {
      musicPlayerManager.playCurrentSong(serverVoiceChannel, textChannel);

    }

    if (emoji.equalsEmoji(Emojis.STAR.getEmoji())) {
      Player playerThatLiked = playerLoader.getPlayerByUser(user);
      playerThatLiked.addQueueElement(queue.getCurrentElement());
      playerLoader.savePlayer(playerThatLiked);
      textChannel.sendMessage("\"" + queue.getCurrentElement().getName()
          + "\" deinen Favoriten hinzugefuegt. Nutze 'songs' um deine Favoriten zu sehen.");
    }

    message.edit(new QueueEmbed(queue));

  }
}
