package actions.listeners.commands.waifu;

import actions.listeners.commands.ACommand;
import actions.listeners.commands.Answer;
import com.google.inject.Inject;
import exceptions.MyOwnException;
import java.util.List;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import routines.RoutineCollectDaily;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import waifu.loader.PlayerLoader;

public class Daily extends ACommand {

  private final PlayerLoader playerLoader;

  @Inject
  public Daily(PlayerLoader playerLoader) {
    super();
    this.playerLoader = playerLoader;
  }

  @Override
  public String getName() {
    return "" + "daily";
  }

  @Override
  public String getDescription() {
    return "Du bekommst 1000 Euro. Nur einmal pro Tag anwendbar.";
  }

  @Override
  protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
      List<SlashCommandInteractionOption> arguments) throws MyOwnException {
    return getRoutineRunner().startRoutine(new RoutineCollectDaily(channel, user, playerLoader));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of();
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte die tägliche Belohnng nicht einsammeln.";
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }

}
