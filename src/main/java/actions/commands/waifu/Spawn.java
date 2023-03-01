package actions.commands.waifu;

import actions.commands.ACommand;
import actions.commands.Answer;
import actions.commands.CommandType;
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
import waifu.model.Player;

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
  protected Answer executeCommand(DiscordApi api, Server server, TextChannel channel, User user,
      Player player, List<SlashCommandInteractionOption> arguments) throws MyOwnException {

    return getRoutineRunner().startRoutine(
        routineSpawnWaifuCommandBuilder.createRoutineSpawnWaifuCommand(channel, player));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return null;
  }


  @Override
  protected String getErrorMessage() {
    return "Konnte keine neue Waifu spawnen.";
  }

  @Override
  public CommandType getCommandType() {
    return CommandType.WAIFU;
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }
}
