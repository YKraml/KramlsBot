package actions.commands;

import de.kraml.Terminal;
import embeds.ExceptionEmbed;
import exceptions.MyOwnException;
import exceptions.messages.CommandWentWrong;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import waifu.loader.PlayerLoader;

/**
 * Klasse {@link CommandManager} managed alle Befehle.
 *
 * @author Yannick Kraml
 * @version 1.0
 */
public final class CommandManager {

  private final Collection<ACommand> commands;
  private final PlayerLoader playerLoader;

  public CommandManager(PlayerLoader playerLoader, Collection<ACommand> commands) {
    this.playerLoader = playerLoader;
    this.commands = commands;
  }


  public Answer executeCommand(SlashCommandCreateEvent event) {

    event.getSlashCommandInteraction().respondLater();
    String commandName = event.getSlashCommandInteraction().getCommandName();
    TextChannel channel = event.getSlashCommandInteraction().getChannel().get();

    Predicate<ACommand> filter = command -> commandName.trim().toLowerCase()
        .matches(command.getName().toLowerCase());

    Optional<ACommand> commandOptional = commands.stream().filter(filter).findFirst();
    if (commandOptional.isEmpty()) {
      event.getSlashCommandInteraction().createImmediateResponder()
          .append("Konnte den Befehl '%s' nicht finden.".formatted(commandName)).respond();
      return new Answer("Command not found");
    }

    ACommand command = commandOptional.get();
    Answer answer;
    try {
      answer = command.execute(event, playerLoader);
    } catch (Exception e) {
      answer = createExceptionAnswer(e, channel, command.getErrorMessage());
    }
    event.getSlashCommandInteraction().createFollowupMessageBuilder()
        .append(command.getClass().getSimpleName()).send();
    return answer;
  }

  private static Answer createExceptionAnswer(Exception e, TextChannel channel, String message) {
    e.printStackTrace();
    MyOwnException ex = new MyOwnException(new CommandWentWrong(message), e);
    channel.sendMessage(new ExceptionEmbed(ex));
    Terminal.printError(ex);
    return new Answer("Exception happened while executing a command.");
  }


}
