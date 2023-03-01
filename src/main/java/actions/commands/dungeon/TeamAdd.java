package actions.commands.dungeon;

import actions.commands.Answer;
import actions.commands.ACommand;
import actions.commands.CommandType;
import com.google.inject.Inject;
import exceptions.MyOwnException;
import java.util.List;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import routines.RoutineAddToTeam;
import waifu.loader.PlayerLoader;
import waifu.model.Player;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class TeamAdd extends ACommand {

  private final PlayerLoader playerLoader;

  @Inject
  public TeamAdd(PlayerLoader playerLoader) {
    super();
    this.playerLoader = playerLoader;
  }

  @Override
  public String getName() {
    return "" + "teams-add";
  }

  @Override
  public String getDescription() {
    return "Fuegt eine Waifu deinem Team hinzu.";
  }

  @Override
  protected Answer executeCommand(DiscordApi api, Server server, TextChannel channel, User user,
      Player player, List<SlashCommandInteractionOption> arguments) throws MyOwnException {

    String teamName = arguments.get(0).getStringValue().get();
    int waifuIndex = Math.toIntExact(arguments.get(1).getLongValue().get());

    return getRoutineRunner().startRoutine(
        new RoutineAddToTeam(playerLoader, channel, player, teamName, waifuIndex));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of(SlashCommandOption.create(SlashCommandOptionType.STRING, "Teamname",
            "Team, in welches die Waifu soll.", true),
        SlashCommandOption.create(SlashCommandOptionType.LONG, "Id",
            "Id der Wwaifu, die in das Team soll.", true));
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte niemanden zum Team hinzufügen.";
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
