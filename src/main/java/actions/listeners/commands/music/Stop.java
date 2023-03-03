package actions.listeners.commands.music;

import actions.listeners.commands.ACommand;
import actions.listeners.commands.Answer;
import com.google.inject.Inject;
import java.util.List;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import music.audio.MusicPlayerManager;
import music.guess.GuessingGameManager;

public class Stop extends ACommand {

  private final MusicPlayerManager musicPlayerManager;
  private final GuessingGameManager guessingGameManager;

  @Inject
  public Stop(MusicPlayerManager musicPlayerManager, GuessingGameManager guessingGameManager) {
    super();
    this.musicPlayerManager = musicPlayerManager;
    this.guessingGameManager = guessingGameManager;
  }

  @Override
  public String getName() {
    return "" + "stop";
  }

  @Override
  public String getDescription() {
    return "Laesst den Bot disconnecten";
  }

  @Override
  protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
      List<SlashCommandInteractionOption> arguments) {

    musicPlayerManager.stopPlaying(server);
    guessingGameManager.removeGuessGame(server.getIdAsString());

    return new Answer("Bot stopped playing Song");
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of();
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte die Musik nicht stoppen.";
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }


}
