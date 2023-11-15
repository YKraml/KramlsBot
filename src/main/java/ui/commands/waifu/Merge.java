package ui.commands.waifu;

import ui.commands.ACommand;
import ui.commands.Answer;
import com.google.inject.Inject;
import domain.exceptions.MyOwnException;
import java.util.List;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteractionOption;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import logic.routines.RoutineMergeWaifus;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;

public class Merge extends ACommand {

  private final WaifuLoader waifuLoader;
  private final PlayerLoader playerLoader;

  @Inject
  public Merge(WaifuLoader waifuLoader, PlayerLoader playerLoader) {
    this.waifuLoader = waifuLoader;
    this.playerLoader = playerLoader;
  }

  @Override
  public String getName() {
    return "waifus-merge";
  }

  @Override
  public String getDescription() {
    return "Verschmelzt zwei Waifus. Die Zweite geht dabei verloren.";
  }

  @Override
  protected Answer execute(DiscordApi api, Server server, TextChannel channel, User user,
                           List<SlashCommandInteractionOption> arguments) throws MyOwnException {

    int waifuId1 = arguments.get(0).getLongValue().get().intValue();
    int waifuId2 = arguments.get(1).getLongValue().get().intValue();

    return getRoutineRunner().start(
        new RoutineMergeWaifus(channel, user, waifuId1, waifuId2, waifuLoader, playerLoader));
  }

  @Override
  public List<SlashCommandOption> getSlashCommandOptions() {
    return List.of(SlashCommandOption.create(SlashCommandOptionType.LONG, "StarWaifuId",
            "Id der zu erhaltenen Waifu", true),
        SlashCommandOption.create(SlashCommandOptionType.LONG, "DeleteWaifuId",
            "Id der zu löschenden Waifu", true));
  }

  @Override
  protected String getErrorMessage() {
    return "Konnte die Waifus nicht miteinander verbinden.";
  }

  @Override
  protected boolean isForAdmins() {
    return false;
  }
}