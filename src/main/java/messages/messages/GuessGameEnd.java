package messages.messages;

import actions.listeners.reaction.AnimeInfoReactionListenerBuilder;
import embeds.guess.GuessEndEmbed;
import exceptions.MyOwnException;
import messages.MyMessage;
import discord.Emojis;
import music.GuessingGame;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class GuessGameEnd extends MyMessage {

  private final GuessingGame game;
  private final AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder;

  public GuessGameEnd(GuessingGame game,
      AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder) {
    this.game = game;
    this.animeInfoReactionListenerBuilder = animeInfoReactionListenerBuilder;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    message.addReaction(Emojis.INFORMATION_SOURCE.getEmoji());
    message.addReactionAddListener(
        animeInfoReactionListenerBuilder.createAnimeInfoReactionListener(game.getAnime()));
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return new GuessEndEmbed(game.getAnime(), game.getSongName(), game.getUrl(),
        game.getPossibleAnswers());
  }


}
