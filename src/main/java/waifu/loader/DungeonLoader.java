package waifu.loader;

import exceptions.MyOwnException;
import java.util.List;
import java.util.Optional;
import waifu.model.dungeon.Dungeon;

public interface DungeonLoader {

  void createDungeon(Dungeon dungeon) throws MyOwnException;

  void deleteDungeon(Dungeon dungeon) throws MyOwnException;

  List<Dungeon> getAllDungeons() throws MyOwnException;

  List<Dungeon> getAllDungeonsFromServer(String serverId) throws MyOwnException;

  Optional<Dungeon> getDungeon(String channelId) throws MyOwnException;

}
