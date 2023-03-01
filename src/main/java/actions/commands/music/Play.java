package actions.commands.music;

import actions.commands.ACommand;
import actions.commands.Answer;
import actions.commands.CommandType;
import com.google.inject.Inject;
import exceptions.MyOwnException;
import java.util.List;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import routines.RoutineAddToQueueBuilder;
import waifu.model.Player;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class Play extends ACommand {

  private final RoutineAddToQueueBuilder builder;

  @Inject
  public Play(RoutineAddToQueueBuilder builder) {
    super();
    this.builder = builder;
  }

  @Override
  public String getName() {
    return "" + "play";
  }

  @Override
  public String getDescription() {
    return "Startet den angegebenen Song";
  }

  @Override
  protected Answer executeCommand(DiscordApi api, Server server, TextChannel channel, User user,
      Player player, List<SlashCommandInteractionOption> arguments) throws MyOwnException {

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
  public CommandType getCommandType() {
    return CommandType.MUSIC;
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }
}
