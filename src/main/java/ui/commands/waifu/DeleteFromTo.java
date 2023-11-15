package ui.commands.waifu;

import ui.commands.ACommand;
import ui.commands.Answer;
import com.google.inject.Inject;
import domain.exceptions.MyOwnException;
import java.util.List;
import ui.messages.MessageSender;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import logic.routines.RoutineDeleteWaifusFromTo;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;

public class DeleteFromTo extends ACommand {

  private final WaifuLoader waifuLoader;
  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;

  @Inject
  public DeleteFromTo(WaifuLoader waifuLoader, PlayerLoader playerLoader,
      MessageSender messageSender) {
    this.waifuLoader = waifuLoader;
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
  }

  @Override
  public String getName() {
    return "waifus-delete-from-to";
  }

  @Override
  public String getDescription() {
    return "Loescht alle Waifus von der angegebenen Nummer bis zur angegebenen Nummer in deine Liste.";
  }

  @Override
  protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
                           List<SlashCommandInteractionOption> arguments) throws MyOwnException {
    int from = arguments.get(0).getLongValue().get().intValue();
    int to = arguments.get(1).getLongValue().get().intValue();

    return getRoutineRunner().start(
        new RoutineDeleteWaifusFromTo(user, from, to, waifuLoader, playerLoader, messageSender,
            channel));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of(SlashCommandOption.create(SlashCommandOptionType.LONG, "VonIndex",
            "Index vom welchem das Löschen starten soll.", true),
        SlashCommandOption.create(SlashCommandOptionType.LONG, "BisIndex",
            "Index bis welchem das Löschen gehen soll.", true));
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte die Waifus mit den Indizes nicht löschen.";
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }
}
