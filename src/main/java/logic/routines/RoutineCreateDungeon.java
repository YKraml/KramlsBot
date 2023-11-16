package logic.routines;

import domain.Answer;
import domain.exceptions.MyOwnException;
import domain.waifu.dungeon.Dungeon;
import logic.waifu.DungeonLoader;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import ui.messages.MessageSenderImpl;
import ui.messages.messages.DungeonCreatedMessage;

public class RoutineCreateDungeon extends Routine {

    private final Server server;
    private final int difficulty;
    private final String name;
    private final TextChannel channel;
    private final DungeonLoader dungeonLoader;

    public RoutineCreateDungeon(Server server, int difficulty, String name, TextChannel channel,
                                DungeonLoader dungeonLoader) {
        this.server = server;
        this.difficulty = difficulty;
        this.name = name;
        this.channel = channel;
        this.dungeonLoader = dungeonLoader;
    }

    @Override
    Answer start(RoutineRunner routineRunner) throws MyOwnException {
        ServerTextChannel serverTextChannel = server
                .createTextChannelBuilder()
                .setName("Dungeon - %d - %s".formatted(difficulty, name))
                .create()
                .join();

        try {
            String serverId = serverTextChannel.getServer().getIdAsString();
            String channelId = serverTextChannel.getIdAsString();
            Dungeon dungeon = new Dungeon(serverId, channelId, name, difficulty);

            dungeonLoader.createDungeon(dungeon);

            MessageSenderImpl result;
            synchronized (MessageSenderImpl.class) {
                result = new MessageSenderImpl();
            }
            result.send(new DungeonCreatedMessage(dungeon), channel);
            return new Answer("Dungeon wurde erstellt. Dungeon = " + dungeon);

        } catch (MyOwnException e) {
            serverTextChannel.delete();
            throw e;
        }
    }
}
