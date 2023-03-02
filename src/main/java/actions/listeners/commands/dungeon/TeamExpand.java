package actions.listeners.commands.dungeon;

import actions.listeners.commands.ACommand;
import actions.listeners.commands.Answer;
import actions.listeners.commands.CommandType;
import com.google.inject.Inject;
import exceptions.MyOwnException;
import java.util.List;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import routines.RoutineExpandTeam;
import waifu.loader.PlayerLoader;
import waifu.model.Player;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class TeamExpand extends ACommand {

  private final PlayerLoader playerLoader;

  @Inject
  public TeamExpand(PlayerLoader playerLoader) {
    super();
    this.playerLoader = playerLoader;
  }

  @Override
  public String getName() {
    return "" + "teams-expand";
  }

  @Override
  public String getDescription() {
    return "Vergoessert das angegebene Team.";
  }

  @Override
  protected Answer executeCommand(DiscordApi api, Server server, TextChannel channel, User user,
      Player player, List<SlashCommandInteractionOption> arguments) throws MyOwnException {

    String teamName = arguments.get(0).getStringValue().get();

    return getRoutineRunner().startRoutine(
        new RoutineExpandTeam(player, teamName, channel, playerLoader));
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
  public CommandType getCommandType() {
    return CommandType.DUNGEON;
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }

}
