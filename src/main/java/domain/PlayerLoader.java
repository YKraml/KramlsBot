package domain;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import org.javacord.api.entity.user.User;

public interface PlayerLoader {

    void savePlayer(Player player) throws MyOwnException;

    Player getPlayerById(String userId) throws MyOwnException;

    default Player getPlayerByUser(User user) throws MyOwnException {
        Player player = getPlayerById(user.getIdAsString());
        player.setName(user.getName());
        return player;
    }
}
