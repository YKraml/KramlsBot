package actions.commands.waifu;

import actions.commands.Answer;
import actions.commands.CommandType;
import actions.commands.ACommand;
import exceptions.MyOwnException;
import java.util.List;
import messages.MessageSender;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import waifu.loader.PlayerLoader;
import messages.messages.ExpandedList;
import waifu.model.Player;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class Expand extends ACommand {

  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;

  public Expand(PlayerLoader playerLoader, MessageSender messageSender) {
    super();
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
  }

  @Override
  public String getName() {
    return "" + "expand";
  }

  @Override
  public String getDescription() {
    return "Kauft 10 Plaetze für deine Waifuliste.";
  }

  @Override
  protected Answer executeCommand(DiscordApi api, Server server, TextChannel channel, User user,
      Player player, List<SlashCommandInteractionOption> arguments) throws MyOwnException {

    int currentSize = player.getMaxWaifus();
    int costForUpgrade = currentSize * 100;

    player.getInventory().removeMoney(costForUpgrade);
    player.expandMaxWaifus(10);
    messageSender.send(new ExpandedList(player, costForUpgrade), channel);
    playerLoader.savePlayer(player);

    return new Answer("Someone expanded his WaifuList.");
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return null;
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte die Waifu-Liste nicht erweitern.";
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
