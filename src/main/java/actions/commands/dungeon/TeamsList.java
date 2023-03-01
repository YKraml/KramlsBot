package actions.commands.dungeon;

import actions.commands.Answer;
import actions.commands.ACommand;
import actions.commands.CommandType;
import exceptions.MyOwnException;
import java.util.List;
import messages.MessageSender;
import messages.MessageSenderImpl;
import messages.messages.TeamsOverview;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import waifu.loader.DungeonLoader;
import waifu.loader.PlayerLoader;
import waifu.model.Player;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class TeamsList extends ACommand {

  private final DungeonLoader dungeonLoader;
  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;

  public TeamsList(DungeonLoader dungeonLoader, PlayerLoader playerLoader,
      MessageSender messageSender) {
    super();
    this.dungeonLoader = dungeonLoader;
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
  }

  @Override
  public String getName() {
    return "" + "teams";
  }

  @Override
  public String getDescription() {
    return "Zeigt deine Teams an.";
  }

  @Override
  protected Answer executeCommand(DiscordApi api, Server server, TextChannel channel, User user,
      Player player, List<SlashCommandInteractionOption> arguments) throws MyOwnException {
    MessageSenderImpl result;
    synchronized (MessageSenderImpl.class) {
      result = new MessageSenderImpl();
    }
    result.send(new TeamsOverview(player, dungeonLoader, playerLoader, messageSender), channel);

    return new Answer("Someone looked at his teams.");
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of();
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte die Teamliste nicht anzeigen.";
  }

  @Override
  public CommandType getCommandType() {
    return CommandType.DUNGEON;
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }


}
