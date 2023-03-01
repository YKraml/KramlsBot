package waifu.loader;

import exceptions.MyOwnException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Singleton;
import waifu.model.dungeon.Dungeon;
import waifu.sql.commands.dungeon.InsertDungeon;
import waifu.sql.commands.dungeon.DeleteDungeon;
import waifu.sql.commands.dungeon.SelectAllDungeons;
import waifu.sql.entry.DungeonEntrySet;

@Singleton
public class DungeonLoaderSql implements DungeonLoader {

  @Override
  public void createDungeon(Dungeon dungeon) throws MyOwnException {
    new InsertDungeon(dungeon).executeCommand();
  }

  @Override
  public void deleteDungeon(Dungeon dungeon) throws MyOwnException {
    new DeleteDungeon(dungeon).executeCommand();
  }

  @Override
  public List<Dungeon> getAllDungeons() throws MyOwnException {
    DungeonEntrySet dungeonEntries = new SelectAllDungeons().executeCommand();
    return dungeonEntries.stream().map(
        entry -> new Dungeon(entry.getServerId(), entry.getChannelId(), entry.getName(),
            entry.getDifficulty())).collect(Collectors.toList());

  }

  @Override
  public List<Dungeon> getAllDungeonsFromServer(String serverId) throws MyOwnException {
    return getAllDungeons().stream().filter(dungeon -> dungeon.getServerId().equals(serverId))
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Dungeon> getDungeon(String channelId) throws MyOwnException {
    return getAllDungeons().stream().filter(dungeon -> dungeon.getChannelId().equals(channelId)).findFirst();
  }
}
