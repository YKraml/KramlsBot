package actions.commands.waifu;

import actions.commands.Answer;
import actions.commands.ACommand;
import actions.commands.CommandType;
import com.google.inject.Inject;
import exceptions.MyOwnException;
import java.util.List;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import routines.RoutineGiveWaifuBuilder;
import waifu.model.Player;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class GiveWaifu extends ACommand {

  private final RoutineGiveWaifuBuilder routineGiveWaifuBuilder;

  @Inject
  public GiveWaifu(RoutineGiveWaifuBuilder routineGiveWaifuBuilder) {
    super();
    this.routineGiveWaifuBuilder = routineGiveWaifuBuilder;
  }

  @Override
  public String getName() {
    return "" + "give-waifu";
  }

  @Override
  public String getDescription() {
    return "Damit gibst du einem anderen Spieler eine Waifu";
  }

  @Override
  protected Answer executeCommand(DiscordApi api, Server server, TextChannel channel, User user,
      Player player, List<SlashCommandInteractionOption> arguments) throws MyOwnException {

    User receiver = arguments.get(0).getUserValue().get();
    int waifuNumber = arguments.get(1).getLongValue().get().intValue();

    return getRoutineRunner().startRoutine(
        routineGiveWaifuBuilder.createRoutineGiveWaifu(channel, user, receiver, waifuNumber));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of(SlashCommandOption.create(SlashCommandOptionType.USER, "Nutzer",
            "Nutzer, der die Waifu erhalten soll.", true),
        SlashCommandOption.create(SlashCommandOptionType.LONG, "WaifuId",
            "Id der Waifu, die du verschenken willst.", true));
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte die Waifu nicht weitergeben.";
  }

  @Override
  public CommandType getCommandType() {
    return CommandType.WAIFU;
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }

}
