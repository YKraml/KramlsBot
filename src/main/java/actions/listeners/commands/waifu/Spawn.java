package actions.listeners.commands.waifu;

import actions.listeners.commands.ACommand;
import actions.listeners.commands.Answer;
import com.google.inject.Inject;
import exceptions.MyOwnException;
import java.util.List;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import routines.RoutineSpawnWaifuCommandBuilder;

public class Spawn extends ACommand {

  private final RoutineSpawnWaifuCommandBuilder routineSpawnWaifuCommandBuilder;

  @Inject
  public Spawn(RoutineSpawnWaifuCommandBuilder routineSpawnWaifuCommandBuilder) {
    super();
    this.routineSpawnWaifuCommandBuilder = routineSpawnWaifuCommandBuilder;
  }

  @Override
  public String getName() {
    return "" + "spawn";
  }

  @Override
  public String getDescription() {
    return "Du erhaelst eine Waifu fuer 1000 Euro.";
  }

  @Override
  protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
      List<SlashCommandInteractionOption> arguments) throws MyOwnException {

    return getRoutineRunner().startRoutine(
        routineSpawnWaifuCommandBuilder.createRoutineSpawnWaifuCommand(channel, user));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of();
  }


  @Override
  protected String getErrorMessage() {
    return "Konnte keine neue Waifu spawnen.";
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }
}
