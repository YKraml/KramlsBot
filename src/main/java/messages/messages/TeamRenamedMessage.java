package messages.messages;

import exceptions.MyOwnException;
import messages.MyMessage;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import waifu.model.Player;

public class TeamRenamedMessage extends MyMessage {


  private final Player player;
  private final String oldName;
  private final String newName;

  public TeamRenamedMessage(Player player, String oldName, String newName) {
    super();
    this.player = player;
    this.oldName = oldName;
    this.newName = newName;
  }

  @Override
  protected void startRoutine(Message message) throws MyOwnException {
    //Ignore.
  }

  @Override
  protected EmbedBuilder getContent() throws MyOwnException {
    return this.convertStringToEmbed(
        player.getNameTag() + ", das Team \"" + oldName + " heisst jetzt \"" + newName + "\"");
  }
}
