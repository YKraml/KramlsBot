package actions.listeners.commands.waifu;

import actions.listeners.commands.ACommand;
import actions.listeners.commands.Answer;
import com.google.inject.Inject;
import exceptions.MyOwnException;
import java.util.List;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import routines.RoutineShowWaifuListBuilder;

public class ShowWaifuList extends ACommand {

  private final RoutineShowWaifuListBuilder routineShowWaifuListBuilder;

  @Inject
  public ShowWaifuList(RoutineShowWaifuListBuilder routineShowWaifuListBuilder) {
    this.routineShowWaifuListBuilder = routineShowWaifuListBuilder;
  }

  @Override
  public String getName() {
    return "waifus";
  }

  @Override
  public String getDescription() {
    return "Zeigt dir eine Liste mit deinen Waifus an.";
  }

  @Override
  protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
      List<SlashCommandInteractionOption> arguments) throws MyOwnException {
    return getRoutineRunner().start(
        routineShowWaifuListBuilder.createRoutineShowWaifuList(user, channel));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of();
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte die Waifuliste nicht anzeigen.";
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }

}
