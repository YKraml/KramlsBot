package waifu.loader;

import com.google.inject.Singleton;
import exceptions.MyOwnException;
import org.javacord.api.entity.user.User;
import waifu.model.Player;

public interface PlayerLoader {

  void savePlayer(Player player) throws MyOwnException;

  Player getPlayerById(String userId) throws MyOwnException;

  default Player getPlayerByUser(User user) throws MyOwnException {
    Player player = getPlayerById(user.getIdAsString());
    player.setName(user.getName());
    return player;
  }
}
