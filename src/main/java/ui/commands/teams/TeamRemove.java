package ui.commands.teams;

import com.google.inject.Inject;
import domain.Answer;
import domain.exceptions.MyOwnException;
import logic.routines.RoutineRemoveFromTeam;
import domain.PlayerLoader;
import domain.TeamLoader;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import ui.commands.ACommand;

import java.util.List;

public class TeamRemove extends ACommand {

    private final PlayerLoader playerLoader;
    private final TeamLoader teamLoader;

    @Inject
    public TeamRemove(PlayerLoader playerLoader, TeamLoader teamLoader) {
        this.playerLoader = playerLoader;
        this.teamLoader = teamLoader;
    }

    @Override
    public String getName() {
        return "teams-remove";
    }

    @Override
    public String getDescription() {
        return "Entfernt die angegebene Waifu aus dem angegebenen Team.";
    }

    @Override
    protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
                             List<SlashCommandInteractionOption> arguments) throws MyOwnException {
        String teamName = arguments.get(0).getStringValue().get();
        int waifuNumber = arguments.get(1).getLongValue().get().intValue();

        return getRoutineRunner().start(
                new RoutineRemoveFromTeam(user, teamName, waifuNumber, channel, playerLoader,
                        teamLoader));
    }

    @Override
    public List<SlashCommandOption> getSlashCommandOptions() {
        return List.of(SlashCommandOption.create(SlashCommandOptionType.STRING, "Teamname",
                        "Name des Teams, aus welchem die Waifu entfernt werden soll.", true),
                SlashCommandOption.create(SlashCommandOptionType.LONG, "WaifuId",
                        "Id der Waifu, die entfernt werden soll.", true));
    }

    @Override
    protected String getErrorMessage() {
        return "Konnte niemanden aus dem Team entfernen.";
    }

    @Override
    protected boolean isForAdmins() {
        return false;
    }


}
