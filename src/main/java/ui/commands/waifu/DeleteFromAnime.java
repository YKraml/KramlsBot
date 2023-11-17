package ui.commands.waifu;

import com.google.inject.Inject;
import domain.Answer;
import domain.exceptions.MyOwnException;
import logic.messages.MessageSender;
import logic.routines.RoutineDeleteWaifusFromAnime;
import domain.PlayerLoader;
import domain.WaifuLoader;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import ui.commands.ACommand;

import java.util.List;

public class DeleteFromAnime extends ACommand {

    private final WaifuLoader waifuLoader;
    private final PlayerLoader playerLoader;
    private final MessageSender messageSender;

    @Inject
    public DeleteFromAnime(WaifuLoader waifuLoader, PlayerLoader playerLoader,
                           MessageSender messageSender) {
        this.waifuLoader = waifuLoader;
        this.playerLoader = playerLoader;
        this.messageSender = messageSender;
    }

    @Override
    public String getName() {
        return "waifus-delete-from-anime";
    }

    @Override
    public String getDescription() {
        return "Loescht alle Waifus, die aus dem angegebenen Anime kommen.";
    }

    @Override
    protected String getErrorMessage() {
        return "Konnte die Waifus vom gegebenen Anime nicht löschen.";
    }

    @Override
    protected boolean isForAdmins() {
        return false;
    }

    @Override
    protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
                             List<SlashCommandInteractionOption> arguments) throws MyOwnException {

        String anime = arguments.get(0).getStringValue().get();
        return getRoutineRunner().start(
                new RoutineDeleteWaifusFromAnime(waifuLoader, playerLoader, user, anime, channel,
                        messageSender));
    }

    @Override
    public List<SlashCommandOption> getSlashCommandOptions() {
        return List.of(SlashCommandOption.create(SlashCommandOptionType.STRING, "Animename",
                "Name des Animes, vom welchem alle Waifus gelöscht werden sollen.", true));
    }
}
