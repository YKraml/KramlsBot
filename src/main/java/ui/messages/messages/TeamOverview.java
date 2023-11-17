package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.dungeon.Team;
import logic.MessageSender;
import logic.waifu.DungeonLoader;
import logic.waifu.JikanFetcher;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.embeds.dungeon.TeamEmbed;
import ui.messages.MyMessageAbs;
import ui.reaction.TeamEditListener;
import util.Emojis;

public class TeamOverview extends MyMessageAbs {

    private final Team team;
    private final DungeonLoader dungeonLoader;
    private final PlayerLoader playerLoader;
    private final MessageSender messageSender;
    private final WaifuLoader waifuLoader;
    private final JikanFetcher jikanFetcher;

    public TeamOverview(Team team, DungeonLoader dungeonLoader, PlayerLoader playerLoader,
                        MessageSender messageSender, WaifuLoader waifuLoader, JikanFetcher jikanFetcher) {
        this.team = team;
        this.dungeonLoader = dungeonLoader;
        this.playerLoader = playerLoader;
        this.messageSender = messageSender;
        this.waifuLoader = waifuLoader;
        this.jikanFetcher = jikanFetcher;
    }

    @Override
    public void startRoutine(Message message) throws MyOwnException {
        message.addReaction(Emojis.MONEY_BAG.getEmoji());
        message.addReaction(Emojis.HOSPITAL.getEmoji());
        message.addReaction(Emojis.LEFTWARDS_ARROW_WITH_HOOK.getEmoji());
        message.addReaction(Emojis.CAMPING.getEmoji());
        message.addReaction(Emojis.ARROWS_COUNTERCLOCKWISE.getEmoji());
        addCountEmojis(message, team.getFighters().size());
        message.addReactionAddListener(new TeamEditListener(team, playerLoader, dungeonLoader,
                messageSender, waifuLoader, jikanFetcher));
    }

    @Override
    public EmbedBuilder getContent() throws MyOwnException {
        return new TeamEmbed(team);
    }

}
