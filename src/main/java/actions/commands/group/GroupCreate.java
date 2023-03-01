package actions.commands.group;

import actions.commands.ACommand;
import actions.commands.Answer;
import actions.commands.CommandType;
import com.google.inject.Inject;
import exceptions.MyOwnException;
import java.util.List;
import messages.MessageSender;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import routines.RoutineCreateGroup;
import waifu.loader.PlayerLoader;
import waifu.model.Player;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class GroupCreate extends ACommand {

  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;

  @Inject
  public GroupCreate(PlayerLoader playerLoader, MessageSender messageSender) {
    super();
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
  }

  @Override
  public String getName() {
    return "" + "groups-create";
  }

  @Override
  public String getDescription() {
    return "Erstellt eine neue Gruppe mit dem gegebenem Name.";
  }

  @Override
  protected Answer executeCommand(DiscordApi api, Server server, TextChannel channel, User user,
      Player player, List<SlashCommandInteractionOption> arguments) throws MyOwnException {

    String groupName = arguments.get(0).getStringValue().get();

    return getRoutineRunner().startRoutine(
        new RoutineCreateGroup(groupName, player, playerLoader, messageSender, channel));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of(SlashCommandOption.create(SlashCommandOptionType.STRING, "Gruppenname",
        "Name der zu erstellenden Gruppe.", true));
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte keine Gruppe erstellen.";
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
