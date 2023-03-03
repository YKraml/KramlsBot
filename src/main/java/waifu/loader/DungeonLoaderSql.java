package waifu.loader;

import exceptions.MyOwnException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import waifu.model.dungeon.Dungeon;
import waifu.sql.SQLCommandExecutor;
import waifu.sql.commands.dungeon.InsertDungeon;
import waifu.sql.commands.dungeon.DeleteDungeon;
import waifu.sql.commands.dungeon.SelectAllDungeons;
import waifu.sql.entry.DungeonEntrySet;

@Singleton
public class DungeonLoaderSql implements DungeonLoader {

  private final SQLCommandExecutor sqlCommandExecutor;

  @Inject
  public DungeonLoaderSql(SQLCommandExecutor sqlCommandExecutor) {
    this.sqlCommandExecutor = sqlCommandExecutor;
  }

  @Override
  public void createDungeon(Dungeon dungeon) throws MyOwnException {
    sqlCommandExecutor.execute(new InsertDungeon(dungeon));
  }

  @Override
  public void deleteDungeon(Dungeon dungeon) throws MyOwnException {
    sqlCommandExecutor.execute(new DeleteDungeon(dungeon));
  }

  @Override
  public List<Dungeon> getAllDungeons() throws MyOwnException {
    DungeonEntrySet dungeonEntries = sqlCommandExecutor.execute(new SelectAllDungeons());
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
    return getAllDungeons().stream().filter(dungeon -> dungeon.getChannelId().equals(channelId))
        .findFirst();
  }
}
