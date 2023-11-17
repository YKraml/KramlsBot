package ui.commands.music;

import com.google.inject.Inject;
import domain.Answer;
import domain.exceptions.MyOwnException;
import domain.queue.Queue;
import logic.MusicPlayerManager;
import logic.messages.MessageSender;
import domain.PlayerLoader;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import ui.commands.ACommand;
import ui.messages.messages.SongQueueMessage;

import java.util.List;

public class ShowQueue extends ACommand {

    private final MusicPlayerManager musicPlayerManager;
    private final PlayerLoader playerLoader;
    private final MessageSender messageSender;

    @Inject
    public ShowQueue(MusicPlayerManager musicPlayerManager, PlayerLoader playerLoader,
                     MessageSender messageSender) {
        this.musicPlayerManager = musicPlayerManager;
        this.playerLoader = playerLoader;
        this.messageSender = messageSender;
    }

    @Override
    public String getName() {
        return "queue";
    }

    @Override
    public String getDescription() {
        return "Zeigt die Songwarteschlange an.";
    }

    @Override
    protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
                             List<SlashCommandInteractionOption> arguments) throws MyOwnException {

        Queue queue = musicPlayerManager.getQueueByServer(server);
        messageSender.send(new SongQueueMessage(queue, musicPlayerManager, playerLoader), channel);

        return new Answer("Someone looked at the music queue");
    }

    @Override
    public List<SlashCommandOption> getSlashCommandOptions() {
        return List.of();
    }

    @Override
    protected String getErrorMessage() {
        return "Konnte die Queue nicht anzeigen.";
    }

    @Override
    protected boolean isForAdmins() {
        return false;
    }
}
