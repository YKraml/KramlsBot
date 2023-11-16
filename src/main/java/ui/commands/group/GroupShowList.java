package ui.commands.group;

import com.google.inject.Inject;
import domain.exceptions.MyOwnException;
import logic.routines.RoutineShowGroupListBuilder;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import ui.commands.ACommand;
import domain.Answer;

import java.util.List;

public class GroupShowList extends ACommand {

    private final RoutineShowGroupListBuilder routineShowGroupListBuilder;

    @Inject
    public GroupShowList(RoutineShowGroupListBuilder routineShowGroupListBuilder) {
        this.routineShowGroupListBuilder = routineShowGroupListBuilder;
    }

    @Override
    public String getName() {
        return "groups";
    }

    @Override
    public String getDescription() {
        return "Zeigt alle eine Gruppen an";
    }

    @Override
    protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
                             List<SlashCommandInteractionOption> arguments) throws MyOwnException {
        return getRoutineRunner().start(
                routineShowGroupListBuilder.createRoutineShowGroupList(channel, user));
    }

    @Override
    public List<SlashCommandOption> getSlashCommandOptions() {
        return List.of();
    }

    @Override
    protected String getErrorMessage() {
        return "Konnte die Gruppenliste nicht anzeigen.";
    }

    @Override
    protected boolean isForAdmins() {
        return false;
    }

}
