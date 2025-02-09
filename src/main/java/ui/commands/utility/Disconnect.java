package ui.commands.utility;

import domain.Answer;
import domain.exceptions.MyOwnException;
import java.util.List;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import ui.commands.ACommand;

public class Disconnect extends ACommand {

  @Override
  public String getName() {
    return "disc";
  }

  @Override
  public String getDescription() {
    return "PRIVAT";
  }

  @Override
  protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
      List<SlashCommandInteractionOption> arguments) throws MyOwnException {

    api.disconnect();
    System.exit(0);
    return null;
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of();
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte den Bot nicht disconnecten.";
  }

  @Override
  protected boolean isForAdmins() {
    return true;
  }
}
