package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class WaifusAreDifferent extends MyMessageAbs {

  private final Player player;

  public WaifusAreDifferent(Player player) {
    this.player = player;
  }

  @Override
  public void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  public EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed(player.getNameTag() + ", die zwei Waifus sind nicht gleich");
  }
}
