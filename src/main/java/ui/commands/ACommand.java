package ui.commands;

import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotGetChannel;
import domain.exceptions.messages.CouldNotGetServer;
import domain.exceptions.messages.NotAdmin;
import logic.routines.RoutineRunner;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;

import java.util.List;

public abstract class ACommand {

    private final static long ADMIN_VALUE = 1 << 3;
    private RoutineRunner routineRunner;

    public abstract String getName();

    public abstract String getDescription();

    public abstract List<SlashCommandOption> getSlashCommandOptions();

    public Answer execute(SlashCommandCreateEvent event)
            throws MyOwnException {

        DiscordApi api = event.getApi();
        SlashCommandInteraction interaction = event.getSlashCommandInteraction();
        TextChannel channel = interaction.getChannel()
                .orElseThrow(() -> new MyOwnException(new CouldNotGetChannel(), null));
        Server server = interaction.getServer()
                .orElseThrow(() -> new MyOwnException(new CouldNotGetServer(), null));
        User user = interaction.getUser();
        List<SlashCommandInteractionOption> arguments = event.getSlashCommandInteraction()
                .getArguments();

        if (isForAdmins() && !userIsAdmin(server, user)) {
            throw new MyOwnException(new NotAdmin(user.getName()), null);
        }

        return execute(api, server, channel, user, arguments);
    }

    protected RoutineRunner getRoutineRunner() {
        return routineRunner;
    }

    public void setRoutineRunner(RoutineRunner routineRunner) {
        this.routineRunner = routineRunner;
    }

    protected abstract Answer execute(DiscordApi api, Server server, TextChannel channel,
                                      User user,
                                      List<SlashCommandInteractionOption> arguments) throws MyOwnException;

    protected abstract boolean isForAdmins();

    protected abstract String getErrorMessage();

    private boolean userIsAdmin(Server server, User user) {
        return user.getRoles(server).stream()
                .flatMap(role -> role.getPermissions().getAllowedPermission().stream())
                .map(PermissionType::getValue)
                .anyMatch(value -> value == ADMIN_VALUE);
    }

}
