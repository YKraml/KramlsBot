package actions.commands.dungeon;

import actions.commands.ACommand;
import actions.commands.Answer;
import actions.commands.CommandType;
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
import waifu.model.Player;

public class CreateDungeon extends ACommand {

  private final DungeonLoader dungeonLoader;

  public CreateDungeon(DungeonLoader dungeonLoader) {
    super();
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
  protected Answer executeCommand(DiscordApi api, Server server, TextChannel channel, User user,
      Player player, List<SlashCommandInteractionOption> arguments) throws MyOwnException {
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
  public CommandType getCommandType() {
    return CommandType.DUNGEON;
  }

  @Override
  protected boolean isForAdmins() {
    return true;
  }
}
