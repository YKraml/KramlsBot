package actions.listeners.commands.waifu;

import actions.listeners.commands.ACommand;
import actions.listeners.commands.Answer;
import com.google.inject.Inject;
import exceptions.MyOwnException;
import java.util.List;
import messages.MessageSender;
import messages.messages.DeletedWaifuOverview;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class Delete extends ACommand {

  private final WaifuLoader waifuLoader;
  private final MessageSender messageSender;
  private final PlayerLoader playerLoader;

  @Inject
  public Delete(WaifuLoader waifuLoader, MessageSender messageSender, PlayerLoader playerLoader) {
    this.waifuLoader = waifuLoader;
    this.messageSender = messageSender;
    this.playerLoader = playerLoader;
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
  protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
      List<SlashCommandInteractionOption> arguments) throws MyOwnException {
    messageSender.send(new DeletedWaifuOverview(user, waifuLoader, messageSender, playerLoader), channel);
    return new Answer("Someone ordered his Delete-List");
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of();
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte die Waifu nicht löschen.";
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }
}
