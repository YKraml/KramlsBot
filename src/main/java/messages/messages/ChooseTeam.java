package messages.messages;

import actions.listeners.reaction.MyAbstractListListener;
import embeds.dungeon.TeamsListEmbed;
import exceptions.MyOwnException;
import messages.MyMessage;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import waifu.model.Player;
import waifu.model.dungeon.Team;

public class ChooseTeam extends MyMessage {

  private final Player player;

  public ChooseTeam(Player player) {
    super();
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
      protected void reactToCountEmoji(TextChannel textChannel, int listPosition, Server server,
          User user)
          throws MyOwnException {
        // TODO: 07.03.2023 Nachdem das Team ausgewählt wurde, sollte irgendwie die Klasse RoutineStartFight davon in Erfahrung kommen.
      }

      @Override
      protected void reactToTooHighCountEmoji(TextChannel textChannel, int listPosition)
          throws MyOwnException {

      }
    };

    message.addReactionAddListener(listener);
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return new TeamsListEmbed(player, 0);
  }
}
