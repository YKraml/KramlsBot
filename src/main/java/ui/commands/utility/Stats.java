package ui.commands.utility;

import com.google.inject.Inject;
import domain.Answer;
import domain.PlayerLoader;
import domain.exceptions.MyOwnException;
import java.util.List;
import logic.messages.MessageSender;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import ui.commands.ACommand;

public class Stats extends ACommand {

  private final MessageSender messageSender;
  private final PlayerLoader playerLoader;

  @Inject
  public Stats(MessageSender messageSender, PlayerLoader playerLoader) {
    this.messageSender = messageSender;
    this.playerLoader = playerLoader;
  }

  @Override
  public String getName() {
    return "stats";
  }

  @Override
  public String getDescription() {
    return "Zeigt deine Statistiken an.";
  }

  @Override
  protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
      List<SlashCommandInteractionOption> arguments) throws MyOwnException {

    messageSender.send(new ui.messages.messages.Stats(user, server, playerLoader), channel);

    return new Answer("Here are your stats %s.".formatted(user.getDiscriminatedName()));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of();
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte die Statistiken des Spielers nicht anzeigen.";
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }

}
