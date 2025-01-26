package ui.messages.messages;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.Waifu;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import ui.messages.MyMessageAbs;

public class Merged extends MyMessageAbs {

  private final Player player;
  private final Waifu waifu1;

  public Merged(Player player, Waifu waifu) {
    this.player = player;
    this.waifu1 = waifu;
  }


  @Override
  public void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  public EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed(
        player.getNameTag() + ", du hast zwei Waifus verbunden. \"" + waifu1.getName()
            + "\" hat nun das Sternenlevel " + waifu1.getStarLevel());
  }
}
