package ui.commands.waifu;

import com.google.inject.Inject;
import domain.exceptions.MyOwnException;
import logic.routines.RoutineGiveMoney;
import logic.waifu.PlayerLoader;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import ui.commands.ACommand;
import ui.commands.Answer;
import ui.messages.MessageSender;

import java.util.List;

public class GiveMoney extends ACommand {

    private final PlayerLoader playerLoader;
    private final MessageSender messageSender;

    @Inject
    public GiveMoney(PlayerLoader playerLoader, MessageSender messageSender) {
        this.playerLoader = playerLoader;
        this.messageSender = messageSender;
    }

    @Override
    public String getName() {
        return "give-money";
    }

    @Override
    public String getDescription() {
        return "Gib dem angegebenen Spieler N viel Euro";
    }

    @Override
    protected String getErrorMessage() {
        return "Konnte kein Geld übertragen.";
    }

    @Override
    protected boolean isForAdmins() {
        return false;
    }

    @Override
    protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
                             List<SlashCommandInteractionOption> arguments) throws MyOwnException {

        User receiver = arguments.get(0).getUserValue().get();
        int money = arguments.get(1).getLongValue().get().intValue();

        return getRoutineRunner().start(
                new RoutineGiveMoney(playerLoader, channel, user, receiver, money, messageSender));
    }

    @Override
    public List<SlashCommandOption> getSlashCommandOptions() {
        return List.of(SlashCommandOption.create(SlashCommandOptionType.USER, "Nutzer",
                        "Nutzer, welchem du Geld schenken willst", true),
                SlashCommandOption.create(SlashCommandOptionType.LONG, "Geld",
                        "Menge an Geld, die du verschenken willst.", true));
    }
}
