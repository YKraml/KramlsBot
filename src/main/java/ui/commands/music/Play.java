package ui.commands.music;

import com.google.inject.Inject;
import domain.exceptions.MyOwnException;
import logic.routines.RoutineAddToQueueBuilder;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import ui.commands.ACommand;
import ui.commands.Answer;

import java.util.List;

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
        return getRoutineRunner().start(
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
