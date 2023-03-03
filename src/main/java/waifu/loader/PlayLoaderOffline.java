package waifu.loader;

import exceptions.MyOwnException;
import waifu.model.Player;

public class PlayLoaderOffline implements PlayerLoader{

  @Override
  public void savePlayer(Player player) throws MyOwnException {

  }

  @Override
  public Player getPlayerById(String userId) throws MyOwnException {
    return Player.createEmptyPlayer();
  }
}
