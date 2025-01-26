package ui.reaction;

import domain.exceptions.MyOwnException;
import logic.routines.RoutineRevealBuilder;
import logic.routines.RoutineRunner;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class StartGuessingGameListener extends MyAbstractReactionListener {

  private final String infoEmoji;
  private final RoutineRunner routineRunner;
  private final RoutineRevealBuilder routineRevealBuilder;

  public StartGuessingGameListener(String infoEmoji, RoutineRunner routineRunner,
      RoutineRevealBuilder routineRevealBuilder) {
    this.infoEmoji = infoEmoji;
    this.routineRunner = routineRunner;
    this.routineRevealBuilder = routineRevealBuilder;
  }

  @Override
  protected void startRoutine(DiscordApi discordApi, Server server, TextChannel textChannel,
      Message message, User user, Emoji emoji) throws MyOwnException {

    if (!emoji.equalsEmoji(infoEmoji) || user.isBot()) {
      return;
    }

    message.removeReactionByEmoji(user, emoji);
    routineRunner.start(
        routineRevealBuilder.createRoutineReveal(message.getServer().get(), textChannel));
  }
}
