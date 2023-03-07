package actions.listeners.commands.teams;

import actions.listeners.commands.ACommand;
import actions.listeners.commands.Answer;
import com.google.inject.Inject;
import exceptions.MyOwnException;
import java.util.List;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import routines.RoutineRenameTeam;
import waifu.loader.PlayerLoader;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class TeamRename extends ACommand {

  private final PlayerLoader playerLoader;

  @Inject
  public TeamRename(PlayerLoader playerLoader) {
    super();
    this.playerLoader = playerLoader;
  }

  @Override
  public String getName() {
    return "" + "teams-rename";
  }

  @Override
  public String getDescription() {
    return "Nennt die angegebene Gruppe um.";
  }

  @Override
  protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
      List<SlashCommandInteractionOption> arguments) throws MyOwnException {
    String oldName = arguments.get(0).getStringValue().get();
    String newName = arguments.get(1).getStringValue().get();

    return getRoutineRunner().startRoutine(
        new RoutineRenameTeam(user, playerLoader, channel, newName, oldName));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of(SlashCommandOption.create(SlashCommandOptionType.STRING, "AlterName",
            "Alter Name des Teams", true),
        SlashCommandOption.create(SlashCommandOptionType.STRING, "NeuerName",
            "Neuer Name des Teams", true));
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte das Team nicht umbenennen.";
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }

}
