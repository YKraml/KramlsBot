package routines;

import actions.listeners.commands.Answer;
import de.kraml.Terminal;
import exceptions.MyOwnException;
import exceptions.messages.CouldNotSpawnWaifu;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import messages.MessageSenderImpl;
import messages.MyMessage;
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

  public RoutineSpawnWaifu(ScheduledExecutorService scheduledExecutorService,
      WaifuSpawnManager waifuSpawnManager, WaifuBuilder waifuBuilder, DiscordApi discordApi) {
    this.scheduledExecutorService = scheduledExecutorService;
    this.waifuSpawnManager = waifuSpawnManager;
    this.waifuBuilder = waifuBuilder;
    this.discordApi = discordApi;
  }

  @Override
  Answer start(RoutineRunner routineRunner) throws MyOwnException {
    for (Server server : discordApi.getServers()) {
      scheduledExecutorService.submit(
          () -> findSpawnChannel(server).ifPresent(textChannel -> {
            try {
              spawnWaifu(server, textChannel);
            } catch (MyOwnException e) {
              throw new RuntimeException(
                  new MyOwnException(() -> "Exception while spawning Waifu", e));
            }
          }));
    }
    return new Answer("Spawned Waifu");
  }

  private Optional<TextChannel> findSpawnChannel(Server server) {

    for (ServerTextChannel serverTextChannel : server.getTextChannels()) {
      if ("spawn".equalsIgnoreCase(serverTextChannel.getName())) {
        return Optional.of(serverTextChannel);
      }
    }

    return Optional.empty();
  }

  private void spawnWaifu(Server server, TextChannel textChannel)
      throws MyOwnException {
    try {

      String serverId = server.getIdAsString();
      Optional<Waifu> waifuOptional = waifuSpawnManager.getWaifu(serverId);
      if (waifuOptional.isPresent()) {
        MessageSenderImpl result;
        synchronized (MessageSenderImpl.class) {
          result = new MessageSenderImpl();
        }
        result.send(new WaifuToClaimWas(waifuOptional.get()), textChannel);
      }

      Waifu newWaifu = waifuBuilder.createRandomWaifu();
      waifuSpawnManager.setWaifuToGuess(serverId, newWaifu);
      MessageSenderImpl result;
      synchronized (MessageSenderImpl.class) {
        result = new MessageSenderImpl();
      }
      result.send(new WaifuSpawn(newWaifu), textChannel);

      String message = "Waifu spawned. Server = ''{0}'', Waifu = ''{1}''";
      Terminal.printLine(MessageFormat.format(message, server.getName(), newWaifu.getId()));

    } catch (MyOwnException e) {
      MyOwnException spawnWaifuException = new MyOwnException(new CouldNotSpawnWaifu(), e);
      MyMessage myMessage = new ExceptionHappenedMessage(spawnWaifuException);
      MessageSenderImpl result;
      synchronized (MessageSenderImpl.class) {
        result = new MessageSenderImpl();
      }
      result.send(myMessage, textChannel);
    }
  }
}
