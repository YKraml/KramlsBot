package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.dungeon.Team;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import ui.embeds.dungeon.TeamsListEmbed;
import ui.messages.MyMessage;
import ui.reaction.MyAbstractListListener;

public class ChooseTeam extends MyMessage {

    private final Player player;

    public ChooseTeam(Player player) {
        this.player = player;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {

        MyAbstractListListener<Team> listener = new MyAbstractListListener<>(player.getTeamList()) {
            @Override
            protected void updateMessage(Message message, int page) throws MyOwnException {
                message.edit(new TeamsListEmbed(player, page));
            }

            @Override
            protected void reactToCountEmoji(Server server, TextChannel textChannel, User user,
                                             int listPosition)
                    throws MyOwnException {
                // TODO: 07.03.2023 Nachdem das Team ausgewählt wurde, sollte irgendwie die Klasse RoutineStartFight davon in Erfahrung kommen.
            }

            @Override
            protected void reactToTooHighCountEmoji(TextChannel textChannel, int listPosition) {
                //Just ignore.
            }
        };

        message.addReactionAddListener(listener);
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return new TeamsListEmbed(player, 0);
    }
}
