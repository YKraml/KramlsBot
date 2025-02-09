package ui.commands.group;

import com.google.inject.Inject;
import domain.Answer;
import domain.GroupLoader;
import domain.PlayerLoader;
import domain.exceptions.MyOwnException;
import java.util.List;
import logic.routines.RoutineDeleteGroup;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import ui.commands.ACommand;

public class GroupDelete extends ACommand {

  private final GroupLoader groupLoader;
  private final PlayerLoader playerLoader;

  @Inject
  public GroupDelete(GroupLoader groupLoader, PlayerLoader playerLoader) {
    this.groupLoader = groupLoader;
    this.playerLoader = playerLoader;
  }

  @Override
  public String getName() {
    return "groups-delete";
  }

  @Override
  public String getDescription() {
    return "Loescht die angegebene Gruppe.";
  }

  @Override
  protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
      List<SlashCommandInteractionOption> arguments) throws MyOwnException {
    String groupName = arguments.get(0).getStringValue().get();

    return getRoutineRunner().start(
        new RoutineDeleteGroup(user, groupName, channel, groupLoader, playerLoader));
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
  protected boolean isForAdmins() {
    return false;
  }


}
