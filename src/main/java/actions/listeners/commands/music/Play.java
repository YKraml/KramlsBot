package actions.listeners.commands.music;

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
import org.javacord.api.interaction.SlashCommandOptionType;
import routines.RoutineAddToQueueBuilder;

public class Play extends ACommand {

  private final RoutineAddToQueueBuilder builder;

  @Inject
  public Play(RoutineAddToQueueBuilder builder) {
    this.builder = builder;
  }

  @Override
  public String getName() {
    return "play";
  }

  @Override
  public String getDescription() {
    return "Startet den angegebenen Song";
  }

  @Override
  protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
      List<SlashCommandInteractionOption> arguments) throws MyOwnException {

    String input = arguments.get(0).getStringValue().get();
    return getRoutineRunner().startRoutine(
        builder.createRoutineAddToQueue(server, channel, user, input));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of(SlashCommandOption.create(SlashCommandOptionType.STRING, "Titel",
        "Name oder URL des Songs, welches abgespielt werden soll.", true));
  }


  @Override
  protected String getErrorMessage() {
    return "Konnte den gegebenen Song nicht starten.";
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }
}
