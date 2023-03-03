package actions.listeners.commands.music;

import actions.listeners.commands.ACommand;
import actions.listeners.commands.Answer;
import actions.listeners.commands.CommandType;
import com.google.inject.Inject;
import exceptions.MyOwnException;
import java.util.List;
import messages.MessageSender;
import music.audio.MusicPlayerManager;
import music.queue.Queue;
import messages.messages.SongQueueMessage;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import waifu.loader.PlayerLoader;
import waifu.model.Player;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class ShowQueue extends ACommand {

  private final MusicPlayerManager musicPlayerManager;
  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;

  @Inject
  public ShowQueue(MusicPlayerManager musicPlayerManager, PlayerLoader playerLoader,
      MessageSender messageSender) {
    super();
    this.musicPlayerManager = musicPlayerManager;
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
  }

  @Override
  public String getName() {
    return "" + "queue";
  }

  @Override
  public String getDescription() {
    return "Zeigt die Songwarteschlange an.";
  }

  @Override
  protected Answer executeCommand(DiscordApi api, Server server, TextChannel channel, User user,
      Player player, List<SlashCommandInteractionOption> arguments) throws MyOwnException {

    Queue queue = musicPlayerManager.getQueueByServer(server);
    messageSender.send(new SongQueueMessage(queue, musicPlayerManager, playerLoader), channel);

    return new Answer("Someone looked at the music queue");
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of();
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte die Queue nicht anzeigen.";
  }

  @Override
  public CommandType getCommandType() {
    return CommandType.MUSIC;
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }
}