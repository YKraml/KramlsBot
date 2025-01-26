package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class DailyAlreadyUsed extends MyMessageAbs {

  private final Player player;
  private final String newDate;

  public DailyAlreadyUsed(Player player, String newDate) {
    this.player = player;
    this.newDate = newDate;
  }


  @Override
  public void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  public EmbedBuilder getContent() throws MyOwnException {
    return convertStringToEmbed(
        player.getNameTag() + ", du hast dein Daily schon heute (" + newDate + ") benutzt.");
  }
}
