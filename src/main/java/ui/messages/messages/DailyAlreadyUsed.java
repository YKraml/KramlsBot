package ui.messages.messages;

import domain.exceptions.MyOwnException;
import ui.messages.MyMessage;
import domain.waifu.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class DailyAlreadyUsed extends MyMessage {

  private final Player player;
  private final String newDate;

  public DailyAlreadyUsed(Player player, String newDate) {
    this.player = player;
    this.newDate = newDate;
  }


  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return convertStringToEmbed(
        player.getNameTag() + ", du hast dein Daily schon heute (" + newDate + ") benutzt.");
  }
}
