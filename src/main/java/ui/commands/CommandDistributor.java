package ui.commands;

import com.google.inject.Inject;
import domain.Answer;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CommandWentWrong;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import ui.embeds.ExceptionEmbed;
import ui.Terminal;

import java.util.List;
import java.util.Optional;

/**
 * Klasse {@link CommandDistributor} managed alle Befehle.
 *
 * @author Yannick Kraml
 * @version 1.0
 */
public final class CommandDistributor {

    private final List<ACommand> commands;

    @Inject
    public CommandDistributor(List<ACommand> commands) {
        this.commands = commands;
    }

    public Answer distributeCommand(SlashCommandCreateEvent event) {

        String commandName = event.getSlashCommandInteraction().getCommandName();
        Optional<ACommand> commandOptional = commands.stream()
                .filter(command -> command.getName().equalsIgnoreCase(commandName)).findFirst();

        return commandOptional.map(command -> executeCommand(event, command))
                .orElseGet(() -> createCommandNotFoundAnswer(event, commandName));
    }

    private Answer exceptionAnswer(Exception e, TextChannel channel, String message) {
        MyOwnException ex = new MyOwnException(new CommandWentWrong(message), e);
        channel.sendMessage(new ExceptionEmbed(ex));
        Terminal.printError(ex);
        return new Answer("Exception happened while executing a command.");
    }

    private Answer createCommandNotFoundAnswer(SlashCommandCreateEvent event, String commandName) {
        String text = "Konnte den Befehl '%s' nicht finden.".formatted(commandName);
        event.getSlashCommandInteraction().createImmediateResponder().append(text).respond();
        return new Answer(text);
    }

    private Answer executeCommand(SlashCommandCreateEvent event, ACommand command) {
        event.getSlashCommandInteraction().createImmediateResponder().respond();

        Answer answer;
        try {
            answer = command.execute(event);
        } catch (Exception e) {
            answer = event.getSlashCommandInteraction().getChannel()
                    .map(textChannel -> exceptionAnswer(e, textChannel, command.getErrorMessage()))
                    .orElse(new Answer("Channel nicht gefunden."));
        }

        return answer;
    }


}
