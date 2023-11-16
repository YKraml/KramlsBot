package ui.commands.gambling;

import com.google.inject.Inject;
import domain.exceptions.MyOwnException;
import logic.routines.RoutineDoubleOrNothingBuilder;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import ui.commands.ACommand;
import domain.Answer;

import java.util.List;

public class DoubleOrNothing extends ACommand {

    private final RoutineDoubleOrNothingBuilder routineDoubleOrNothingBuilder;

    @Inject
    public DoubleOrNothing(RoutineDoubleOrNothingBuilder routineDoubleOrNothingBuilder) {
        this.routineDoubleOrNothingBuilder = routineDoubleOrNothingBuilder;
    }

    @Override
    public String getName() {
        return "gambling-alles-oder-nichts";
    }

    @Override
    public String getDescription() {
        return "Verdoppelt dein Geld oder du verlierst alles.";
    }

    @Override
    protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
                             List<SlashCommandInteractionOption> arguments) throws MyOwnException {
        return getRoutineRunner().start(
                routineDoubleOrNothingBuilder.createRoutineDoubleOrNothing(user, channel));
    }

    @Override
    public List<SlashCommandOption> getSlashCommandOptions() {
        return List.of();
    }

    @Override
    protected String getErrorMessage() {
        return "Konnte das Glücksspiel nicht ausführen.";
    }

    @Override
    protected boolean isForAdmins() {
        return false;
    }

}
