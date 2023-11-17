package ui.commands.group;

import com.google.inject.Inject;
import domain.Answer;
import domain.GroupLoader;
import domain.PlayerLoader;
import domain.exceptions.MyOwnException;
import logic.messages.MessageSender;
import logic.routines.RoutineRemoveFromGroup;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import ui.commands.ACommand;

import java.util.List;

public class GroupRemoveWaifu extends ACommand {

    private final GroupLoader groupLoader;
    private final MessageSender messageSender;
    private final PlayerLoader playerLoader;

    @Inject
    public GroupRemoveWaifu(GroupLoader groupLoader, MessageSender messageSender,
                            PlayerLoader playerLoader) {
        this.groupLoader = groupLoader;
        this.messageSender = messageSender;
        this.playerLoader = playerLoader;
    }

    @Override
    public String getName() {
        return "groups-remove";
    }

    @Override
    public String getDescription() {
        return "Entfert die angegebene Waifu aus der angegebenen Gruppe.";
    }

    @Override
    protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
                             List<SlashCommandInteractionOption> arguments) throws MyOwnException {

        String groupName = arguments.get(0).getStringValue().get();
        int waifuNumber = arguments.get(1).getLongValue().get().intValue();

        return getRoutineRunner().start(
                new RoutineRemoveFromGroup(user, groupName, waifuNumber, channel, groupLoader,
                        messageSender, playerLoader));
    }

    @Override
    public List<SlashCommandOption> getSlashCommandOptions() {
        return List.of(SlashCommandOption.create(SlashCommandOptionType.STRING, "Gruppenname",
                        "Name der Gruppe, aus welcher die Waifu entfernt werden soll.", true),
                SlashCommandOption.create(SlashCommandOptionType.LONG, "WaifuId",
                        "Id der Waifu, die entfernt werden soll", true));
    }

    @Override
    protected String getErrorMessage() {
        return "Konnte niemanden vom Team entfernen.";
    }

    @Override
    protected boolean isForAdmins() {
        return false;
    }

}
