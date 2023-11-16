package ui.commands.guess;

import com.google.inject.Inject;
import domain.exceptions.MyOwnException;
import logic.routines.RoutineRevealBuilder;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import ui.commands.ACommand;
import domain.Answer;

import java.util.List;

public class Reveal extends ACommand {

    private final RoutineRevealBuilder routineRevealBuilder;

    @Inject
    public Reveal(RoutineRevealBuilder routineRevealBuilder) {
        this.routineRevealBuilder = routineRevealBuilder;
    }

    @Override
    public String getName() {
        return "reveal";
    }

    @Override
    public String getDescription() {
        return "Beendet frühzeitig das Ratespiel";
    }

    @Override
    protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
                             List<SlashCommandInteractionOption> arguments) throws MyOwnException {
        return getRoutineRunner().start(
                routineRevealBuilder.createRoutineReveal(server, channel));
    }

    @Override
    public List<SlashCommandOption> getSlashCommandOptions() {
        return List.of();
    }

    @Override
    protected String getErrorMessage() {
        return "Konnte den Anime nicht auflösen.";
    }

    @Override
    protected boolean isForAdmins() {
        return false;
    }

}
