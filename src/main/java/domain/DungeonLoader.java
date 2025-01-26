package domain;

import domain.exceptions.MyOwnException;
import domain.waifu.dungeon.Dungeon;
import java.util.List;
import java.util.Optional;

public interface DungeonLoader {

  void createDungeon(Dungeon dungeon) throws MyOwnException;

  void deleteDungeon(Dungeon dungeon) throws MyOwnException;

  List<Dungeon> getAllDungeons() throws MyOwnException;

  List<Dungeon> getAllDungeonsFromServer(String serverId) throws MyOwnException;

  Optional<Dungeon> getDungeon(String channelId) throws MyOwnException;

}
