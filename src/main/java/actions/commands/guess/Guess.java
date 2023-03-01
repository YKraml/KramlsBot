package actions.commands.guess;

import actions.commands.ACommand;
import actions.commands.Answer;
import actions.commands.CommandType;
import exceptions.MyOwnException;
import java.util.List;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import routines.RoutineGuessBuilder;
import waifu.model.Player;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class Guess extends ACommand {

  private final RoutineGuessBuilder routineGuessBuilder;

  public Guess(RoutineGuessBuilder routineGuessBuilder) {
    this.routineGuessBuilder = routineGuessBuilder;
  }

  @Override
  public String getName() {
    return "" + "guess";
  }

  @Override
  public String getDescription() {
    return "Damit kannst du raten, welcher Song spielt.";
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte kein Opening raten.";
  }

  @Override
  public CommandType getCommandType() {
    return CommandType.GUESS;
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }

  @Override
  protected Answer executeCommand(DiscordApi api, Server server, TextChannel channel, User user,
      Player player, List<SlashCommandInteractionOption> arguments) throws MyOwnException {
    String guess = arguments.get(0).getStringValue().get();
    return getRoutineRunner().startRoutine(
        routineGuessBuilder.createRoutineGuess(server, channel, player, guess));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of(SlashCommandOption.create(SlashCommandOptionType.STRING, "Anime",
        "Deine Vermutung, vom welchem Anime der Song kommt.", true));
  }


}
