package ui.commands.teams;

import domain.Answer;
import domain.exceptions.MyOwnException;
import java.util.List;
import javax.inject.Inject;
import logic.routines.RoutineStartFightBuilder;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import ui.commands.ACommand;

public class Fight extends ACommand {

  private final RoutineStartFightBuilder routineStartFightBuilder;

  @Inject
  public Fight(RoutineStartFightBuilder routineStartFightBuilder) {
    this.routineStartFightBuilder = routineStartFightBuilder;
  }

  @Override
  public String getName() {
    return "fight";
  }

  @Override
  public String getDescription() {
    return "Fordert einen anderen Spieler zum Kampf heraus.";
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of(
        SlashCommandOption.create(SlashCommandOptionType.USER, "Gegner",
            "Gegner, den du herausfordern willst.", true),
        SlashCommandOption.create(SlashCommandOptionType.LONG, "Geld",
            "Geld, das du wetten willst.", true),
        SlashCommandOption.create(SlashCommandOptionType.LONG, "Stardust",
            "Stardust, das du wetten willst.", true),
        SlashCommandOption.create(SlashCommandOptionType.LONG, "Morphsteine",
            "Morphsteine, das du wetten willst.", true)
    );
  }

  @Override
  protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
      List<SlashCommandInteractionOption> arguments) throws MyOwnException {

    User enemy = arguments.get(0).getUserValue().get();
    long money = arguments.get(1).getLongValue().get();
    long stardust = arguments.get(2).getLongValue().get();
    long morphStones = arguments.get(3).getLongValue().get();

    return getRoutineRunner().start(
        routineStartFightBuilder.createStartFightRoutine(user, enemy,
            money, stardust, morphStones, channel));
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte den Gegner nicht zum Kampf herausfordern.";
  }
}
