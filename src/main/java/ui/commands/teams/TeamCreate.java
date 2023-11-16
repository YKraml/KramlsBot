package ui.commands.teams;

import com.google.inject.Inject;
import domain.Answer;
import domain.exceptions.MyOwnException;
import logic.routines.RoutineCreateTeam;
import logic.waifu.PlayerLoader;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import ui.commands.ACommand;

import java.util.List;

public class TeamCreate extends ACommand {

    private final PlayerLoader playerLoader;

    @Inject
    public TeamCreate(PlayerLoader playerLoader) {
        this.playerLoader = playerLoader;
    }

    @Override
    public String getName() {
        return "teams-create";
    }

    @Override
    public String getDescription() {
        return "Erstellt ein neues Team. Das erste ist kostenlos. Die darauffolgenden kosten immer mehr.";
    }

    @Override
    protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
                             List<SlashCommandInteractionOption> arguments) throws MyOwnException {
        String teamName = arguments.get(0).getStringValue().get();

        return getRoutineRunner().start(
                new RoutineCreateTeam(channel, user, teamName, playerLoader));
    }

    @Override
    public List<SlashCommandOption> getSlashCommandOptions() {
        return List.of(SlashCommandOption.create(SlashCommandOptionType.STRING, "Teamname",
                "Name des Teams, das erstellt werden soll.", true));
    }

    @Override
    protected String getErrorMessage() {
        return "Konnte kein Team erstellen.";
    }

    @Override
    protected boolean isForAdmins() {
        return false;
    }

}
