package ui.commands.dungeon;

import com.google.inject.Inject;
import domain.Answer;
import domain.DungeonLoader;
import domain.exceptions.MyOwnException;
import java.util.List;
import logic.messages.MessageSender;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import ui.commands.ACommand;
import ui.messages.messages.DeleteDungeonsMessage;

public class DeleteDungeons extends ACommand {

  private final DungeonLoader dungeonLoader;
  private final MessageSender messageSender;

  @Inject
  public DeleteDungeons(DungeonLoader dungeonLoader, MessageSender messageSender) {
    this.dungeonLoader = dungeonLoader;
    this.messageSender = messageSender;
  }

  @Override
  public String getName() {
    return "dungeons-delete";
  }

  @Override
  public String getDescription() {
    return "Damit kannst du Dungeons auf dem Server löschen.";
  }

  @Override
  protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
      List<SlashCommandInteractionOption> arguments) throws MyOwnException {

    String serverId = server.getIdAsString();
    messageSender.send(new DeleteDungeonsMessage(dungeonLoader, messageSender, serverId), channel);
    return new Answer("'%s' requested the dungeon deletion list.".formatted(user.getName()));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of();
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte die Dungeonlöschliste nicht anzeigen.";
  }

  @Override
  protected boolean isForAdmins() {
    return true;
  }
}
