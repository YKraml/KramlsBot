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
import org.javacord.api.interaction.SlashCommandOptionType;
import routines.RoutineClaimBuilder;

public class Claim extends ACommand {

  private final RoutineClaimBuilder routineClaimBuilder;

  @Inject
  public Claim(RoutineClaimBuilder routineClaimBuilder) {
    this.routineClaimBuilder = routineClaimBuilder;
  }

  @Override
  public String getName() {
    return "claim";
  }

  @Override
  public String getDescription() {
    return "Damit kannst du eine Waifu für dich beanspruchen, falls du den Namen richtig erraten hast.";
  }

  @Override
  protected synchronized Answer execute(DiscordApi api, Server server, TextChannel channel,
      User user, List<SlashCommandInteractionOption> arguments)
      throws MyOwnException {

    String guess = arguments.get(0).getStringValue().get();

    return getRoutineRunner().startRoutine(
        routineClaimBuilder.createRoutineClaim(server, channel, user, guess));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of(
        SlashCommandOption.create(SlashCommandOptionType.STRING, "Name", "Name der Waifu.", true));
  }


  @Override
  protected String getErrorMessage() {
    return "Konnte die Waifu nicht claimen.";
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }
}
