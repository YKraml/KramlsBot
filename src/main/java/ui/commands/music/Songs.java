package ui.commands.music;

import com.google.inject.Inject;
import domain.Answer;
import domain.exceptions.MyOwnException;
import java.util.List;
import logic.routines.RoutineShowSongsBuilder;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import ui.commands.ACommand;

public class Songs extends ACommand {

  private final RoutineShowSongsBuilder routineShowSongsBuilder;

  @Inject
  public Songs(RoutineShowSongsBuilder routineShowSongsBuilder) {
    this.routineShowSongsBuilder = routineShowSongsBuilder;
  }

  @Override
  public String getName() {
    return "songs";
  }

  @Override
  public String getDescription() {
    return "Zeigt deine Lieblingssongs an";
  }

  @Override
  protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
      List<SlashCommandInteractionOption> arguments) throws MyOwnException {
    return getRoutineRunner().start(
        routineShowSongsBuilder.createRoutineShowSongs(server, channel, user));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of();
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte deine gespeicherten Songs nicht anzeigen.";
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }
}
