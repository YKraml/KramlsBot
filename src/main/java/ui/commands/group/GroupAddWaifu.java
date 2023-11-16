package ui.commands.group;

import com.google.inject.Inject;
import domain.Answer;
import domain.exceptions.MyOwnException;
import logic.routines.RoutineAddWaifuToGroup;
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

public class GroupAddWaifu extends ACommand {

    private final PlayerLoader playerLoader;

    @Inject
    public GroupAddWaifu(PlayerLoader playerLoader) {
        this.playerLoader = playerLoader;
    }

    @Override
    public String getName() {
        return "groups-add";
    }

    @Override
    public String getDescription() {
        return "Fuegt die Waifu mit der Nummer n der angegebenen Gruppe hinzu.";
    }

    @Override
    protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
                             List<SlashCommandInteractionOption> arguments) throws MyOwnException {

        String groupName = arguments.get(0).getStringValue().get();
        int waifuId = arguments.get(1).getLongValue().get().intValue();

        return getRoutineRunner().start(
                new RoutineAddWaifuToGroup(user, groupName, waifuId, channel, playerLoader));
    }

    @Override
    public List<SlashCommandOption> getSlashCommandOptions() {
        return List.of(SlashCommandOption.create(SlashCommandOptionType.STRING, "Gruppenname",
                        "Name der Gruppe, in welche die Waifu soll.", true),
                SlashCommandOption.create(SlashCommandOptionType.LONG, "WaifuId",
                        "Id der Waifu, die in die Gruppe soll", true));
    }

    @Override
    protected String getErrorMessage() {
        return "Konnte keine Waifu zum Team hinzufügen.";
    }

    @Override
    protected boolean isForAdmins() {
        return false;
    }

}
