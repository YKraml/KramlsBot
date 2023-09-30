package actions.listeners.commands.teams;

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
import routines.RoutineExpandTeam;
import waifu.loader.PlayerLoader;

public class TeamExpand extends ACommand {

  private final PlayerLoader playerLoader;

  @Inject
  public TeamExpand(PlayerLoader playerLoader) {
    this.playerLoader = playerLoader;
  }

  @Override
  public String getName() {
    return "teams-expand";
  }

  @Override
  public String getDescription() {
    return "Vergoessert das angegebene Team.";
  }

  @Override
  protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
      List<SlashCommandInteractionOption> arguments) throws MyOwnException {

    String teamName = arguments.get(0).getStringValue().get();

    return getRoutineRunner().start(
        new RoutineExpandTeam(user, teamName, channel, playerLoader));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of(SlashCommandOption.create(SlashCommandOptionType.STRING, "Teamname",
        "Name des Teams, welches vergrößert werden soll.", true));
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte das Team nicht vergrößern.";
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }

}
