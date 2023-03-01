package actions.commands.gambling;

import actions.commands.Answer;
import actions.commands.ACommand;
import actions.commands.CommandType;
import exceptions.MyOwnException;
import java.util.List;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import routines.RoutineDoubleOrNothingWithParameterBuilder;
import waifu.model.Player;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class DoubleOrNothingWithParameter extends ACommand {

  private final RoutineDoubleOrNothingWithParameterBuilder routineDoubleOrNothingWithParameterBuilder;

  public DoubleOrNothingWithParameter(
      RoutineDoubleOrNothingWithParameterBuilder routineDoubleOrNothingWithParameterBuilder) {
    super();
    this.routineDoubleOrNothingWithParameterBuilder = routineDoubleOrNothingWithParameterBuilder;
  }

  @Override
  public String getName() {
    return "" + "gambling-doppel";
  }

  @Override
  public String getDescription() {
    return "Entweder du verdoppelst das angegebene Geld oder du gewinnst nichts.";
  }

  @Override
  protected Answer executeCommand(DiscordApi api, Server server, TextChannel channel, User user,
      Player player, List<SlashCommandInteractionOption> arguments)
      throws MyOwnException {
    int bettedMoney = Math.toIntExact(arguments.get(0).getLongValue().get());

    return getRoutineRunner().startRoutine(
        routineDoubleOrNothingWithParameterBuilder.createRoutineDoubleOrNothingWithParameter(player,
            bettedMoney, channel));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of(
        SlashCommandOption.create(SlashCommandOptionType.LONG, "Geld", "Menge an Geld", true));
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
