package ui.commands.waifu;

import com.google.inject.Inject;
import domain.Answer;
import domain.PlayerLoader;
import domain.exceptions.MyOwnException;
import java.util.List;
import logic.messages.MessageSender;
import logic.routines.RoutineCollectDaily;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import ui.commands.ACommand;

public class Daily extends ACommand {

  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;

  @Inject
  public Daily(PlayerLoader playerLoader, MessageSender messageSender) {
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
  }

  @Override
  public String getName() {
    return "daily";
  }

  @Override
  public String getDescription() {
    return "Du bekommst 1000 Euro. Nur einmal pro Tag anwendbar.";
  }

  @Override
  protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
      List<SlashCommandInteractionOption> arguments) throws MyOwnException {
    return getRoutineRunner().start(
        new RoutineCollectDaily(channel, user, playerLoader, messageSender));
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
