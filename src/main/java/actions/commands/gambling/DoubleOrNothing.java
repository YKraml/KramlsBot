package actions.commands.gambling;

import actions.commands.Answer;
import actions.commands.ACommand;
import actions.commands.CommandType;
import exceptions.MyOwnException;
import java.util.List;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import routines.RoutineDoubleOrNothingBuilder;
import waifu.model.Player;

public class DoubleOrNothing extends ACommand {

  private final RoutineDoubleOrNothingBuilder routineDoubleOrNothingBuilder;

  public DoubleOrNothing(RoutineDoubleOrNothingBuilder routineDoubleOrNothingBuilder) {
    super();
    this.routineDoubleOrNothingBuilder = routineDoubleOrNothingBuilder;
  }

  @Override
  public String getName() {
    return "" + "gambling-alles-oder-nichts";
  }

  @Override
  public String getDescription() {
    return "Verdoppelt dein Geld oder du verlierst alles.";
  }

  @Override
  protected Answer executeCommand(DiscordApi api, Server server, TextChannel channel, User user,
      Player player, List<SlashCommandInteractionOption> arguments) throws MyOwnException {
    return getRoutineRunner().startRoutine(
        routineDoubleOrNothingBuilder.createRoutineDoubleOrNothing(player, channel));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of();
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte das Glücksspiel nicht ausführen.";
  }

  @Override
  public CommandType getCommandType() {
    return CommandType.GAMBLING;
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }

}
