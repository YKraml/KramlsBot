package ui.commands.teams;

import com.google.inject.Inject;
import domain.exceptions.MyOwnException;
import logic.waifu.DungeonLoader;
import logic.waifu.JikanFetcher;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import ui.commands.ACommand;
import ui.commands.Answer;
import ui.messages.MessageSender;
import ui.messages.messages.TeamsOverview;

import java.util.List;

public class TeamsList extends ACommand {

    private final DungeonLoader dungeonLoader;
    private final PlayerLoader playerLoader;
    private final MessageSender messageSender;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;

    @Inject
    public TeamsList(DungeonLoader dungeonLoader, PlayerLoader playerLoader,
                     MessageSender messageSender, WaifuLoader waifuLoader, JikanFetcher jikanFetcher) {
        this.dungeonLoader = dungeonLoader;
        this.playerLoader = playerLoader;
        this.messageSender = messageSender;
        this.waifuLoader = waifuLoader;
        this.jikanFetcher = jikanFetcher;
    }

    @Override
    public String getName() {
        return "teams";
    }

    @Override
    public String getDescription() {
        return "Zeigt deine Teams an.";
    }

    @Override
    protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
                             List<SlashCommandInteractionOption> arguments) throws MyOwnException {
        messageSender.send(new TeamsOverview(user, dungeonLoader, playerLoader, messageSender,
                waifuLoader, jikanFetcher), channel);

        return new Answer("Someone looked at his teams.");
    }

    @Override
    public List<SlashCommandOption> getSlashCommandOptions() {
        return List.of();
    }

    @Override
    protected String getErrorMessage() {
        return "Konnte die Teamliste nicht anzeigen.";
    }

    @Override
    protected boolean isForAdmins() {
        return false;
    }


}
