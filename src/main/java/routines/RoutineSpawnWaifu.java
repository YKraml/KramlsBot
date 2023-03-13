package routines;

import actions.listeners.commands.Answer;
import de.kraml.Terminal;
import exceptions.MyOwnException;
import exceptions.messages.CouldNotSpawnWaifu;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import messages.MessageSender;
import messages.messages.ExceptionHappenedMessage;
import messages.messages.WaifuSpawn;
import messages.messages.WaifuToClaimWas;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import waifu.WaifuBuilder;
import waifu.WaifuSpawnManager;
import waifu.model.Waifu;

public class RoutineSpawnWaifu extends Routine {

  private final ScheduledExecutorService scheduledExecutorService;
  private final WaifuSpawnManager waifuSpawnManager;
  private final WaifuBuilder waifuBuilder;
  private final DiscordApi discordApi;
  private final MessageSender messageSender;

  public RoutineSpawnWaifu(ScheduledExecutorService scheduledExecutorService,
      WaifuSpawnManager waifuSpawnManager, WaifuBuilder waifuBuilder, DiscordApi discordApi,
      MessageSender messageSender) {
    this.scheduledExecutorService = scheduledExecutorService;
    this.waifuSpawnManager = waifuSpawnManager;
    this.waifuBuilder = waifuBuilder;
    this.discordApi = discordApi;
    this.messageSender = messageSender;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    for (Server server : discordApi.getServers()) {
      scheduledExecutorService.submit(() -> spawnWaifu(server));
    }
    return new Answer("Spawned Waifus");
  }

  private void spawnWaifu(Server server) {
    findSpawnChannel(server).ifPresent(textChannel -> {
      try {

        String serverId = server.getIdAsString();
        Optional<Waifu> waifuOptional = waifuSpawnManager.getWaifu(serverId);
        if (waifuOptional.isPresent()) {
          messageSender.send(new WaifuToClaimWas(waifuOptional.get()), textChannel);
        }

        Waifu newWaifu = waifuBuilder.createRandomWaifu();
        waifuSpawnManager.setWaifuToGuess(serverId, newWaifu);
        messageSender.send(new WaifuSpawn(newWaifu), textChannel);

        String message = "Waifu spawned. Server = ''{0}'', Waifu = ''{1}''";
        Terminal.printLine(MessageFormat.format(message, server.getName(), newWaifu.getId()));

      } catch (MyOwnException e) {
        messageSender.sendSafe(new ExceptionHappenedMessage(
            new MyOwnException(new CouldNotSpawnWaifu(server.getName()), e)), textChannel);
      }

    });
  }

  private Optional<TextChannel> findSpawnChannel(Server server) {

    for (ServerTextChannel serverTextChannel : server.getTextChannels()) {
      if ("spawn".equalsIgnoreCase(serverTextChannel.getName())) {
        return Optional.of(serverTextChannel);
      }
    }

    return Optional.empty();
  }

}
