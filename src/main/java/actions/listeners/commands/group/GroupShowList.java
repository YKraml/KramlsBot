package actions.listeners.commands.group;

import actions.listeners.commands.ACommand;
import actions.listeners.commands.Answer;
import com.google.inject.Inject;
import exceptions.MyOwnException;
import java.util.List;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import routines.RoutineShowGroupListBuilder;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class GroupShowList extends ACommand {

  private final RoutineShowGroupListBuilder routineShowGroupListBuilder;

  @Inject
  public GroupShowList(RoutineShowGroupListBuilder routineShowGroupListBuilder) {
    super();
    this.routineShowGroupListBuilder = routineShowGroupListBuilder;
  }

  @Override
  public String getName() {
    return "" + "groups";
  }

  @Override
  public String getDescription() {
    return "Zeigt alle eine Gruppen an";
  }

  @Override
  protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
      List<SlashCommandInteractionOption> arguments) throws MyOwnException {
    return getRoutineRunner().startRoutine(
        routineShowGroupListBuilder.createRoutineShowGroupList(channel, user));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of();
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte die Gruppenliste nicht anzeigen.";
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }

}
