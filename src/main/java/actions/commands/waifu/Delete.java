package actions.commands.waifu;

import actions.commands.Answer;
import actions.commands.ACommand;
import actions.commands.CommandType;
import com.google.inject.Inject;
import exceptions.MyOwnException;
import java.util.List;
import messages.MessageSender;
import messages.messages.DeletedWaifuOverview;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import waifu.loader.WaifuLoader;
import waifu.model.Player;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class Delete extends ACommand {

  private final WaifuLoader waifuLoader;
  private final MessageSender messageSender;

  @Inject
  public Delete(WaifuLoader waifuLoader, MessageSender messageSender) {
    super();
    this.waifuLoader = waifuLoader;
    this.messageSender = messageSender;
  }

  @Override
  public String getName() {
    return "" + "waifus-delete";
  }

  @Override
  public String getDescription() {
    return "Gibt dir deine WaifuListe, wobei jeder Klick eine Waifu loescht.";
  }

  @Override
  protected Answer executeCommand(DiscordApi api, Server server, TextChannel channel, User user,
      Player player, List<SlashCommandInteractionOption> arguments) throws MyOwnException {
    messageSender.send(new DeletedWaifuOverview(player, waifuLoader, messageSender), channel);
    return new Answer("Someone ordered his Delete-List");
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return null;
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte die Waifu nicht löschen.";
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
