package logic.routines;

import domain.Answer;
import domain.DungeonLoader;
import domain.exceptions.MyOwnException;
import domain.waifu.dungeon.Dungeon;
import logic.messages.MessageSender;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;

public class RoutineCreateDungeon extends Routine {

  private final Server server;
  private final int difficulty;
  private final String name;
  private final TextChannel channel;
  private final DungeonLoader dungeonLoader;
  private final MessageSender messageSender;

  public RoutineCreateDungeon(Server server, int difficulty, String name, TextChannel channel,
      DungeonLoader dungeonLoader, MessageSender messageSender) {
    this.server = server;
    this.difficulty = difficulty;
    this.name = name;
    this.channel = channel;
    this.dungeonLoader = dungeonLoader;
    this.messageSender = messageSender;
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

      messageSender.sendDungeonCreatedMessage(channel, dungeon);
      return new Answer("Dungeon wurde erstellt. Dungeon = " + dungeon);

    } catch (MyOwnException e) {
      serverTextChannel.delete();
      throw e;
    }
  }
}
