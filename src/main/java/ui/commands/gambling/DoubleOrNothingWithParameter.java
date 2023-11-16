package ui.commands.gambling;

import com.google.inject.Inject;
import domain.exceptions.MyOwnException;
import logic.routines.RoutineGamblingDoubleBuilder;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import ui.commands.ACommand;
import domain.Answer;

import java.util.List;

public class DoubleOrNothingWithParameter extends ACommand {

    private final RoutineGamblingDoubleBuilder routineGamblingDoubleBuilder;

    @Inject
    public DoubleOrNothingWithParameter(
            RoutineGamblingDoubleBuilder routineGamblingDoubleBuilder) {
        this.routineGamblingDoubleBuilder = routineGamblingDoubleBuilder;
    }

    @Override
    public String getName() {
        return "gambling-doppel";
    }

    @Override
    public String getDescription() {
        return "Entweder du verdoppelst das angegebene Geld oder du gewinnst nichts.";
    }

    @Override
    protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
                             List<SlashCommandInteractionOption> arguments) throws MyOwnException {
        int bettedMoney = Math.toIntExact(arguments.get(0).getLongValue().get());

        return getRoutineRunner().start(
                routineGamblingDoubleBuilder.createRoutineDoubleOrNothingWithParameter(user,
                        bettedMoney, channel));
    }

    @Override
    public List<SlashCommandOption> getSlashCommandOptions() {
        return List.of(
                SlashCommandOption.create(SlashCommandOptionType.LONG, "Geld", "Menge an Geld", true));
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
