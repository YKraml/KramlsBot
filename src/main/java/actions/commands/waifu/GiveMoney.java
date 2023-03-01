package actions.commands.waifu;

import actions.commands.Answer;
import actions.commands.ACommand;
import actions.commands.CommandType;
import com.google.inject.Inject;
import exceptions.MyOwnException;
import java.util.List;
import messages.MessageSender;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import routines.RoutineGiveMoney;
import waifu.loader.PlayerLoader;
import waifu.model.Player;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class GiveMoney extends ACommand {

  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;

  @Inject
  public GiveMoney(PlayerLoader playerLoader, MessageSender messageSender) {
    super();
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
  }

  @Override
  public String getName() {
    return "" + "give-money";
  }

  @Override
  public String getDescription() {
    return "Gib dem angegebenen Spieler N viel Euro";
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte kein Geld übertragen.";
  }

  @Override
  public CommandType getCommandType() {
    return CommandType.GAMBLING;
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }

  @Override
  protected Answer executeCommand(DiscordApi api, Server server, TextChannel channel, User user,
      Player player, List<SlashCommandInteractionOption> arguments) throws MyOwnException {

    User receiver = arguments.get(0).getUserValue().get();
    int money = arguments.get(1).getLongValue().get().intValue();

    return getRoutineRunner().startRoutine(
        new RoutineGiveMoney(playerLoader, channel, user, receiver, money, messageSender));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of(SlashCommandOption.create(SlashCommandOptionType.USER, "Nutzer",
            "Nutzer, welchem du Geld schenken willst", true),
        SlashCommandOption.create(SlashCommandOptionType.LONG, "Geld",
            "Menge an Geld, die du verschenken willst.", true));
  }
}
