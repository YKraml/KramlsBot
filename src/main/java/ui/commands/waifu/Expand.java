package ui.commands.waifu;

import com.google.inject.Inject;
import domain.Answer;
import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import logic.MessageSender;
import logic.waifu.PlayerLoader;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import ui.commands.ACommand;
import ui.messages.messages.ExpandedList;

import java.util.List;

public class Expand extends ACommand {

    private final PlayerLoader playerLoader;
    private final MessageSender messageSender;

    @Inject
    public Expand(PlayerLoader playerLoader, MessageSender messageSender) {
        this.playerLoader = playerLoader;
        this.messageSender = messageSender;
    }

    @Override
    public String getName() {
        return "expand";
    }

    @Override
    public String getDescription() {
        return "Kauft 10 Plaetze für deine Waifuliste.";
    }

    @Override
    protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
                             List<SlashCommandInteractionOption> arguments) throws MyOwnException {

        Player player = playerLoader.getPlayerByUser(user);

        int currentSize = player.getMaxWaifus();
        int costForUpgrade = currentSize * 100;

        player.getInventory().removeMoney(costForUpgrade);
        player.expandMaxWaifus(10);
        messageSender.send(new ExpandedList(player, costForUpgrade), channel);
        playerLoader.savePlayer(player);

        return new Answer("Someone expanded his WaifuList.");
    }

    @Override
    public List<SlashCommandOption> getSlashCommandOptions() {
        return List.of();
    }

    @Override
    protected String getErrorMessage() {
        return "Konnte die Waifu-Liste nicht erweitern.";
    }

    @Override
    protected boolean isForAdmins() {
        return false;
    }
}
