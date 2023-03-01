package actions.commands.group;

import actions.commands.ACommand;
import actions.commands.Answer;
import actions.commands.CommandType;
import exceptions.MyOwnException;
import java.util.List;
import messages.MessageSender;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import routines.RoutineRemoveFromGroup;
import waifu.loader.GroupLoader;
import waifu.model.Player;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class GroupRemoveWaifu extends ACommand {

  private final GroupLoader groupLoader;
  private final MessageSender messageSender;

  public GroupRemoveWaifu(GroupLoader groupLoader, MessageSender messageSender) {
    super();
    this.groupLoader = groupLoader;
    this.messageSender = messageSender;
  }

  @Override
  public String getName() {
    return "" + "groups-remove";
  }

  @Override
  public String getDescription() {
    return "Entfert die angegebene Waifu aus der angegebenen Gruppe.";
  }

  @Override
  protected Answer executeCommand(DiscordApi api, Server server, TextChannel channel, User user,
      Player player, List<SlashCommandInteractionOption> arguments) throws MyOwnException {

    String groupName = arguments.get(0).getStringValue().get();
    int waifuNumber = arguments.get(1).getLongValue().get().intValue();

    return getRoutineRunner().startRoutine(
        new RoutineRemoveFromGroup(player, groupName, waifuNumber, channel, groupLoader,
            messageSender));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of(SlashCommandOption.create(SlashCommandOptionType.STRING, "Gruppenname",
            "Name der Gruppe, aus welcher die Waifu entfernt werden soll.", true),
        SlashCommandOption.create(SlashCommandOptionType.LONG, "WaifuId",
            "Id der Waifu, die entfernt werden soll", true));
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte niemanden vom Team entfernen.";
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
