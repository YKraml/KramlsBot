package routines;

import actions.listeners.commands.Answer;
import discord.Emojis;
import exceptions.MyOwnException;
import messages.MessageSender;
import messages.messages.ChooseTeam;
import messages.messages.FightAcceptet;
import messages.messages.FightDeclined;
import messages.messages.FightRequest;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import waifu.loader.PlayerLoader;
import waifu.model.Player;

public class RoutineStartFight extends Routine {

  private final User user;
  private final User userEnemy;
  private final long money;
  private final long stardust;
  private final long morphStones;
  private final MessageSender messageSender;
  private final TextChannel textChannel;
  private final PlayerLoader playerLoader;


  public RoutineStartFight(User user, User userEnemy, long money, long stardust, long morphStones,
      MessageSender messageSender, TextChannel textChannel, PlayerLoader playerLoader) {
    this.user = user;
    this.userEnemy = userEnemy;
    this.money = money;
    this.stardust = stardust;
    this.morphStones = morphStones;
    this.messageSender = messageSender;
    this.textChannel = textChannel;
    this.playerLoader = playerLoader;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {

    textChannel.sendMessage(userEnemy.getMentionTag());
    Message fightRequestMessage = messageSender.send(
        new FightRequest(user, userEnemy, money, stardust, morphStones), textChannel);

    fightRequestMessage.addReactionAddListener(event -> handleEvent(fightRequestMessage, event));

    return new Answer(
        "%s hat %s zum Kampf herausgefordert.".formatted(user.getName(), userEnemy.getName()));
  }

  private void handleEvent(Message fightRequestMessage, ReactionAddEvent event) {
    if (event.getUser().isPresent() && event.getUser().get().isBot()) {
      return;
    }

    if (event.getEmoji().equalsEmoji(Emojis.X.getEmoji())) {
      fightRequestMessage.delete();
      textChannel.sendMessage(user.getMentionTag());
      try {
        messageSender.send(new FightDeclined(user, userEnemy), textChannel);
      } catch (MyOwnException ignored) {
        //Ignore.
      }
    }

    if (event.getEmoji().equalsEmoji(Emojis.WHITE_CHECK_MARK.getEmoji())) {
      fightRequestMessage.delete();
      textChannel.sendMessage(user.getMentionTag());
      try {
        messageSender.send(new FightAcceptet(user, userEnemy, money, stardust, morphStones),
            textChannel);

        Player player = playerLoader.getPlayerByUser(user);
        Player playerEnemy = playerLoader.getPlayerByUser(userEnemy);

        messageSender.send(new ChooseTeam(player), textChannel);
        messageSender.send(new ChooseTeam(playerEnemy), textChannel);


      } catch (MyOwnException ignored) {
        //Ignore.
      }
    }
  }

}
