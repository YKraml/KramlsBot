package waifu.loader;

import exceptions.MyOwnException;
import waifu.model.Player;

public class PlayerLoaderOffline implements PlayerLoader {

  private static final Player PLAYER = Player.createEmptyPlayer();

  @Override
  public void savePlayer(Player player) throws MyOwnException {
    //Can't save players without DB.
  }

  @Override
  public Player getPlayerById(String userId) throws MyOwnException {
    return PLAYER;
  }
}
