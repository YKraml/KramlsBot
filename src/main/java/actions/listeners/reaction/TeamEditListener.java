package actions.listeners.reaction;

import actions.listeners.reaction.util.MyAbstractReactionListener;
import embeds.dungeon.TeamEmbed;
import exceptions.MyOwnException;
import discord.Emojis;
import exceptions.messages.TeamIsInDungeon;
import messages.MessageSender;
import messages.messages.ButtonNotForYou;
import messages.messages.DungeonList;
import messages.messages.TeamGavePocketMessage;
import waifu.loader.DungeonLoader;
import waifu.loader.PlayerLoader;
import waifu.model.dungeon.Dungeon;
import waifu.model.dungeon.Team;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.listener.message.reaction.ReactionAddListener;

import java.util.List;

public class TeamEditListener extends MyAbstractReactionListener implements ReactionAddListener {

  private final Team team;
  private final PlayerLoader playerLoader;
  private final DungeonLoader dungeonLoader;
  private final MessageSender messageSender;

  public TeamEditListener(Team team, PlayerLoader playerLoader, DungeonLoader dungeonLoader,
      MessageSender messageSender) {
    this.team = team;
    this.playerLoader = playerLoader;
    this.dungeonLoader = dungeonLoader;
    this.messageSender = messageSender;
  }

  @Override
  protected void startRoutine(DiscordApi discordApi, Server server, TextChannel textChannel,
      Message message, User user, Emoji emoji) throws MyOwnException {

    if (!user.getIdAsString().equals(team.getPlayer().getId())) {
      messageSender.send(new ButtonNotForYou(user.getMentionTag(), team.getPlayer().getNameTag()),
          textChannel);
      return;
    }

    if (emoji.equalsEmoji(Emojis.MONEY_BAG.getEmoji())) {
      this.removeMoney(textChannel, message);
    } else if (emoji.equalsEmoji(Emojis.LEFTWARDS_ARROW_WITH_HOOK.getEmoji())) {
      this.returnFromDungeon(textChannel, message);
    } else if (emoji.equalsEmoji(Emojis.CAMPING.getEmoji())) {
      List<Dungeon> dungeonList = dungeonLoader.getAllDungeonsFromServer(server.getIdAsString());
      messageSender.send(new DungeonList(team, dungeonList, playerLoader, messageSender),
          textChannel);
    } else if (emoji.equalsEmoji(Emojis.ARROWS_COUNTERCLOCKWISE.getEmoji())) {
      message.edit(new TeamEmbed(team));
    } else if (emoji.equalsEmoji(Emojis.HOSPITAL.getEmoji())) {
      team.heal();
      message.edit(new TeamEmbed(team));
    }

    playerLoader.savePlayer(team.getPlayer());
  }

  private void returnFromDungeon(TextChannel textChannel, Message message) {
    textChannel.sendMessage(
        "%s, das Team '%s' hat den Dungeon '%s' verlassen.".formatted(team.getPlayer().getNameTag(),
            team.getName(), team.getCurrentDungeon().getName()));
    team.leavesDungeon();
    message.edit(new TeamEmbed(team));
  }

  private void removeMoney(TextChannel textChannel, Message message) throws MyOwnException {

    if (team.isInDungeon()) {
      throw new MyOwnException(new TeamIsInDungeon(team), null);
    }

    messageSender.send(new TeamGavePocketMessage(team), textChannel);
    team.givePocketToPlayer();
    message.edit(new TeamEmbed(team));
  }
}
