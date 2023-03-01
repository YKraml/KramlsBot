package actions.commands.music;

import actions.commands.Answer;
import actions.commands.ACommand;
import actions.commands.CommandType;
import com.google.inject.Inject;
import exceptions.MyOwnException;
import java.util.List;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import routines.RoutineShowSongsBuilder;
import waifu.model.Player;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class Songs extends ACommand {

  private final RoutineShowSongsBuilder routineShowSongsBuilder;

  @Inject
  public Songs(RoutineShowSongsBuilder routineShowSongsBuilder) {
    super();
    this.routineShowSongsBuilder = routineShowSongsBuilder;
  }

  @Override
  public String getName() {
    return "" + "songs";
  }

  @Override
  public String getDescription() {
    return "Zeigt deine Lieblingssongs an";
  }

  @Override
  protected Answer executeCommand(DiscordApi api, Server server, TextChannel channel, User user,
      Player player, List<SlashCommandInteractionOption> arguments) throws MyOwnException {
    return getRoutineRunner().startRoutine(
        routineShowSongsBuilder.createRoutineShowSongs(server, channel, user, player));
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
  public CommandType getCommandType() {
    return CommandType.MUSIC;
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }
}
