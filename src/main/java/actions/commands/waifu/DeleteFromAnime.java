package actions.commands.waifu;

import actions.commands.Answer;
import actions.commands.ACommand;
import actions.commands.CommandType;
import exceptions.MyOwnException;
import messages.MessageSender;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import routines.RoutineDeleteWaifusFromAnime;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import waifu.model.Player;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.List;

public class DeleteFromAnime extends ACommand {

  private final WaifuLoader waifuLoader;
  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;

  public DeleteFromAnime(WaifuLoader waifuLoader, PlayerLoader playerLoader,
      MessageSender messageSender) {
    super();
    this.waifuLoader = waifuLoader;
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
  }

  @Override
  public String getName() {
    return "" + "waifus-delete-from-anime";
  }

  @Override
  public String getDescription() {
    return "Loescht alle Waifus, die aus dem angegebenen Anime kommen.";
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte die Waifus vom gegebenen Anime nicht löschen.";
  }

  @Override
  public CommandType getCommandType() {
    return CommandType.WAIFU;
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }

  @Override
  protected Answer executeCommand(DiscordApi api, Server server, TextChannel channel, User user,
      Player player, List<SlashCommandInteractionOption> arguments) throws MyOwnException {

    String anime = arguments.get(0).getStringValue().get();
    return getRoutineRunner().startRoutine(new RoutineDeleteWaifusFromAnime(waifuLoader,
        playerLoader, player, anime, channel, messageSender));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of(SlashCommandOption.create(SlashCommandOptionType.STRING, "Animename",
        "Name des Animes, vom welchem alle Waifus gelöscht werden sollen.", true));
  }
}
