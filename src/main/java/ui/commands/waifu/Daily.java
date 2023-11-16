package ui.commands.waifu;

import com.google.inject.Inject;
import domain.exceptions.MyOwnException;
import logic.routines.RoutineCollectDaily;
import logic.waifu.PlayerLoader;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import ui.commands.ACommand;
import domain.Answer;

import java.util.List;

public class Daily extends ACommand {

    private final PlayerLoader playerLoader;

    @Inject
    public Daily(PlayerLoader playerLoader) {
        this.playerLoader = playerLoader;
    }

    @Override
    public String getName() {
        return "daily";
    }

    @Override
    public String getDescription() {
        return "Du bekommst 1000 Euro. Nur einmal pro Tag anwendbar.";
    }

    @Override
    protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
                             List<SlashCommandInteractionOption> arguments) throws MyOwnException {
        return getRoutineRunner().start(new RoutineCollectDaily(channel, user, playerLoader));
    }

    @Override
    public List<SlashCommandOption> getSlashCommandOptions() {
        return List.of();
    }

    @Override
    protected String getErrorMessage() {
        return "Konnte die tägliche Belohnng nicht einsammeln.";
    }

    @Override
    protected boolean isForAdmins() {
        return false;
    }

}
