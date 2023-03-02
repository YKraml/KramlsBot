package actions.listeners.commands.group;

import actions.listeners.commands.ACommand;
import actions.listeners.commands.Answer;
import actions.listeners.commands.CommandType;
import com.google.inject.Inject;
import exceptions.MyOwnException;
import java.util.List;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import routines.RoutineAddWaifuToGroup;
import waifu.loader.PlayerLoader;
import waifu.model.Player;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class GroupAddWaifu extends ACommand {

  private final PlayerLoader playerLoader;

  @Inject
  public GroupAddWaifu(PlayerLoader playerLoader) {
    super();
    this.playerLoader = playerLoader;
  }

  @Override
  public String getName() {
    return "" + "groups-add";
  }

  @Override
  public String getDescription() {
    return "Fuegt die Waifu mit der Nummer n der angegebenen Gruppe hinzu.";
  }

  @Override
  protected Answer executeCommand(DiscordApi api, Server server, TextChannel channel, User user,
      Player player, List<SlashCommandInteractionOption> arguments) throws MyOwnException {

    String groupName = arguments.get(0).getStringValue().get();
    int waifuId = arguments.get(1).getLongValue().get().intValue();

    return getRoutineRunner().startRoutine(
        new RoutineAddWaifuToGroup(player, groupName, waifuId, channel, playerLoader));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of(SlashCommandOption.create(SlashCommandOptionType.STRING, "Gruppenname",
            "Name der Gruppe, in welche die Waifu soll.", true),
        SlashCommandOption.create(SlashCommandOptionType.LONG, "WaifuId",
            "Id der Waifu, die in die Gruppe soll", true));
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte keine Waifu zum Team hinzufügen.";
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
