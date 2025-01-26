package ui.commands.guess;

import com.google.inject.Inject;
import domain.Answer;
import domain.exceptions.MyOwnException;
import java.util.List;
import java.util.Random;
import logic.routines.RoutineStartGuessingGameBuilder;
import logic.timer.RevealTimerBuilder;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import ui.commands.ACommand;

public class StartGuessingGame extends ACommand {

  private final RoutineStartGuessingGameBuilder builder;
  private final RevealTimerBuilder revealTimerbuilder;

  @Inject
  public StartGuessingGame(RoutineStartGuessingGameBuilder builder,
      RevealTimerBuilder revealTimerBuilder) {
    this.builder = builder;
    this.revealTimerbuilder = revealTimerBuilder;
  }

  @Override
  public String getName() {
    return "start-guess";
  }

  @Override
  public String getDescription() {
    return "Gibt dir einen Song, für den du den Namen erraten musst.";
  }

  @Override
  protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
      List<SlashCommandInteractionOption> arguments) throws MyOwnException {

    int difficulty = (new Random().nextInt(10) + 1);
    if (!arguments.isEmpty()) {
      difficulty = arguments.get(0).getLongValue().get().intValue();
    }

    return getRoutineRunner().start(
        builder.createRoutineStartGuessingGame(server, channel, user, difficulty,
            revealTimerbuilder));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of(SlashCommandOption.create(SlashCommandOptionType.LONG, "Schwierigkeit",
        "Schwierigkeit als Zahl. 1 ist dabei am einfachsten.", false));
  }


  @Override
  protected String getErrorMessage() {
    return "Konnte kein Ratespiel starten.";
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }
}
