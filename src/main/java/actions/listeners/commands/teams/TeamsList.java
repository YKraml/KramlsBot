package actions.listeners.commands.teams;

import actions.listeners.commands.ACommand;
import actions.listeners.commands.Answer;
import com.google.inject.Inject;
import exceptions.MyOwnException;
import java.util.List;
import messages.MessageSender;
import messages.messages.TeamsOverview;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import waifu.JikanFetcher;
import waifu.loader.DungeonLoader;
import waifu.loader.PlayerLoader;
import waifu.loader.WaifuLoader;

public class TeamsList extends ACommand {

  private final DungeonLoader dungeonLoader;
  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;
  private final WaifuLoader waifuLoader;
  private final JikanFetcher jikanFetcher;

  @Inject
  public TeamsList(DungeonLoader dungeonLoader, PlayerLoader playerLoader,
      MessageSender messageSender, WaifuLoader waifuLoader, JikanFetcher jikanFetcher) {
    this.dungeonLoader = dungeonLoader;
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
    this.waifuLoader = waifuLoader;
    this.jikanFetcher = jikanFetcher;
  }

  @Override
  public String getName() {
    return "teams";
  }

  @Override
  public String getDescription() {
    return "Zeigt deine Teams an.";
  }

  @Override
  protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
      List<SlashCommandInteractionOption> arguments) throws MyOwnException {
    messageSender.send(new TeamsOverview(user, dungeonLoader, playerLoader, messageSender,
        waifuLoader, jikanFetcher), channel);

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
  protected boolean isForAdmins() {
    return false;
  }


}
