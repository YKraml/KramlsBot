package actions.commands;

import exceptions.MyOwnException;
import exceptions.messages.CouldNotGetChannel;
import exceptions.messages.CouldNotGetServer;
import exceptions.messages.NotAdmin;
import java.util.List;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import routines.RoutineRunner;
import waifu.loader.PlayerLoader;
import waifu.model.Player;

public abstract class ACommand {

  private final static long ADMIN_VALUE = 1 << 3;
  private RoutineRunner routineRunner;

  public void setRoutineRunner(RoutineRunner routineRunner) {
    this.routineRunner = routineRunner;
  }

  public abstract CommandType getCommandType();

  public abstract String getName();

  public abstract String getDescription();

  public Answer execute(SlashCommandCreateEvent event, PlayerLoader playerLoader)
      throws MyOwnException {
    DiscordApi api = event.getApi();
    SlashCommandInteraction interaction = event.getSlashCommandInteraction();
    TextChannel channel = interaction.getChannel().orElseThrow(() -> new MyOwnException(new CouldNotGetChannel(), null));
    Server server = interaction.getServer().orElseThrow(() -> new MyOwnException(new CouldNotGetServer(), null));
    User user = interaction.getUser();
    List<SlashCommandInteractionOption> arguments = event.getSlashCommandInteraction().getArguments();

    if (isForAdmins() && !userIsAdmin(server, user)) {
      throw new MyOwnException(new NotAdmin(user.getName()), null);
    }

    Player player = playerLoader.getPlayerById(user.getIdAsString());
    Answer answer = executeCommand(api, server, channel, user, player, arguments);
    playerLoader.savePlayer(player);
    return answer;
  }

  protected RoutineRunner getRoutineRunner() {
    return routineRunner;
  }

  protected abstract boolean isForAdmins();

  protected abstract String getErrorMessage();

  protected abstract Answer executeCommand(DiscordApi api, Server server, TextChannel channel,
      User user, Player player,
      List<SlashCommandInteractionOption> arguments) throws MyOwnException;

  private static boolean userIsAdmin(Server server, User user) {
    return user.getRoles(server).stream()
        .flatMap(role -> role.getPermissions().getAllowedPermission().stream())
        .map(PermissionType::getValue)
        .anyMatch(value -> value == ADMIN_VALUE);
  }

  public abstract List<SlashCommandOption> getSlashCommandOptions();
}
