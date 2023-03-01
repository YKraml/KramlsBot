package messages.messages;

import actions.listeners.reaction.TeamEditListener;
import discord.Emojis;
import embeds.dungeon.TeamEmbed;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.MyMessage;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.loader.DungeonLoader;
import waifu.loader.PlayerLoader;
import waifu.model.dungeon.Team;

public class TeamOverview extends MyMessage {
    private final Team team;
    private final DungeonLoader dungeonLoader;
    private final PlayerLoader playerLoader;
    private final MessageSender messageSender;

    public TeamOverview(Team team, DungeonLoader dungeonLoader, PlayerLoader playerLoader,
        MessageSender messageSender) {
        super();
        this.team = team;
        this.dungeonLoader = dungeonLoader;
        this.playerLoader = playerLoader;
        this.messageSender = messageSender;
    }

    @Override
    protected void startRoutine(Message message) throws MyOwnException {
        message.addReaction(Emojis.MONEY_BAG.getEmoji());
        message.addReaction(Emojis.HOSPITAL.getEmoji());
        message.addReaction(Emojis.LEFTWARDS_ARROW_WITH_HOOK.getEmoji());
        message.addReaction(Emojis.CAMPING.getEmoji());
        message.addReaction(Emojis.ARROWS_COUNTERCLOCKWISE.getEmoji());
        message.addReactionAddListener(new TeamEditListener(team, playerLoader, dungeonLoader,
            messageSender));
    }

    @Override
    protected EmbedBuilder getContent() throws MyOwnException {
        return new TeamEmbed(team);
    }

}
