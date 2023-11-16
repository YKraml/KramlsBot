package ui.commands.waifu;

import com.google.inject.Inject;
import domain.Answer;
import domain.exceptions.MyOwnException;
import logic.MessageSender;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import ui.commands.ACommand;
import ui.messages.messages.DeletedWaifuOverview;

import java.util.List;

public class Delete extends ACommand {

    private final WaifuLoader waifuLoader;
    private final MessageSender messageSender;
    private final PlayerLoader playerLoader;

    @Inject
    public Delete(WaifuLoader waifuLoader, MessageSender messageSender, PlayerLoader playerLoader) {
        this.waifuLoader = waifuLoader;
        this.messageSender = messageSender;
        this.playerLoader = playerLoader;
    }

    @Override
    public String getName() {
        return "waifus-delete";
    }

    @Override
    public String getDescription() {
        return "Gibt dir deine WaifuListe, wobei jeder Klick eine Waifu loescht.";
    }

    @Override
    protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
                             List<SlashCommandInteractionOption> arguments) throws MyOwnException {
        messageSender.send(new DeletedWaifuOverview(user, waifuLoader, messageSender, playerLoader),
                channel);
        return new Answer("Someone ordered his Delete-List");
    }

    @Override
    public List<SlashCommandOption> getSlashCommandOptions() {
        return List.of();
    }

    @Override
    protected String getErrorMessage() {
        return "Konnte die Waifu nicht löschen.";
    }

    @Override
    protected boolean isForAdmins() {
        return false;
    }
}
