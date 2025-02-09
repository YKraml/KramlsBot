package ui.commands.group;

import com.google.inject.Inject;
import domain.Answer;
import domain.PlayerLoader;
import domain.exceptions.MyOwnException;
import java.util.List;
import logic.messages.MessageSender;
import logic.routines.RoutineCreateGroup;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import ui.commands.ACommand;

public class GroupCreate extends ACommand {

  private final PlayerLoader playerLoader;
  private final MessageSender messageSender;

  @Inject
  public GroupCreate(PlayerLoader playerLoader, MessageSender messageSender) {
    this.playerLoader = playerLoader;
    this.messageSender = messageSender;
  }

  @Override
  public String getName() {
    return "groups-create";
  }

  @Override
  public String getDescription() {
    return "Erstellt eine neue Gruppe mit dem gegebenem Name.";
  }

  @Override
  protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
      List<SlashCommandInteractionOption> arguments) throws MyOwnException {

    String groupName = arguments.get(0).getStringValue().get();

    return getRoutineRunner().start(
        new RoutineCreateGroup(groupName, user, playerLoader, messageSender, channel));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of(SlashCommandOption.create(SlashCommandOptionType.STRING, "Gruppenname",
        "Name der zu erstellenden Gruppe.", true));
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte keine Gruppe erstellen.";
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }

}
