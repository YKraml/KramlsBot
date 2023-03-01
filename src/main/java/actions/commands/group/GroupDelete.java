package actions.commands.group;

import actions.commands.ACommand;
import actions.commands.Answer;
import actions.commands.CommandType;
import exceptions.MyOwnException;
import java.util.List;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import routines.RoutineDeleteGroup;
import waifu.loader.GroupLoader;
import waifu.model.Player;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class GroupDelete extends ACommand {

  private final GroupLoader groupLoader;

  public GroupDelete(GroupLoader groupLoader) {
    super();
    this.groupLoader = groupLoader;
  }

  @Override
  public String getName() {
    return "" + "groups-delete";
  }

  @Override
  public String getDescription() {
    return "Loescht die angegebene Gruppe.";
  }

  @Override
  protected Answer executeCommand(DiscordApi api, Server server, TextChannel channel, User user,
      Player player, List<SlashCommandInteractionOption> arguments) throws MyOwnException {
    String groupName = arguments.get(0).getStringValue().get();

    return getRoutineRunner().startRoutine(new RoutineDeleteGroup(player, groupName, channel,
        groupLoader));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of(SlashCommandOption.create(SlashCommandOptionType.STRING, "Gruppenname",
        "Name der zu löschenden Gruppe.", true));
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte die Gruppe nicht löschen.";
  }

  @Override
  public CommandType getCommandType() {
    return CommandType.GROUP;
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }


}
