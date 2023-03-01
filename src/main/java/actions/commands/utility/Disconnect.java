package actions.commands.utility;

import actions.commands.ACommand;
import actions.commands.Answer;
import actions.commands.CommandType;
import exceptions.MyOwnException;
import java.util.List;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import waifu.model.Player;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class Disconnect extends ACommand {


  public Disconnect() {
    super();
  }

  @Override
  public String getName() {
    return "" + "disc";
  }

  @Override
  public String getDescription() {
    return "PRIVAT";
  }

  @Override
  protected Answer executeCommand(DiscordApi api, Server server, TextChannel channel, User user,
      Player player, List<SlashCommandInteractionOption> arguments) throws MyOwnException {

    api.disconnect();
    System.exit(0);
    return null;
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return null;
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte den Bot nicht disconnecten.";
  }

  @Override
  public CommandType getCommandType() {
    return CommandType.PRIVATE;
  }

  @Override
  protected boolean isForAdmins() {
    return true;
  }
}
