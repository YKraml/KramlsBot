package actions.listeners.commands;

import com.google.inject.Inject;
import de.kraml.Terminal;
import embeds.ExceptionEmbed;
import exceptions.MyOwnException;
import exceptions.messages.CommandWentWrong;
import java.util.List;
import java.util.Optional;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import waifu.loader.PlayerLoader;

/**
 * Klasse {@link CommandDistributor} managed alle Befehle.
 *
 * @author Yannick Kraml
 * @version 1.0
 */
public final class CommandDistributor {

  private final List<ACommand> commands;
  private final PlayerLoader playerLoader;

  @Inject
  public CommandDistributor(PlayerLoader playerLoader, List<ACommand> commands) {
    this.playerLoader = playerLoader;
    this.commands = commands;
  }


  public Answer distributeCommand(SlashCommandCreateEvent event) {

    String commandName = event.getSlashCommandInteraction().getCommandName();
    Optional<ACommand> commandOptional = commands.stream()
        .filter(command -> command.getName().equalsIgnoreCase(commandName)).findFirst();

    return commandOptional.map(command -> executeCommand(event, command))
        .orElseGet(() -> createCommandNotFoundAnswer(event, commandName));
  }

  private Answer createCommandNotFoundAnswer(SlashCommandCreateEvent event, String commandName) {
    String text = "Konnte den Befehl '%s' nicht finden.".formatted(commandName);
    event.getSlashCommandInteraction().createImmediateResponder().append(text).respond();
    return new Answer(text);
  }

  private Answer executeCommand(SlashCommandCreateEvent event, ACommand command) {
    event.getSlashCommandInteraction().respondLater();
    Answer answer;
    try {
      answer = command.execute(event, playerLoader);
    } catch (Exception e) {
      answer = event.getSlashCommandInteraction().getChannel()
          .map(textChannel -> exceptionAnswer(e, textChannel, command.getErrorMessage()))
          .orElse(new Answer("Channel nicht gefunden."));
    }

    event.getSlashCommandInteraction().createFollowupMessageBuilder()
        .append(command.getClass().getSimpleName()).send();

    return answer;
  }

  private static Answer exceptionAnswer(Exception e, TextChannel channel, String message) {
    e.printStackTrace();
    MyOwnException ex = new MyOwnException(new CommandWentWrong(message), e);
    channel.sendMessage(new ExceptionEmbed(ex));
    Terminal.printError(ex);
    return new Answer("Exception happened while executing a command.");
  }


}
