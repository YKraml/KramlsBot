package actions.commands.utility;

import actions.commands.ACommand;
import actions.commands.Answer;
import actions.commands.CommandType;
import com.google.inject.Inject;
import exceptions.MyOwnException;
import java.util.List;
import messages.MessageSender;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import waifu.model.Player;

public class Stats extends ACommand {

  private final MessageSender messageSender;

  @Inject
  public Stats(MessageSender messageSender) {
    super();
    this.messageSender = messageSender;
  }

  @Override
  public String getName() {
    return "" + "stats";
  }

  @Override
  public String getDescription() {
    return "Zeigt deine Statistiken an.";
  }

  @Override
  protected Answer executeCommand(DiscordApi api, Server server, TextChannel channel, User user,
      Player player, List<SlashCommandInteractionOption> arguments) throws MyOwnException {

    messageSender.send(new messages.messages.Stats(player, user, server), channel);

    return new Answer("Showed someone his stats. User = " + user.getDiscriminatedName());
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return null;
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte die Statistiken des Spielers nicht anzeigen.";
  }

  @Override
  public CommandType getCommandType() {
    return CommandType.UTIL;
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }

}
