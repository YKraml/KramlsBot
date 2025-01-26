package ui.commands.guess;

import com.google.inject.Inject;
import domain.Answer;
import domain.exceptions.MyOwnException;
import java.util.List;
import logic.routines.RoutineGuessBuilder;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import ui.commands.ACommand;

public class Guess extends ACommand {

  private final RoutineGuessBuilder routineGuessBuilder;

  @Inject
  public Guess(RoutineGuessBuilder routineGuessBuilder) {
    this.routineGuessBuilder = routineGuessBuilder;
  }

  @Override
  public String getName() {
    return "guess";
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
  protected boolean isForAdmins() {
    return false;
  }

  @Override
  protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
      List<SlashCommandInteractionOption> arguments) throws MyOwnException {
    String guess = arguments.get(0).getStringValue().get();

    return getRoutineRunner().start(
        routineGuessBuilder.createRoutineGuess(server, channel, user, guess));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of(SlashCommandOption.create(SlashCommandOptionType.STRING, "Anime",
        "Deine Vermutung, vom welchem Anime der Song kommt.", true));
  }


}
