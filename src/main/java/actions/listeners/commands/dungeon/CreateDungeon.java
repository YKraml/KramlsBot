package actions.listeners.commands.dungeon;

import actions.listeners.commands.ACommand;
import actions.listeners.commands.Answer;
import com.google.inject.Inject;
import exceptions.MyOwnException;
import java.util.List;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import routines.RoutineCreateDungeon;
import waifu.loader.DungeonLoader;

public class CreateDungeon extends ACommand {

  private final DungeonLoader dungeonLoader;

  @Inject
  public CreateDungeon(DungeonLoader dungeonLoader) {
    this.dungeonLoader = dungeonLoader;
  }

  @Override
  public String getName() {
    return "" + "dungeons-create";
  }

  @Override
  public String getDescription() {
    return "Erzeugt einen Dungeon mit der angegebenen Schwierigkeit und Namen.";
  }

  @Override
  protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
      List<SlashCommandInteractionOption> arguments) throws MyOwnException {
    int difficulty = arguments.get(0).getLongValue().get().intValue();
    String name = arguments.get(1).getStringValue().get();
    return getRoutineRunner().startRoutine(
        new RoutineCreateDungeon(server, difficulty, name, channel, dungeonLoader));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of(SlashCommandOption.create(SlashCommandOptionType.LONG, "Schwierigkeit",
            "Die Schwierigkeit des Dungeons.", true),
        SlashCommandOption.create(SlashCommandOptionType.STRING, "Name", "Name des Dungeons",
            true));
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte keinen Dungeon erzeugen.";
  }

  @Override
  protected boolean isForAdmins() {
    return true;
  }
}
