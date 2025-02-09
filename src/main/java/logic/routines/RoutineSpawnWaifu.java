package logic.routines;

import domain.Answer;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotSpawnWaifu;
import domain.waifu.Waifu;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import logic.messages.MessageSender;
import logic.waifu.WaifuBuilder;
import logic.waifu.WaifuSpawnManager;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import util.Terminal;

public class RoutineSpawnWaifu extends Routine {

  private final ScheduledExecutorService scheduledExecutorService;
  private final WaifuSpawnManager waifuSpawnManager;
  private final WaifuBuilder waifuBuilder;
  private final DiscordApi discordApi;
  private final MessageSender messageSender;
  private final Terminal terminal;

  public RoutineSpawnWaifu(ScheduledExecutorService scheduledExecutorService,
      WaifuSpawnManager waifuSpawnManager, WaifuBuilder waifuBuilder, DiscordApi discordApi,
      MessageSender messageSender, Terminal terminal) {
    this.scheduledExecutorService = scheduledExecutorService;
    this.waifuSpawnManager = waifuSpawnManager;
    this.waifuBuilder = waifuBuilder;
    this.discordApi = discordApi;
    this.messageSender = messageSender;
    this.terminal = terminal;
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
          messageSender.sendWaifuToClaimWas(textChannel, waifuOptional.get());
        }

        Waifu newWaifu = waifuBuilder.createRandomWaifu();
        waifuSpawnManager.setWaifuToGuess(serverId, newWaifu);
        messageSender.sendWaifuSpawn(textChannel, newWaifu);

        String message = "Waifu spawned. Server = ''{0}'', Waifu = ''{1}''";
        terminal.printLine(MessageFormat.format(message, server.getName(), newWaifu.getId()));

      } catch (MyOwnException e) {
        messageSender.sendSafeExceptionHappenedMessage(textChannel,
            new MyOwnException(new CouldNotSpawnWaifu(server.getName()), e));
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
