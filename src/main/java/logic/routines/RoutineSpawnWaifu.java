package logic.routines;

import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotSpawnWaifu;
import domain.waifu.Waifu;
import logic.waifu.WaifuBuilder;
import logic.waifu.WaifuSpawnManager;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import ui.commands.Answer;
import ui.messages.MessageSender;
import ui.messages.messages.ExceptionHappenedMessage;
import ui.messages.messages.WaifuSpawn;
import ui.messages.messages.WaifuToClaimWas;
import util.Terminal;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;

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
