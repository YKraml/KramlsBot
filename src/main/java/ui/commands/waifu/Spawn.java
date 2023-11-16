package ui.commands.waifu;

import com.google.inject.Inject;
import domain.Answer;
import domain.exceptions.MyOwnException;
import logic.routines.RoutineSpawnWaifuCommandBuilder;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import ui.commands.ACommand;

import java.util.List;

public class Spawn extends ACommand {

    private final RoutineSpawnWaifuCommandBuilder routineSpawnWaifuCommandBuilder;

    @Inject
    public Spawn(RoutineSpawnWaifuCommandBuilder routineSpawnWaifuCommandBuilder) {
        this.routineSpawnWaifuCommandBuilder = routineSpawnWaifuCommandBuilder;
    }

    @Override
    public String getName() {
        return "spawn";
    }

    @Override
    public String getDescription() {
        return "Du erhaelst eine Waifu fuer 1000 Euro.";
    }

    @Override
    protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
                             List<SlashCommandInteractionOption> arguments) throws MyOwnException {

        return getRoutineRunner().start(
                routineSpawnWaifuCommandBuilder.createRoutineSpawnWaifuCommand(channel, user));
    }

    @Override
    public List<SlashCommandOption> getSlashCommandOptions() {
        return List.of();
    }


    @Override
    protected String getErrorMessage() {
        return "Konnte keine neue Waifu spawnen.";
    }

    @Override
    protected boolean isForAdmins() {
        return false;
    }
}
